package com.scm.wms.warehouse.service.impl;

import com.scm.wms.warehouse.audit.WarehouseAuditService;

import com.scm.wms.warehouse.dto.request.WarehouseRequestDto;
import com.scm.wms.warehouse.dto.response.WarehouseResponseDto;
import com.scm.wms.warehouse.entities.Warehouse;
import com.scm.wms.warehouse.exception.DuplicateResourceException;
import com.scm.wms.warehouse.exception.ResourceNotFoundException;
import com.scm.wms.warehouse.mapper.WarehouseMapper;
import com.scm.wms.warehouse.repository.WarehouseRepository;
import com.scm.wms.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private final WarehouseRepository warehouseRepository;

    private final WarehouseAuditService auditService;

    private static final Logger logger =
            LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Override
    public WarehouseResponseDto createWarehouse(
            WarehouseRequestDto requestDto) {

        logger.info("Creating warehouse: {}",
                requestDto.getWarehouseCode());

        warehouseRepository.findByWarehouseCode(
                requestDto.getWarehouseCode()
        ).ifPresent(warehouse -> {
            throw new DuplicateResourceException(
                    "Warehouse code already exists"
            );
        });

        Warehouse warehouse = WarehouseMapper.toEntity(requestDto);

        Warehouse savedWarehouse =
                warehouseRepository.save(warehouse);

        auditService.logAction(
                warehouse.getWarehouseCode(),
                "CREATE",
                "ADMIN",
                "Warehouse created successfully"
        );

        return WarehouseMapper.toDto(savedWarehouse);
    }


    @Override
    public Page<WarehouseResponseDto> getAllWarehouses(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Warehouse> warehousePage =
                warehouseRepository.findAll(pageable);

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

        return WarehouseMapper.toDto(warehouse);
    }

    @Override
    public WarehouseResponseDto updateWarehouse(
            Long id,
            WarehouseRequestDto requestDto) {

        logger.info("Updating warehouse with id: {}", id);

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
                "UPDATE",
                "ADMIN",
                "Warehouse updated successfully"
        );

        return WarehouseMapper.toDto(updatedWarehouse);
    }

    @Override
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
                "DELETE",
                "ADMIN",
                "Warehouse soft deleted"
        );
    }

    @Override
    public List<WarehouseResponseDto> searchWarehouse(
            String name) {

        return warehouseRepository
                .findByName(name)
                .stream()
                .map(WarehouseMapper::toDto)
                .toList();
    }
}
