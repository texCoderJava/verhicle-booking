package com.booking.flipkar.model;

import com.booking.flipkar.util.LocationUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Location {
    private double latitude;
    private double longitude;

    public double getDistanceDiff(Location location){
        return LocationUtil.calculateDistance(this,location);
    }
}
