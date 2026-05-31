package com.scm.wms.warehouse.dto.response;

import com.scm.wms.warehouse.enums.WarehouseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class WarehouseResponseDto {
    private Long id;
    private Long version;
    private String warehouseCode;
    private String name;

    private String location;

    private Integer capacity;

    private String managerName;

    private String contactNumber;

    private WarehouseStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer usedCapacity;

    private Integer freeCapacity;

}
