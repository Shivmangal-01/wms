package com.scm.wms.dto.request;

import com.scm.wms.enums.WarehouseStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class WarehouseRequestDto {
    @NotBlank(message = "Warehouse code is required")
    private String warehouseCode;

    @NotBlank(message = "Warehouse name is required")
    private String name;

    @NotBlank(message = "Warehouse Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than zero")
    private Integer capacity;

    @NotBlank(message = "Manager name is required")
    private String managerName;

    @Pattern(regexp = "^[0-9]{10}$",
    message = "Contact number must be 10 digits")
    private String contactNumber;

    @NotNull(message = "Status is required")
    private WarehouseStatus status;

    @NotNull(message = "Used capacity is required")
    @PositiveOrZero(message = "Used capacity cannot be negative")
    private Integer usedCapacity;
}
