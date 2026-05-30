package com.scm.wms.product.dto.request;

import com.scm.wms.product.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    private String productName;

    private String category;

    private String brand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0)
    private Integer quantity;

    private Integer reorderLevel;

    private String supplierName;

    private ProductStatus status;
}
