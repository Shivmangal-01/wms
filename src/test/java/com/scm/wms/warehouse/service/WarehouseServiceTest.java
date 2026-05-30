package com.scm.wms.warehouse.service;


import com.scm.wms.warehouse.dto.request.WarehouseRequestDto;
import com.scm.wms.warehouse.dto.response.WarehouseResponseDto;
import com.scm.wms.warehouse.entities.Warehouse;
import com.scm.wms.warehouse.enums.WarehouseStatus;
import com.scm.wms.warehouse.exception.DuplicateResourceException;
import com.scm.wms.warehouse.exception.ResourceNotFoundException;
import com.scm.wms.warehouse.repository.WarehouseRepository;
import com.scm.wms.warehouse.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    private Warehouse warehouse;

    private WarehouseRequestDto requestDto;

    @BeforeEach
    void setUp() {

        warehouse = Warehouse.builder()
                .id(1L)
                .warehouseCode("WH001")
                .name("Pune Warehouse")
                .location("Pune")
                .capacity(5000)
                .managerName("Rahul")
                .contactNumber("9876543210")
                .status(WarehouseStatus.ACTIVE)
                .build();

        requestDto = new WarehouseRequestDto();

        requestDto.setWarehouseCode("WH001");
        requestDto.setName("Pune Warehouse");
        requestDto.setLocation("Pune");
        requestDto.setCapacity(5000);
        requestDto.setManagerName("Rahul");
        requestDto.setContactNumber("9876543210");
        requestDto.setStatus(WarehouseStatus.ACTIVE);
    }

    @Test
    void createWarehouse_ShouldReturnWarehouseResponse() {

        when(warehouseRepository
                .findByWarehouseCode("WH001"))
                .thenReturn(Optional.empty());

        when(warehouseRepository.save(any(Warehouse.class)))
                .thenReturn(warehouse);

        WarehouseResponseDto response =
                warehouseService.createWarehouse(requestDto);

        assertNotNull(response);

        assertEquals(
                "WH001",
                response.getWarehouseCode()
        );

        assertEquals(
                "Pune Warehouse",
                response.getName()
        );
        verify(warehouseRepository)
                .save(any(Warehouse.class));
    }


    @Test
    void createWarehouse_ShouldThrowException_WhenCodeExists() {

        when(warehouseRepository
                .findByWarehouseCode("WH001"))
                .thenReturn(Optional.of(warehouse));

        assertThrows(
                DuplicateResourceException.class,
                () -> warehouseService.createWarehouse(requestDto)
        );

        verify(warehouseRepository, never())
                .save(any(Warehouse.class));


    }


    @Test
    void getWarehouseById_ShouldReturnWarehouse() {

        when(warehouseRepository.findById(1L))
                .thenReturn(Optional.of(warehouse));

        WarehouseResponseDto response =
                warehouseService.getWarehouseById(1L);

        assertNotNull(response);

        assertEquals(
                "WH001",
                response.getWarehouseCode()
        );
    }


    @Test
    void getWarehouseById_ShouldThrowException_WhenNotFound() {

        when(warehouseRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> warehouseService.getWarehouseById(1L)
        );
    }

    @Test
    void deleteWarehouse_ShouldSoftDeleteWarehouse() {

        when(warehouseRepository.findById(1L))
                .thenReturn(Optional.of(warehouse));

        warehouseService.deleteWarehouse(1L);

        assertTrue(warehouse.getIsDeleted());
    }
}
