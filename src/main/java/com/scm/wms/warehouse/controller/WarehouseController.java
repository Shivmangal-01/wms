package com.scm.wms.warehouse.controller;

import com.scm.wms.warehouse.dto.request.WarehouseRequestDto;
import com.scm.wms.warehouse.dto.response.WarehouseResponseDto;
import com.scm.wms.warehouse.service.WarehouseService;
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
public class WarehouseController {
    private final WarehouseService warehouseService;

    //controller to create thr warehouse
    @PostMapping
    public ResponseEntity<WarehouseResponseDto> createWarehouse(
            @Valid @RequestBody WarehouseRequestDto requestDto
            ){
        WarehouseResponseDto response=warehouseService.createWarehouse(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    //controller to get all warehouses
    @GetMapping
    public ResponseEntity<Page<WarehouseResponseDto>>
    getAllWarehouses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        return ResponseEntity.ok(
                warehouseService.getAllWarehouses(
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }

    //controller to get warehouse by its id
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseDto>getWarehouseById(
            @PathVariable Long id){
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    //controller to update warehouse
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponseDto>updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseRequestDto requestDto
    ){
        return ResponseEntity.ok(warehouseService.updateWarehouse(id,requestDto));
    }


    //controller to delete the warehouse
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(
            @PathVariable Long id
    ){
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok("Warehouse deleted successfully");
    }

    //controller to get warehouse by its name
    @GetMapping("/search")
    public ResponseEntity<List<WarehouseResponseDto>>
    searchWarehouse(@RequestParam String name) {

        return ResponseEntity.ok(
                warehouseService.searchWarehouse(name)
        );
    }
}
