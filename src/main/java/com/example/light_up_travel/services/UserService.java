package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.User;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();


//    boolean isDeleted(Long id);

    List<User> getAllNotDeletedUsers();

    User isUserDeletedCheck(Long id);
}
