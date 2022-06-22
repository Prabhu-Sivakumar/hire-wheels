package com.upgrad.hirewheels.services;
import com.upgrad.hirewheels.entities.User;

public interface UserService {
	
    User getUser(User user);
    User createUser(User user);
    User getUserDetailsByEmail(String email);

}

