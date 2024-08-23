package com.booking.flipkar;

import com.booking.flipkar.business.BookingService;
import com.booking.flipkar.business.BookingServiceImpl;
import com.booking.flipkar.business.BranchService;
import com.booking.flipkar.business.BranchServiceImpl;
import com.booking.flipkar.business.VehicleService;
import com.booking.flipkar.business.VehicleServiceImpl;
import com.booking.flipkar.business.handler.LowestPriceHandler;
import com.booking.flipkar.business.handler.NearestBranchHandler;
import com.booking.flipkar.business.strategy.BookingStrategy;
import com.booking.flipkar.business.strategy.LowestPriceStrategy;
import com.booking.flipkar.business.strategy.NearestBranchStrategy;
import com.booking.flipkar.dao.BookingDao;
import com.booking.flipkar.dao.BookingDaoImpl;
import com.booking.flipkar.dao.BranchDao;
import com.booking.flipkar.dao.BranchDaoImpl;
import com.booking.flipkar.dao.VehicleDao;
import com.booking.flipkar.dao.VehicleDaoImpl;
import com.booking.flipkar.dto.BookingDto;
import com.booking.flipkar.dto.VehicleDto;
import com.booking.flipkar.entity.Booking;
import com.booking.flipkar.model.Location;
import com.booking.flipkar.model.VehicleType;
import com.booking.flipkar.util.DateTimeUtil;

import java.text.ParseException;
import java.util.List;

public class FlipKarApplication {

    public static void main(String... args) throws ParseException {
        Location location = new Location(12.9383177,77.7255165);

        BookingStrategy lowestPriceStrategy = new LowestPriceStrategy();
        BookingStrategy nearestBranchBookingStrategy = new NearestBranchStrategy(location);

        LowestPriceHandler lowestPriceHandler = new LowestPriceHandler(lowestPriceStrategy);
        NearestBranchHandler nearestBranchHandler = new NearestBranchHandler(nearestBranchBookingStrategy);

        lowestPriceHandler.setNextHandler(nearestBranchHandler);

        VehicleDao vehicleDao = new VehicleDaoImpl();
        BookingDao bookingDao = new BookingDaoImpl();
        BranchDao branchDao = new BranchDaoImpl();

        VehicleService vehicleService = new VehicleServiceImpl(vehicleDao);
        BranchService branchService = new BranchServiceImpl(branchDao,vehicleService);

        BookingService bookingService = new BookingServiceImpl(lowestPriceHandler,vehicleService,bookingDao);

        //================================================================================================//

        branchService.addBranch("koramangla", List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.20)
                .build()));

        branchService.addBranch("jayanagar",List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.30)
                .build()));

        branchService.addBranch("malleshwaram",List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.30)
                .build()));

        branchService.addVehicles("koramangla",1,VehicleType.BIKE,0.12);

        BookingDto bookingDto = BookingDto.builder()
                .vehicleType(VehicleType.BIKE)
                .startTime(DateTimeUtil.convertToTimeStamp("20 Feb 2024 10:00 PM"))
                .endTime(DateTimeUtil.convertToTimeStamp("21 Feb 2024 12:00 PM"))
                .build();

        Booking booking = bookingService.bookVehicle(bookingDto);

        System.out.println(booking.getVehicle().getSlots());
        System.out.println(booking.getVehicle().getBooking());
        System.out.println(booking.getVehicle().getVehicleType());

        Booking booking1 = bookingService.bookVehicle(bookingDto);

        System.out.println(booking1.getVehicle().getSlots());
        System.out.println(booking1.getVehicle().getBooking());
        System.out.println(booking1.getVehicle().getVehicleType());

        Booking booking2 = bookingService.bookVehicle(bookingDto);

        System.out.println(booking2.getVehicle().getSlots());
        System.out.println(booking2.getVehicle().getBooking());
        System.out.println(booking2.getVehicle().getVehicleType());

        Booking booking3 = bookingService.bookVehicle(bookingDto);

        System.out.println(booking3.getVehicle().getSlots());
        System.out.println(booking3.getVehicle().getBooking());
        System.out.println(booking3.getVehicle().getVehicleType());

        Booking booking4 = bookingService.bookVehicle(bookingDto);

        System.out.println(booking4.getVehicle().getSlots());
        System.out.println(booking4.getVehicle().getBooking());
        System.out.println(booking4.getVehicle().getVehicleType());
    }
}
