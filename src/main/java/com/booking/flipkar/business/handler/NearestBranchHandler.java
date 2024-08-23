package com.booking.flipkar.business.handler;

import com.booking.flipkar.business.strategy.BookingStrategy;
import com.booking.flipkar.entity.Vehicle;

import java.util.List;

public class NearestBranchHandler extends BookingStrategyHandler{

    private BookingStrategy bookingStrategy;
    public NearestBranchHandler(BookingStrategy nearestBookingStrategy){
        this.bookingStrategy = nearestBookingStrategy;
    }

    @Override
    protected List<Vehicle> applyStrategy(List<Vehicle> vehicles) {
        List<Vehicle> vehicles1 = this.bookingStrategy.bookVehicle(vehicles);
        return vehicles1;
    }
}
