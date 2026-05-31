package com.scm.wms.warehouse.controller;


import com.scm.wms.warehouse.audit.WarehouseAuditService;
import com.scm.wms.warehouse.dto.request.WarehouseRequestDto;
import com.scm.wms.warehouse.dto.response.WarehouseAuditResponseDto;
import com.scm.wms.warehouse.dto.response.WarehouseResponseDto;
import com.scm.wms.warehouse.dto.response.WarehouseStatsDto;
import com.scm.wms.warehouse.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor

@Tag(
        name = "Warehouse Management",
        description = "Operations related to warehouse module"
)
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final WarehouseAuditService auditService;

    //controller to create thr warehouse
    @PostMapping
    @Operation(summary = "Create warehouse")
    public ResponseEntity<WarehouseResponseDto> createWarehouse(
            @Valid @RequestBody WarehouseRequestDto requestDto
            ){
        WarehouseResponseDto response=warehouseService.createWarehouse(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    //controller to get all warehouses
    @GetMapping
    @Operation(summary = "Get all warehouses")
    public ResponseEntity<Page<WarehouseResponseDto>>
    getAllWarehouses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        return ResponseEntity.ok(warehouseService.getAllWarehouses(page, size, sortBy, sortDir));
    }

    //controller to get warehouse by its id

    @GetMapping("/{id}")
    @Operation(summary = "Get Warehouse By Its ID")
    public ResponseEntity<WarehouseResponseDto>getWarehouseById(
            @PathVariable Long id){
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    //controller to update warehouse
    @Operation(summary = "Update Warehouse")
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponseDto>updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseRequestDto requestDto
    ){
        return ResponseEntity.ok(warehouseService.updateWarehouse(id,requestDto));
    }


    //controller to delete the warehouse
    @Operation(summary = "Delete Warehouse")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(
            @PathVariable Long id
    ){
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok("Warehouse deleted successfully");
    }

    //controller to get warehouse by its name
    @GetMapping("/search")
    @Operation(summary = "Search Warehouse By Its Name")
    public ResponseEntity<Page<WarehouseResponseDto>> searchWarehouse(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                warehouseService.searchWarehouse(name, page, size));
    }


    @GetMapping("/stats")
    @Operation(summary = "Get Warehouse Stats")
    public ResponseEntity<WarehouseStatsDto> getWarehouseStats() {
        return ResponseEntity.ok(warehouseService.getWarehouseStats());
    }



    @GetMapping("/{id}/audit")
    @Operation(summary = "Get Audit History For Warehouse")
    public ResponseEntity<List<WarehouseAuditResponseDto>> getAuditHistory(
            @PathVariable Long id) {

        WarehouseResponseDto warehouse = warehouseService.getWarehouseById(id);

        return ResponseEntity.ok(
                auditService.getAuditHistory(warehouse.getWarehouseCode()));
    }
}
