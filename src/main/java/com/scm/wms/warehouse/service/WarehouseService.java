package com.scm.wms.service;

import com.scm.wms.dto.request.WarehouseRequestDto;
import com.scm.wms.dto.response.WarehouseResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WarehouseService {

    WarehouseResponseDto createWarehouse(
            WarehouseRequestDto requestDto
    );

//    List<WarehouseResponseDto> getAllWarehouses();
    Page<WarehouseResponseDto> getAllWarehouses(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    WarehouseResponseDto getWarehouseById(Long id);

    WarehouseResponseDto updateWarehouse(
            Long id,
            WarehouseRequestDto requestDto
    );

    void deleteWarehouse(Long id);

    List<WarehouseResponseDto> searchWarehouse(String name);
}
