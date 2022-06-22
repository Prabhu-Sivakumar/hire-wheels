package com.upgrad.hirewheels.services;

import com.upgrad.hirewheels.dao.UserDao;
import com.upgrad.hirewheels.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    /**
     * Checks if the user mobile number/email is already exists or not.
     * If not exists, saves the user details else throws an error.
     * @param user
     * @return
     */

    @Override
    public User createUser(User user){
        User returnedUser = userDao.findByEmailIgnoreCase(user.getEmail());
        if ( returnedUser != null) {
            System.out.println("Email Already Exists");
        }
        User returnedUser1 = userDao.findByMobileNoIgnoreCase(user.getMobileNo());
        if (returnedUser1 != null) {
            System.out.println("Mobile Number Already Exists");
        }
        User savedUser = userDao.save(user);
        return savedUser;
    }

    /**
     * Checks if the user is registered or not.
     * If registered it returns the user details else throws an error.
     * @param user
     * @return

     */

    @Override
    public User getUser(User user)  {
        User checkUser = userDao.findByEmailIgnoreCase(user.getEmail());
        if (checkUser == null){
            System.out.println("User not Registered");
        }
        User retrievedUser = userDao.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (retrievedUser == null){
            System.out.println("Invalid Credentials");
        }
        return retrievedUser;
    }


    @Override
    public User getUserDetailsByEmail(String email){
        User retrievedUser = userDao.findByEmailIgnoreCase(email);
        if (retrievedUser == null){
            System.out.println("User not Registered");
        }
        return retrievedUser;
    }
}
