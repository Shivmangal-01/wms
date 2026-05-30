package com.scm.wms.product.repository;

import com.scm.wms.product.entities.Product;
import com.scm.wms.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySkuCode(String skuCode);

    Page<Product> findByIsDeletedFalse(Pageable pageable);
    Page<Product> findByProductName(String keyword, Pageable pageable);
    @Query("""
        SELECT p
        FROM Product p
        WHERE p.quantity <= p.reorderLevel
        AND p.isDeleted = false""")
    List<Product> findLowStockProducts();

    long countByIsDeletedFalse();
    long countByStatusAndIsDeletedFalse(ProductStatus status);

    @Query("""
        SELECT COALESCE(SUM(p.quantity),0)
        FROM Product p
        WHERE p.isDeleted = false
        """)
    Long getTotalInventoryQuantity();
}
