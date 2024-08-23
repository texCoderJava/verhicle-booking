package com.booking.flipkar.entity;

import com.booking.flipkar.model.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Slot {
    private Timestamp startTime;
    private Timestamp endTime;
    private VehicleStatus vehicleStatus;
}
