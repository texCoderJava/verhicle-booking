package com.booking.flipkar.dao;

import com.booking.flipkar.entity.Booking;
import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.expcetion.FlipKarApplicationException;
import com.booking.flipkar.model.PaymentStatus;
import com.booking.flipkar.util.DateTimeUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BookingDaoImpl implements BookingDao{
    private Map<String, Booking> bookingMap;

    public BookingDaoImpl(){
        this.bookingMap = new HashMap<>();
    }


    @Override
    public Booking getBookingById(String bookingId) {
        return Optional.ofNullable(this.bookingMap.get(bookingId))
                .orElseThrow(() -> new FlipKarApplicationException("Booking not found. BookingId : " + bookingId));
    }

    @Override
    public Booking createBooking(Vehicle vehicle, Branch branch, Timestamp startTime, Timestamp endTime) {
        String bookingId = UUID.randomUUID().toString();
        Booking booking = Booking.builder()
                .bookingId(bookingId)
                .branch(branch)
                .vehicle(vehicle)
                .startTime(startTime)
                .endTime(endTime)
                .amount(DateTimeUtil.findNumberOfHours(startTime,endTime) * vehicle.getHourlyPricePerUnit())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        this.bookingMap.put(bookingId,booking);

        return booking;
    }

    @Override
    public void updateBooking(Booking booking) {
        Optional.ofNullable(this.bookingMap.get(booking.getBookingId()))
                .map(existingBooking -> this.bookingMap.put(booking.getBookingId(),booking))
                .orElseThrow(() -> new FlipKarApplicationException("Booking not found. BookingId : " + booking.getBookingId()));
    }
}
