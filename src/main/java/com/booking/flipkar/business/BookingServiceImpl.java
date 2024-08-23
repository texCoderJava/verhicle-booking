package com.booking.flipkar.business;

import com.booking.flipkar.business.handler.LowestPriceHandler;
import com.booking.flipkar.dao.BookingDao;
import com.booking.flipkar.dto.BookingDto;
import com.booking.flipkar.entity.Booking;
import com.booking.flipkar.entity.Slot;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.expcetion.FlipKarApplicationException;
import com.booking.flipkar.model.VehicleStatus;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BookingServiceImpl implements BookingService{
    private Lock lock;
    private BookingDao bookingDao;
    private LowestPriceHandler bookingStrategyHandler;
    private VehicleService vehicleService;


    public BookingServiceImpl(LowestPriceHandler bookingStrategyHandler,VehicleService vehicleService,BookingDao bookingDao){
        this.lock = new ReentrantLock(true);
        this.bookingStrategyHandler = bookingStrategyHandler;
        this.vehicleService = vehicleService;
        this.bookingDao = bookingDao;
    }

    @Override
    public Booking bookVehicle(BookingDto bookingDto) {
        try {
            lock.lock();
            List<Vehicle> availableVehicles = this.findVehicleInTimeRange(bookingDto);
            Vehicle vehicle = this.bookingStrategyHandler.handleBooking(availableVehicles);
            Booking booking = this.bookingDao.createBooking(vehicle,vehicle.getBranch(),bookingDto.getStartTime(),bookingDto.getEndTime());
            vehicle.setBooking(booking);
            vehicle.getSlots().add(new Slot(bookingDto.getStartTime(),bookingDto.getEndTime(),VehicleStatus.BOOKED));
            this.vehicleService.updateVehicle(vehicle);
            return booking;
        }finally {
            lock.unlock();
        }
    }

    private List<Vehicle> findVehicleInTimeRange(BookingDto bookingDto){
        List<Vehicle> vehicles = this.vehicleService.getAllVehicles()
                .stream().filter(vehicle -> vehicle.getVehicleType() == bookingDto.getVehicleType()).toList();
        List<Vehicle> eligibleVehicles = vehicles.stream()
                .filter(vehicle -> vehicle.getSlots().isEmpty() || vehicle.getSlots().stream()
                        .noneMatch(slot -> bookingDto.getStartTime().before(slot.getEndTime())
                                && bookingDto.getEndTime().after(slot.getStartTime())))
                .toList();

        if(eligibleVehicles.isEmpty()){
            throw new FlipKarApplicationException("No vehicle Found please try later");
        }

        return eligibleVehicles;
    }
}
