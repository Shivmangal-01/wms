package com.scm.wms.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseAuditRepository  extends JpaRepository<WarehouseAudit, Long> {

}
