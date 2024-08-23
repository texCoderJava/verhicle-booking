package com.booking.flipkar.business.strategy;

import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.model.Location;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NearestBranchStrategy implements BookingStrategy{

    private Location location;

    public NearestBranchStrategy(Location location){
        this.location = location;
    }

    @Override
    public List<Vehicle> bookVehicle(List<Vehicle> vehicles) {
        return vehicles.stream()
                .min(Comparator.comparingDouble(vehicle -> vehicle.getBranch().getLocation().getDistanceDiff(this.location)))
                .stream().collect(Collectors.toList());
    }
}
