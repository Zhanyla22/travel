package com.example.light_up_travel.services;

import com.example.light_up_travel.model.User;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();


//    boolean isDeleted(Long id);

    List<User> getAllNotDeletedUsers();

    User isUserDeletedCheck(Long id);
}
