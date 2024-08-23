package com.booking.flipkar.dto;

import com.booking.flipkar.model.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class BookingDto {
    private VehicleType vehicleType;
    private Timestamp startTime;
    private Timestamp endTime;

}
