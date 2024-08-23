package com.booking.flipkar.business.handler;

import com.booking.flipkar.business.strategy.BookingStrategy;
import com.booking.flipkar.entity.Vehicle;

import java.util.List;

public class LowestPriceHandler extends BookingStrategyHandler{
    private final BookingStrategy bookingStrategy;
    public LowestPriceHandler(BookingStrategy lowestPriceStrategy){
        this.bookingStrategy = lowestPriceStrategy;
    }

    @Override
    protected List<Vehicle> applyStrategy(List<Vehicle> vehicles) {
        List<Vehicle> vehicles1 = this.bookingStrategy.bookVehicle(vehicles);
        return vehicles1;
    }
}
