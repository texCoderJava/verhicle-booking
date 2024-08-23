package com.booking.flipkar.business.handler;

import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.expcetion.FlipKarApplicationException;

import java.util.List;
import java.util.Optional;

abstract class BookingStrategyHandler {
    protected BookingStrategyHandler nextHandler;

    public void setNextHandler(BookingStrategyHandler bookingStrategyHandler){
        this.nextHandler = bookingStrategyHandler;
    }

    public Vehicle handleBooking(List<Vehicle> vehicles){
        List<Vehicle> filteredVehicles = applyStrategy(vehicles);
        if(filteredVehicles.size() > 1 && this.nextHandler != null){
            return this.nextHandler.handleBooking(filteredVehicles);
        }

        return Optional.ofNullable(filteredVehicles.get(0))
                .orElseThrow(() -> new FlipKarApplicationException("No vehicle meeting criteria"));
    }

    protected abstract List<Vehicle> applyStrategy(List<Vehicle> vehicles);
}
