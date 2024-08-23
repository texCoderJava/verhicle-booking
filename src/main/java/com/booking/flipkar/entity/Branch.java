package com.booking.flipkar.entity;

import com.booking.flipkar.model.Location;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Branch {
    private String branchId;
    private String branchArea;
    private Location location;
    private List<Vehicle> vehicles;
    private List<Booking> bookings;

}
