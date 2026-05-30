package com.scm.wms.product.service.impl;

import org.springframework.data.domain.*;
import com.scm.wms.product.dto.request.ProductRequestDto;
import com.scm.wms.product.dto.response.ProductResponseDto;
import com.scm.wms.product.entities.Product;
import com.scm.wms.product.exception.ProductAlreadyExistsException;
import com.scm.wms.product.exception.ProductNotFoundException;
import com.scm.wms.product.mapper.ProductMapper;
import com.scm.wms.product.repository.ProductRepository;
import com.scm.wms.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        Product product = ProductMapper.toEntity(requestDto);

        String skuCode = generateSkuCode(requestDto.getCategory(), requestDto.getBrand());
        product.setSkuCode(skuCode);

        Product savedProduct = productRepository.save(product);

        log.info("Product created successfully with SKU={}", savedProduct.getSkuCode());

        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .filter(p -> !p.getIsDeleted()).orElseThrow(
                        () -> new ProductNotFoundException("Product not found with id: " + id));

        return ProductMapper.toDto(product);
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findByIsDeletedFalse(pageable);

        return productPage.map(ProductMapper::toDto);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {

        Product product = productRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id));
        product.setProductName(requestDto.getProductName());

        product.setCategory(requestDto.getCategory());

        product.setBrand(requestDto.getBrand());

        product.setPrice(requestDto.getPrice());

        product.setQuantity(requestDto.getQuantity());

        product.setReorderLevel(requestDto.getReorderLevel());

        product.setSupplierName(requestDto.getSupplierName());

        product.setStatus(requestDto.getStatus());

        Product updatedProduct = productRepository.save(product);

        log.info("Product updated successfully with SKU={}", updatedProduct.getSkuCode());

        return ProductMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (product.getIsDeleted()) {
            throw new ProductNotFoundException("Product already deleted");
        }

        product.setIsDeleted(true);

        productRepository.save(product);

        log.info("Product soft deleted with SKU={}", product.getSkuCode());
    }

    private String generateSkuCode(String category, String brand) {

        String categoryCode = category == null ? "GEN" : category.length() >= 3 ? category.substring(0, 3) : category;

        String brandCode = brand == null ? "BRD" : brand.length() >= 3 ? brand.substring(0, 3) : brand;

        long count = productRepository.count() + 1;

        return categoryCode.toUpperCase() + "-" + brandCode.toUpperCase() + "-" + String.format("%04d", count);
    }


    @Override
    public Page<ProductResponseDto> searchProducts(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findByProductName(keyword, pageable);

        return productPage.map(ProductMapper::toDto);
    }

    @Override
    public List<ProductResponseDto>
    getLowStockProducts() {
        List<Product> products = productRepository.findLowStockProducts();

        return products.stream()
                .map(ProductMapper::toDto)
                .toList();
    }
}
