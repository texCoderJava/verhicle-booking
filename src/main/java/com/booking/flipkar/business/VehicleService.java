package com.booking.flipkar.business;

import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.model.VehicleType;

import java.util.List;

public interface VehicleService {

    Vehicle createVehicle(VehicleType vehicleType, double price, Branch branch);

    void updateVehicle(Vehicle vehicle);

    List<Vehicle> getVehicleByBranch(String branch);

    List<Vehicle> getAllVehicles();
}
