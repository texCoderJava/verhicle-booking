package com.booking.flipkar.business;

import com.booking.flipkar.dto.BookingDto;
import com.booking.flipkar.entity.Booking;

public interface BookingService {

    Booking bookVehicle(BookingDto bookingDto);
}
