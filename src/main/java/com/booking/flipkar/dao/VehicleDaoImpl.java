package com.booking.flipkar.dao;

import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.expcetion.FlipKarApplicationException;
import com.booking.flipkar.model.VehicleStatus;
import com.booking.flipkar.model.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VehicleDaoImpl implements VehicleDao{
    private Map<String,Vehicle> vehicleMap;
    public VehicleDaoImpl(){
        this.vehicleMap = new HashMap<>();
    }
    @Override
    public Vehicle getVehicleByType(String id) {
        return Optional.ofNullable(this.vehicleMap.get(id))
                .orElseThrow(() -> new FlipKarApplicationException("No vehicle exist with id : " + id));
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return this.vehicleMap.values().stream().toList();
    }

    @Override
    public Vehicle createNewVehicle(Vehicle vehicle) {
        Optional.ofNullable(this.vehicleMap.get(vehicle.getVehicleId())).ifPresent(newVehicle -> {
            throw new FlipKarApplicationException("Vehicle With Id Already exit. VehicleId : " + newVehicle);
        });

        this.vehicleMap.put(vehicle.getVehicleId(),vehicle);

        return vehicle;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        Optional.ofNullable(this.vehicleMap.get(vehicle.getVehicleId()))
                .map(existingVehicle -> this.vehicleMap.put(existingVehicle.getVehicleId(),existingVehicle))
                .orElseThrow(() -> new FlipKarApplicationException("No vehicle with id " + vehicle.getVehicleId() + " found"));
    }
}
