package com.scm.wms.product.service;

import com.scm.wms.product.dto.request.ProductRequestDto;
import com.scm.wms.product.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    Page<ProductResponseDto> getAllProducts(int page, int size, String sortBy, String sortDir);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    void deleteProduct(Long id);

    Page<ProductResponseDto> searchProducts(String keyword, int page, int size);
    List<ProductResponseDto> getLowStockProducts();
}
