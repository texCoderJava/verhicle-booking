package com.booking.flipkar.dao;

import com.booking.flipkar.entity.Booking;
import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;

import java.sql.Timestamp;

public interface BookingDao {

    Booking getBookingById(String bookingId);

    Booking createBooking(Vehicle vehicle, Branch branch, Timestamp startTime,Timestamp endTime);

    void updateBooking(Booking booking);
}
