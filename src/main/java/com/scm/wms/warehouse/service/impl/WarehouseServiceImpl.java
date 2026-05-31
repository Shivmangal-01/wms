package com.scm.wms.warehouse.service.impl;

import com.scm.wms.warehouse.audit.WarehouseAuditService;

import com.scm.wms.warehouse.dto.request.WarehouseRequestDto;
import com.scm.wms.warehouse.dto.response.WarehouseResponseDto;
import com.scm.wms.warehouse.dto.response.WarehouseStatsDto;
import com.scm.wms.warehouse.entities.Warehouse;
import com.scm.wms.warehouse.enums.AuditAction;
import com.scm.wms.warehouse.enums.WarehouseStatus;
import com.scm.wms.warehouse.exception.DuplicateResourceException;
import com.scm.wms.warehouse.exception.ResourceNotFoundException;
import com.scm.wms.warehouse.mapper.WarehouseMapper;
import com.scm.wms.warehouse.repository.WarehouseRepository;
import com.scm.wms.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {


    private final WarehouseRepository warehouseRepository;

    private final WarehouseAuditService auditService;

    private static final Logger logger =
            LoggerFactory.getLogger(WarehouseServiceImpl.class);

    private static final List<String> ALLOWED_SORT_FIELDS =
            List.of("id", "name", "warehouseCode",
                    "capacity", "status", "createdAt");

    @Override
    @Transactional
    public WarehouseResponseDto createWarehouse(WarehouseRequestDto requestDto) {

        logger.info("Creating warehouse: {}",
                requestDto.getWarehouseCode());

        warehouseRepository.findByWarehouseCode(
                requestDto.getWarehouseCode()
        ).ifPresent(warehouse -> {
            throw new DuplicateResourceException(
                    "Warehouse code already exists"
            );
        });

        if (requestDto.getUsedCapacity() > requestDto.getCapacity()) {
            throw new IllegalArgumentException(
                    "Used capacity cannot exceed total capacity");
        }




        Warehouse warehouse = WarehouseMapper.toEntity(requestDto);

        Warehouse savedWarehouse =
                warehouseRepository.save(warehouse);

        auditService.logAction(
                warehouse.getWarehouseCode(),
                AuditAction.CREATE.name(),
                "ADMIN",
                "Warehouse created successfully"
        );

        return WarehouseMapper.toDto(savedWarehouse);
    }


    @Override
    public Page<WarehouseResponseDto> getAllWarehouses(int page, int size, String sortBy, String sortDir) {



        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sort field: " + sortBy);
        }

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Warehouse> warehousePage = warehouseRepository.findByIsDeletedFalse(pageable);

        return warehousePage.map(WarehouseMapper::toDto);
    }

    @Override
    public WarehouseResponseDto getWarehouseById(Long id) {

        logger.info("Fetching warehouse with id: {}", id);

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id: " + id
                        ));

        if (warehouse.getIsDeleted()) {
            throw new ResourceNotFoundException(
                    "Warehouse not found with id: " + id);
        }

        return WarehouseMapper.toDto(warehouse);
    }

    @Override
    @Transactional
    public WarehouseResponseDto updateWarehouse(
            Long id,
            WarehouseRequestDto requestDto) {

        logger.info("Updating warehouse with id: {}", id);

        if (requestDto.getUsedCapacity() > requestDto.getCapacity()) {
            throw new IllegalArgumentException(
                    "Used capacity cannot exceed total capacity");
        }

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id: " + id
                        ));

        warehouse.setWarehouseCode(requestDto.getWarehouseCode());
        warehouse.setName(requestDto.getName());
        warehouse.setLocation(requestDto.getLocation());
        warehouse.setCapacity(requestDto.getCapacity());
        warehouse.setManagerName(requestDto.getManagerName());
        warehouse.setContactNumber(requestDto.getContactNumber());
        warehouse.setStatus(requestDto.getStatus());

        Warehouse updatedWarehouse =
                warehouseRepository.save(warehouse);

        auditService.logAction(
                warehouse.getWarehouseCode(),
                AuditAction.UPDATE.name(),
                "ADMIN",
                "Warehouse updated successfully"
        );

        return WarehouseMapper.toDto(updatedWarehouse);
    }

    @Override
    @Transactional
    public void deleteWarehouse(Long id) {

        logger.info("Deleting warehouse with id: {}", id);

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id: " + id
                        ));

        warehouse.setIsDeleted(true);
        warehouseRepository.save(warehouse);

        auditService.logAction(
                warehouse.getWarehouseCode(),
                AuditAction.DELETE.name(),
                "ADMIN",
                "Warehouse soft deleted"
        );
    }

    @Override
    public Page<WarehouseResponseDto> searchWarehouse(
            String name, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return warehouseRepository
                .findByNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable)
                .map(WarehouseMapper::toDto);
    }


    @Override
    public WarehouseStatsDto getWarehouseStats() {

        List<Warehouse> warehouses =
                warehouseRepository.findByIsDeletedFalse();

        int totalCapacity = warehouses.stream()
                .mapToInt(Warehouse::getCapacity)
                .sum();

        int totalUsed = warehouses.stream()
                .mapToInt(w -> w.getUsedCapacity() != null
                        ? w.getUsedCapacity() : 0)
                .sum();

        long activeWarehouses = warehouses.stream()
                .filter(w -> w.getStatus() == WarehouseStatus.ACTIVE)
                .count();

        double utilization = totalCapacity > 0
                ? Math.round((totalUsed * 100.0 / totalCapacity) * 100.0) / 100.0
                : 0.0;

        return WarehouseStatsDto.builder()
                .totalWarehouses(warehouses.size())
                .activeWarehouses(activeWarehouses)
                .totalCapacity(totalCapacity)
                .totalUsedCapacity(totalUsed)
                .utilizationPercent(utilization)
                .build();
    }
}
