package com.booking.flipkar.business;

import com.booking.flipkar.business.factory.TwoWheelerFactory;
import com.booking.flipkar.business.factory.VehicleFactory;
import com.booking.flipkar.dao.VehicleDao;
import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.expcetion.FlipKarApplicationException;
import com.booking.flipkar.model.VehicleStatus;
import com.booking.flipkar.model.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehicleServiceImpl implements VehicleService{

    private VehicleDao vehicleDao;

    public VehicleServiceImpl(VehicleDao vehicleDao){
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Vehicle createVehicle(VehicleType vehicleType, double price, Branch branch) {
        return Optional.ofNullable(this.getInstanceByType(vehicleType)).map(vehicleFactory ->
                        vehicleFactory.createVehicle(Vehicle.builder()
                        .branch(branch)
                        .hourlyPricePerUnit(price)
                        .build()))
                .orElseThrow(() -> new FlipKarApplicationException("Unsupported vehicle type " + vehicleType.name()));
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        this.vehicleDao.updateVehicle(vehicle);
    }

    @Override
    public List<Vehicle> getVehicleByBranch(String branch) {
        return this.vehicleDao.getAllVehicles().stream().filter(vehicle -> vehicle.getBranch().getBranchArea().equals(branch))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(this.vehicleDao.getAllVehicles());
    }

    private VehicleFactory getInstanceByType(VehicleType vehicleType){
        if (Objects.requireNonNull(vehicleType) == VehicleType.BIKE) {
            return new TwoWheelerFactory(this.vehicleDao);
        }
        return null;
    }
}
