package com.booking.flipkar.dto;

import com.booking.flipkar.model.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VehicleDto {
    private int quantity;
    private VehicleType vehicleType;
    private double hourlyPricePerUnit;
}
