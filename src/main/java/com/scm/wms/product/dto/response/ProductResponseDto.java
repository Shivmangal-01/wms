package com.scm.wms.product.dto.response;

import com.scm.wms.product.enums.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponseDto {

    private Long id;

    private String skuCode;

    private String productName;

    private String category;

    private String brand;

    private BigDecimal price;

    private Integer quantity;

    private Integer reorderLevel;

    private String supplierName;

    private ProductStatus status;

    //because if quantity <= reorderlevel then alert generated
    private Boolean lowStock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
