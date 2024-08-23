package com.booking.flipkar.entity;

import com.booking.flipkar.model.PaymentStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Booking {
    private String bookingId;
    private Branch branch;
    private Vehicle vehicle;
    private Timestamp startTime;
    private Timestamp endTime;
    private double amount;
    private PaymentStatus paymentStatus;
}
