package com.scm.wms.warehouse.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseAuditRepository  extends JpaRepository<WarehouseAudit, Long> {

}
