package com.booking.flipkar.business;

import com.booking.flipkar.dao.BranchDao;
import com.booking.flipkar.dto.VehicleDto;
import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.Vehicle;
import com.booking.flipkar.expcetion.FlipKarApplicationException;
import com.booking.flipkar.model.Location;
import com.booking.flipkar.model.VehicleType;
import com.booking.flipkar.util.LocationUtil;

import java.util.List;
import java.util.stream.IntStream;

public class BranchServiceImpl implements BranchService{
    private BranchDao branchDao;
    private VehicleService vehicleService;
    public BranchServiceImpl(BranchDao branchDao,VehicleService vehicleService) {
        this.branchDao = branchDao;
        this.vehicleService = vehicleService;
    }

    @Override
    public Branch addBranch(String branchArea, List<VehicleDto> vehiclesDto) {
        Location location = LocationUtil.generateRandomLocation();
        Branch branch = this.branchDao.createBranch(branchArea,location);
        List<Vehicle> vehicles = vehiclesDto.stream()
                .flatMap(vehicle -> IntStream.rangeClosed(0, vehicle.getQuantity() - 1)
                        .mapToObj(vehicleCount -> this.vehicleService.createVehicle(vehicle.getVehicleType(), vehicle.getHourlyPricePerUnit(), branch)))
                .toList();
        branch.getVehicles().addAll(vehicles);
        this.branchDao.updateBranch(branch);
        return branch;
    }

    @Override
    public void addVehicles(String branchName,int quantity,VehicleType vehicleType,double pricePerUnit) {
        try {
            Branch branch = this.branchDao.getBranchByName(branchName);
            List<Vehicle> vehicles = IntStream.rangeClosed(0,quantity - 1)
                    .mapToObj(vehicle -> this.vehicleService.createVehicle(vehicleType,pricePerUnit,branch))
                    .toList();

            branch.getVehicles().addAll(vehicles);
            this.branchDao.updateBranch(branch);
        } catch (FlipKarApplicationException fae){
            System.out.println(fae.getMessage());
        }
    }

    @Override
    public Branch getBranch(String branch) {
        return this.branchDao.getBranchByName(branch);
    }

    @Override
    public List<Branch> getAllBranches() {
        return this.branchDao.getAllBranch();
    }
}
