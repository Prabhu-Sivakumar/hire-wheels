package com.upgrad.hirewheels.services;

import com.upgrad.hirewheels.dao.FuelTypeDao;
import com.upgrad.hirewheels.dao.LocationDao;
import com.upgrad.hirewheels.dao.VehicleCategoryDao;
import com.upgrad.hirewheels.dao.VehicleDao;
import com.upgrad.hirewheels.dao.VehicleSubcategoryDao;
import com.upgrad.hirewheels.entities.FuelType;
import com.upgrad.hirewheels.entities.Location;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.entities.VehicleSubcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {
  @Autowired
  VehicleDao vehicleDao;

  @Autowired
  VehicleSubcategoryDao vehicleSubcategoryDao;

  @Autowired
  VehicleCategoryDao vehicleCategoryDao;

  @Autowired
  LocationDao locationDao;

  @Autowired
  FuelTypeDao fuelTypeDao;

  /**
   * This method interacts with the VehicleDao to store vehicle's data into the database.
   * @param vehicle
   * @return
   */

  @Override
  public Vehicle registerVehicle(Vehicle vehicle) throws Exception {
    boolean testVehicleNumber = vehicleDao.existsByVehicleNumber(vehicle.getVehicleNumber());
    if (testVehicleNumber) {
      System.out.println("Vehicle Number Already Exists");
    }

    VehicleSubcategory vehicleSubcategory =
        vehicleSubcategoryDao.findById(vehicle.getVehicleSubcategory().getVehicleSubcategoryId()).get();
    if (vehicleSubcategory == null) {
      throw new Exception("Vehicle Subcategory id is not correct");
    }
    vehicle.setVehicleSubcategory(vehicleSubcategory);

    Location location = locationDao.findById(vehicle.getLocation().getLocationId()).get();
    if (location == null) {
      throw new Exception("Location id is not correct");
    }
    vehicle.setLocation(location);

    FuelType fuelType = fuelTypeDao.findById(vehicle.getFuelType().getFuelTypeId()).get();

    if (fuelType == null) {
      throw new Exception("Fuel type is not correct");
    }

    Vehicle savedVehicle = vehicleDao.save(vehicle);
    return savedVehicle;
  }

  /**
   * This method changes the availability_status field of the vehicle based on the input parameters.
   * If the availability_status =0, then the vehicle is not available for booking. Similarly,
   * if the availability_status =1, then the vehicle is available for booking.
   * @param vehicleId
   * @param availabilityStatus
   * @return
   */

  @Override
  public Vehicle changeAvailability(int vehicleId, int availabilityStatus) {
    Vehicle vehicle = vehicleDao.findById(vehicleId).get();

    vehicle.setAvailabilityStatus(availabilityStatus);
    return vehicleDao.save(vehicle);
  }
}
