package com.scm.wms.product.mapper;

import com.scm.wms.product.dto.request.ProductRequestDto;
import com.scm.wms.product.dto.response.ProductResponseDto;
import com.scm.wms.product.entities.Product;

public class ProductMapper {

    public static Product toEntity(
            ProductRequestDto dto
    ) {

        return Product.builder()
                .productName(dto.getProductName())
                .category(dto.getCategory())
                .brand(dto.getBrand())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .reorderLevel(dto.getReorderLevel())
                .supplierName(dto.getSupplierName())
                .status(dto.getStatus())
                .build();
    }

    public static ProductResponseDto toDto(
            Product product
    ) {

        boolean lowStock =
                product.getQuantity()
                        <= product.getReorderLevel();

        return ProductResponseDto.builder()
                .id(product.getId())
                .skuCode(product.getSkuCode())
                .productName(product.getProductName())
                .category(product.getCategory())
                .brand(product.getBrand())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .reorderLevel(product.getReorderLevel())
                .supplierName(product.getSupplierName())
                .status(product.getStatus())
                .lowStock(lowStock)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
