package com.scm.wms.warehouse.repository;

import com.scm.wms.warehouse.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository  extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByWarehouseCode(String warehouseCode);
    List<Warehouse> findByName(String name);
    Page<Warehouse> findByIsDeletedFalse(Pageable pageable);
}
