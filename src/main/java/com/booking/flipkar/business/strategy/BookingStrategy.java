package com.booking.flipkar.business.strategy;

import com.booking.flipkar.dto.VehicleDto;
import com.booking.flipkar.entity.Vehicle;

import java.util.List;

public interface BookingStrategy {
    List<Vehicle> bookVehicle(List<Vehicle> vehicles);
}
