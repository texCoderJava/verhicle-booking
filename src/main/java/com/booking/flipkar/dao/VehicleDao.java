package com.booking.flipkar.dao;

import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.model.VehicleType;

import java.util.List;

public interface VehicleDao {

    Vehicle getVehicleByType(String id);

    List<Vehicle> getAllVehicles();

    Vehicle createNewVehicle(Vehicle vehicle);

    void updateVehicle(Vehicle vehicle);

}
