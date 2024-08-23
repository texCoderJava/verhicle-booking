package com.booking.flipkar.entity;

import com.booking.flipkar.model.VehicleStatus;
import com.booking.flipkar.model.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Vehicle {
    private String vehicleId;
    private VehicleType vehicleType;
    private double hourlyPricePerUnit;
    private Branch branch;
    private Booking booking;
    private User user;
    List<Slot> slots;

}
