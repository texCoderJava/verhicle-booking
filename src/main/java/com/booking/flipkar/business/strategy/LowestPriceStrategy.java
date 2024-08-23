package com.booking.flipkar.business.strategy;

import com.booking.flipkar.entity.Vehicle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class LowestPriceStrategy implements BookingStrategy{
    @Override
    public List<Vehicle> bookVehicle(List<Vehicle> vehicles) {
        OptionalDouble minPriceOpt = vehicles.stream()
                .mapToDouble(Vehicle::getHourlyPricePerUnit)
                .min();

        if (minPriceOpt.isPresent()) {
            double minPrice = minPriceOpt.getAsDouble();
            return vehicles.stream()
                    .filter(vehicle -> vehicle.getHourlyPricePerUnit() == minPrice)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
