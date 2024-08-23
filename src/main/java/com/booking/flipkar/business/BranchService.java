package com.booking.flipkar.business;

import com.booking.flipkar.dto.VehicleDto;
import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.model.VehicleType;

import java.util.List;

public interface BranchService {
    Branch addBranch(String branchArea, List<VehicleDto> vehicles);

    void addVehicles(String branchName, int quantity, VehicleType vehicleType,double pricePerUnit);
    Branch getBranch(String branch);
    List<Branch> getAllBranches();

}
