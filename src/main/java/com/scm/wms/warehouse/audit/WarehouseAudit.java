package com.scm.wms.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String warehouseCode;
    private String action;
    private String performedBy;
    private LocalDateTime actionTime;
    private String details;
}
