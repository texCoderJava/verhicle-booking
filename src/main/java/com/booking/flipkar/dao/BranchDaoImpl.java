package com.booking.flipkar.dao;

import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.expcetion.FlipKarApplicationException;
import com.booking.flipkar.model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BranchDaoImpl implements BranchDao{
    private Map<String,Branch> branchMap = new HashMap<>();

    @Override
    public Branch getBranchByName(String branchName) {
        return Optional.ofNullable(this.branchMap.get(branchName))
                .orElseThrow(() -> new FlipKarApplicationException("Branch not found. branchName : " + branchName));
    }

    @Override
    public Branch createBranch(String branchArea, Location location) {
        Optional.ofNullable(this.branchMap.get(branchArea))
                .ifPresent(branch -> {
                    throw new FlipKarApplicationException("Branch with name already exist. BranchName : " + branchArea);
                });

        Branch branch = Branch.builder()
                .branchId(UUID.randomUUID().toString())
                .branchArea(branchArea)
                .vehicles(new ArrayList<>())
                .bookings(new ArrayList<>())
                .location(location)
                .build();
        this.branchMap.put(branchArea,branch);

        return branch;
    }

    @Override
    public List<Branch> getAllBranch() {
        return this.branchMap.values().stream().toList();
    }

    @Override
    public void updateBranch(Branch branch) {
        Optional.ofNullable(this.branchMap.get(branch.getBranchArea()))
                .map(existingBranch -> this.branchMap.put(existingBranch.getBranchArea(),existingBranch))
                .orElseThrow(() ->
                        new FlipKarApplicationException("Unable to update branch. Branch not found. Branch : " + branch.getBranchArea()));
    }
}
