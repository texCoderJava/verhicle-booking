package com.booking.flipkar.dao;

import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.model.Location;

import java.util.List;

public interface BranchDao {

    Branch getBranchByName(String branchName);

    Branch createBranch(String branchArea, Location location);

    List<Branch> getAllBranch();

    void updateBranch(Branch branch);
}
