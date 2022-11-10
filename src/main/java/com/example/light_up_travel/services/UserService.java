package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.model.payload.UserDto;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();


//    boolean isDeleted(Long id);
     UserDto add(UserDto signupRequest);

    List<User> getAllNotDeletedUsers();

    User isUserDeletedCheck(Long id);
}
