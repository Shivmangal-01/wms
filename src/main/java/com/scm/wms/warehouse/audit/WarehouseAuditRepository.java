package com.scm.wms.warehouse.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseAuditRepository  extends JpaRepository<WarehouseAudit, Long> {
    List<WarehouseAudit> findByWarehouseCodeOrderByActionTimeDesc(
            String warehouseCode);

}
