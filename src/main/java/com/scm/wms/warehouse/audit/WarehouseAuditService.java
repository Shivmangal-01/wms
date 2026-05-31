package com.scm.wms.warehouse.audit;

import com.scm.wms.warehouse.dto.response.WarehouseAuditResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<WarehouseAuditResponseDto> getAuditHistory(
            String warehouseCode) {

        return auditRepository
                .findByWarehouseCodeOrderByActionTimeDesc(warehouseCode)
                .stream()
                .map(audit -> WarehouseAuditResponseDto.builder()
                        .id(audit.getId())
                        .warehouseCode(audit.getWarehouseCode())
                        .action(audit.getAction())
                        .performedBy(audit.getPerformedBy())
                        .actionTime(audit.getActionTime())
                        .details(audit.getDetails())
                        .build())
                .toList();
    }
}
