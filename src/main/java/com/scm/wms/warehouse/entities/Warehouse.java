package com.scm.wms.entities;

import com.scm.wms.enums.WarehouseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "warehouse_code", nullable = false,unique = true)
    private String warehouseCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "contact_number")
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private WarehouseStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "used_capacity")
    private Integer usedCapacity;

    //run before insert
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    //run before update
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
