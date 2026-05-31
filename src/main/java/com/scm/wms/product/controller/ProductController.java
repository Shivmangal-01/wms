package com.scm.wms.product.controller;

import com.scm.wms.product.dto.request.ProductRequestDto;
import com.scm.wms.product.dto.response.ProductResponseDto;
import com.scm.wms.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

@Tag(
        name = "Product  Management",
        description = "Operations related to Product module"
)
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Add new product")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto response = productService.createProduct(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product By ID")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    @Operation(summary= "Get All Products")
    public ResponseEntity<Page<ProductResponseDto>>
    getAllProducts(@RequestParam(defaultValue = "0") int page,
                   @RequestParam(defaultValue = "5") int size,
                   @RequestParam(defaultValue = "id") String sortBy,
                   @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, sortDir));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Product")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto requestDto) {
        return ResponseEntity.ok(productService.updateProduct(id, requestDto));
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/search")
    @Operation(summary = "Search Product By Its Name")
    public ResponseEntity<Page<ProductResponseDto>>
    searchProducts(@RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(productService.searchProducts(keyword, page, size));
    }


    @Operation(summary = "Low Stock")
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponseDto>>
    getLowStockProducts() {

        return ResponseEntity.ok(
                productService.getLowStockProducts()
        );
    }
}
