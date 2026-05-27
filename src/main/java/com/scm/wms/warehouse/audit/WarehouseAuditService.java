package com.scm.wms.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseAuditService {
    private final WarehouseAuditRepository auditRepository;

    public void logAction(
            String warehouseCode,
            String action,
            String performedBy,
            String details)
    {
        WarehouseAudit audit=WarehouseAudit.builder()
                .warehouseCode(warehouseCode)
                .action(action)
                .performedBy(performedBy)
                .actionTime(LocalDateTime.now())
                .details(details)
                .build();
        auditRepository.save(audit);

        log.info("Audit log saved for warehouse : {} ",warehouseCode);

    }
}
