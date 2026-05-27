package com.scm.wms.mapper;

import com.scm.wms.dto.request.WarehouseRequestDto;
import com.scm.wms.dto.response.WarehouseResponseDto;
import com.scm.wms.entities.Warehouse;

public class WarehouseMapper {

    public static Warehouse toEntity(WarehouseRequestDto dto) {

        return Warehouse.builder()
                .warehouseCode(dto.getWarehouseCode())
                .name(dto.getName())
                .location(dto.getLocation())
                .capacity(dto.getCapacity())
                .managerName(dto.getManagerName())
                .contactNumber(dto.getContactNumber())
                .status(dto.getStatus())
                .usedCapacity(dto.getUsedCapacity())
                .build();
    }

    public static WarehouseResponseDto toDto(Warehouse warehouse) {

        return WarehouseResponseDto.builder()
                .id(warehouse.getId())
                .warehouseCode(warehouse.getWarehouseCode())
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .capacity(warehouse.getCapacity())
                .managerName(warehouse.getManagerName())
                .contactNumber(warehouse.getContactNumber())
                .status(warehouse.getStatus())
                .createdAt(warehouse.getCreatedAt())
                .updatedAt(warehouse.getUpdatedAt())
                .usedCapacity(
                        warehouse.getUsedCapacity() == null
                                ? 0
                                : warehouse.getUsedCapacity()
                )

                .freeCapacity(
                        warehouse.getCapacity()
                                - (
                                warehouse.getUsedCapacity() == null
                                        ? 0
                                        : warehouse.getUsedCapacity()
                        )
                )

                .build();
    }
}
