package com.scm.wms.warehouse.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WarehouseStatsDto {
    private long totalWarehouses;
    private long activeWarehouses;
    private int totalCapacity;
    private int totalUsedCapacity;
    private double utilizationPercent;
}
