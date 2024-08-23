package com.booking.flipkar.business.factory;

import com.booking.flipkar.dao.VehicleDao;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.model.VehicleType;

import java.util.ArrayList;
import java.util.UUID;

public class TwoWheelerFactory implements VehicleFactory {

    private VehicleDao vehicleDao;

    public TwoWheelerFactory(VehicleDao vehicleDao){
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        vehicle.setVehicleId(UUID.randomUUID().toString());
        vehicle.setVehicleType(VehicleType.BIKE);
        vehicle.setSlots(new ArrayList<>());
        return this.vehicleDao.createNewVehicle(vehicle);
    }
}
