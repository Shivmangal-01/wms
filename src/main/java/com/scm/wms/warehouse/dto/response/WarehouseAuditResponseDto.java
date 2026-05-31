package com.scm.wms.warehouse.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WarehouseAuditResponseDto {
    private Long id;
    private String warehouseCode;
    private String action;
    private String performedBy;
    private LocalDateTime actionTime;
    private String details;
}
