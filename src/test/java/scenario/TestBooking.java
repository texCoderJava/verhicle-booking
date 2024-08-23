package scenario;

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
import com.booking.flipkar.entity.Branch;
import com.booking.flipkar.entity.User;
import com.booking.flipkar.expcetion.FlipKarApplicationException;
import com.booking.flipkar.model.Location;
import com.booking.flipkar.model.VehicleType;
import com.booking.flipkar.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestBooking {

    private VehicleDao vehicleDao;
    private BranchDao branchDao;
    private BookingDao bookingDao;

    @Before
    public void init(){
        this.vehicleDao = new VehicleDaoImpl();
        this.branchDao = new BranchDaoImpl();
        this.bookingDao = new BookingDaoImpl();
    }

    @Test
    public void testAddBranches(){
        VehicleService vehicleService = new VehicleServiceImpl(vehicleDao);
        BranchService branchService = new BranchServiceImpl(branchDao,vehicleService);

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


        Assertions.assertEquals(3, branchService.getAllBranches().size());
        Assertions.assertEquals(1,branchService.getBranch("malleshwaram").getVehicles().size());
        Assertions.assertEquals(1,branchService.getBranch("jayanagar").getVehicles().size());
        Assertions.assertEquals(1,branchService.getBranch("koramangla").getVehicles().size());

    }

    @Test
    public void addVehicle(){
        VehicleService vehicleService = new VehicleServiceImpl(vehicleDao);
        BranchService branchService = new BranchServiceImpl(branchDao,vehicleService);

        branchService.addBranch("koramangla", List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.20)
                .build()));

        branchService.addVehicles("koramangla",1,VehicleType.BIKE,0.12);

        Assertions.assertEquals(2,branchService.getBranch("koramangla").getVehicles().size());
    }

    @Test
    public void testRentVehicle() throws ParseException {
        User user = User.builder()
                .userName("Sam")
                .email("sam@mail.com")
                .phoneNumber("1234567890")
                .location(new Location(12.9383177,77.7255165))
                .build();

        BookingStrategy lowestPriceStrategy = new LowestPriceStrategy();
        BookingStrategy nearestBranchBookingStrategy = new NearestBranchStrategy(user.getLocation());

        LowestPriceHandler lowestPriceHandler = new LowestPriceHandler(lowestPriceStrategy);
        NearestBranchHandler nearestBranchHandler = new NearestBranchHandler(nearestBranchBookingStrategy);

        lowestPriceHandler.setNextHandler(nearestBranchHandler);


        VehicleService vehicleService = new VehicleServiceImpl(vehicleDao);
        BranchService branchService = new BranchServiceImpl(branchDao,vehicleService);

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


        BookingService bookingService = new BookingServiceImpl(lowestPriceHandler,vehicleService,bookingDao);

        Booking booking = bookingService.bookVehicle(BookingDto.builder()
                .vehicleType(VehicleType.BIKE)
                .startTime(DateTimeUtil.convertToTimeStamp("20 Feb 2024 10:00 PM"))
                .endTime(DateTimeUtil.convertToTimeStamp("21 Feb 2024 12:00 PM"))
                .build());

        Assertions.assertNotNull(booking.getVehicle());

        Booking booking1= bookingService.bookVehicle(BookingDto.builder()
                .vehicleType(VehicleType.BIKE)
                .startTime(DateTimeUtil.convertToTimeStamp("20 Feb 2024 10:00 PM"))
                .endTime(DateTimeUtil.convertToTimeStamp("21 Feb 2024 12:00 PM"))
                .build());

        Assertions.assertNotNull(booking1.getVehicle());

        Booking booking2= bookingService.bookVehicle(BookingDto.builder()
                .vehicleType(VehicleType.BIKE)
                .startTime(DateTimeUtil.convertToTimeStamp("20 Feb 2024 10:00 PM"))
                .endTime(DateTimeUtil.convertToTimeStamp("21 Feb 2024 12:00 PM"))
                .build());

        Assertions.assertNotNull(booking2.getVehicle());


        FlipKarApplicationException exception = Assertions.assertThrows(FlipKarApplicationException.class
                ,() -> bookingService.bookVehicle(BookingDto.builder()
                        .vehicleType(VehicleType.BIKE)
                        .startTime(DateTimeUtil.convertToTimeStamp("20 Feb 2024 10:00 PM"))
                        .endTime(DateTimeUtil.convertToTimeStamp("21 Feb 2024 12:00 PM"))
                        .build()));

        Assertions.assertEquals("No vehicle Found please try later",exception.getMessage());

    }

    @Test
    public void testBookingWithNearLocation() throws ParseException {
        User user = User.builder()
                .userName("Sam")
                .email("sam@mail.com")
                .phoneNumber("1234567890")
                .location(new Location(20.593440,78.962201))
                .build();

        BookingStrategy lowestPriceStrategy = new LowestPriceStrategy();
        BookingStrategy nearestBranchBookingStrategy = new NearestBranchStrategy(user.getLocation());

        LowestPriceHandler lowestPriceHandler = new LowestPriceHandler(lowestPriceStrategy);
        NearestBranchHandler nearestBranchHandler = new NearestBranchHandler(nearestBranchBookingStrategy);

        lowestPriceHandler.setNextHandler(nearestBranchHandler);


        VehicleService vehicleService = new VehicleServiceImpl(vehicleDao);
        BranchService branchService = new BranchServiceImpl(branchDao,vehicleService);

        Branch kormanglaBranch = branchService.addBranch("koramangla", List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.30)
                .build()));

        Branch jayanagarBranch = branchService.addBranch("jayanagar",List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.30)
                .build()));


        jayanagarBranch.setLocation(new Location(20.595328,78.959710));
        kormanglaBranch.setLocation(new Location(20.598702,78.954428));

        this.branchDao.updateBranch(jayanagarBranch);
        this.branchDao.updateBranch(kormanglaBranch);

        BookingService bookingService = new BookingServiceImpl(lowestPriceHandler,vehicleService,bookingDao);

        Booking booking = bookingService.bookVehicle(BookingDto.builder()
                .vehicleType(VehicleType.BIKE)
                .startTime(DateTimeUtil.convertToTimeStamp("20 Feb 2024 10:00 PM"))
                .endTime(DateTimeUtil.convertToTimeStamp("21 Feb 2024 12:00 PM"))
                .build());

        Assertions.assertEquals("jayanagar",booking.getBranch().getBranchArea());

    }

    @Test
    public void testBookingImpersonated_withMultipleUsers(){
        User user = User.builder()
                .userName("Sam")
                .email("sam@mail.com")
                .phoneNumber("1234567890")
                .location(new Location(20.593440,78.962201))
                .build();

        BookingStrategy lowestPriceStrategy = new LowestPriceStrategy();
        BookingStrategy nearestBranchBookingStrategy = new NearestBranchStrategy(user.getLocation());

        LowestPriceHandler lowestPriceHandler = new LowestPriceHandler(lowestPriceStrategy);
        NearestBranchHandler nearestBranchHandler = new NearestBranchHandler(nearestBranchBookingStrategy);

        lowestPriceHandler.setNextHandler(nearestBranchHandler);


        VehicleService vehicleService = new VehicleServiceImpl(vehicleDao);
        BranchService branchService = new BranchServiceImpl(branchDao,vehicleService);

        Branch kormanglaBranch = branchService.addBranch("koramangla", List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.30)
                .build()));

        Branch jayanagarBranch = branchService.addBranch("jayanagar",List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.30)
                .build()));


    }

    @Test
    public void testRentVehicleWithMultipleThreads() throws ParseException, InterruptedException {
        User user = User.builder()
                .userName("Sam")
                .email("sam@mail.com")
                .phoneNumber("1234567890")
                .location(new Location(12.9383177,77.7255165))
                .build();

        BookingStrategy lowestPriceStrategy = new LowestPriceStrategy();
        BookingStrategy nearestBranchBookingStrategy = new NearestBranchStrategy(user.getLocation());

        LowestPriceHandler lowestPriceHandler = new LowestPriceHandler(lowestPriceStrategy);
        NearestBranchHandler nearestBranchHandler = new NearestBranchHandler(nearestBranchBookingStrategy);

        lowestPriceHandler.setNextHandler(nearestBranchHandler);

        VehicleService vehicleService = new VehicleServiceImpl(vehicleDao);
        BranchService branchService = new BranchServiceImpl(branchDao, vehicleService);

        branchService.addBranch("koramangla", List.of(VehicleDto.builder()
                .quantity(1)
                .vehicleType(VehicleType.BIKE)
                .hourlyPricePerUnit(0.20)
                .build()));

        BookingService bookingService = new BookingServiceImpl(lowestPriceHandler, vehicleService, bookingDao);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Callable<String>> tasks = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            tasks.add(() -> {
                try {
                    Booking booking = bookingService.bookVehicle(BookingDto.builder()
                            .vehicleType(VehicleType.BIKE)
                            .startTime(DateTimeUtil.convertToTimeStamp("20 Feb 2024 10:00 PM"))
                            .endTime(DateTimeUtil.convertToTimeStamp("21 Feb 2024 12:00 PM"))
                            .build());
                    return "Booked: " + booking.getVehicle().getVehicleId();
                } catch (FlipKarApplicationException e) {
                    return "Failed: " + e.getMessage();
                }
            });
        }

        List<Future<String>> results = executorService.invokeAll(tasks);
        executorService.shutdown();

        for (Future<String> result : results) {
            try {
                System.out.println(result.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long successfulBookings = results.stream()
                .filter(future -> {
                    try {
                        return future.get().startsWith("Booked");
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();

        long failedBookings = results.stream()
                .filter(future -> {
                    try {
                        return future.get().startsWith("Failed");
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();

        Assertions.assertEquals(1, successfulBookings);
        Assertions.assertEquals(3, failedBookings);
    }
}
