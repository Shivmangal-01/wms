package com.scm.wms.product.entities;

import com.scm.wms.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String skuCode;

    @Column(nullable = false)
    private String productName;

    private String category;

    private String brand;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    //minimum safe stock quantity if stock running low then system triggers alert
    private Integer reorderLevel;

    private String supplierName;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Boolean isDeleted = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        //if user doesnt provide status then set default -ACTIVE

        if (status == null) {
            status = ProductStatus.ACTIVE;
        }

        //to avoid null pointere

        if (quantity == null) {
            quantity = 0;
        }


        //alert when stock is below 10
        if (reorderLevel == null) {
            reorderLevel = 10;
        }

        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
