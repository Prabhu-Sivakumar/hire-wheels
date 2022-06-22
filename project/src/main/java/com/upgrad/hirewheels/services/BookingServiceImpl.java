package com.upgrad.hirewheels.services;

import com.upgrad.hirewheels.dao.BookingDao;
import com.upgrad.hirewheels.dao.LocationDao;
import com.upgrad.hirewheels.dao.UserDao;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.entities.Location;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.entities.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookingServiceImpl implements BookingService {
  @Autowired
  BookingDao bookingDao;

  @Autowired
  UserDao userDao;

  @Autowired
  private LocationDao locationDao;

  @Autowired
  private VehicleService vehicleService;

  /**
   * This method adds booking for a particular vehicle in the database. While adding the booking,
   * the booking amount should be deducted from the wallet balance of the user.
   * @param booking
   * @return
   */

  @Override
  public Booking addBooking(Booking booking) throws Exception {

    Vehicle vehicle = vehicleService.getVehicleBasedOnId(booking.getVehicleWithBooking().getVehicleId());
    if (vehicle == null) {
      throw new Exception("Vehicle  id passed is not correct");
    }
    booking.setVehicleWithBooking(vehicle);
    User user = userDao.findById(booking.getUser().getUserId()).get();

    if (user == null) {
      throw new Exception("User  id passed is not correct");
    }
    booking.setUser(user);
    user = booking.getUser();
    if (user.getWalletMoney() < booking.getAmount()) {
      System.out.println("Insufficient Balance. Please Check With Admin.");
    } else {
      user.setWalletMoney(user.getWalletMoney() - booking.getAmount());
      userDao.save(user);
    }

    Location location = locationDao.findById(booking.getLocation().getLocationId()).get();
    if (location == null) {
      throw new Exception("Location id is not correct");
    }

    booking.setLocation(location);
    Booking savedBooking = bookingDao.save(booking);
    return savedBooking;
  }
}
