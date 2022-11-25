package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.model.UpdateUserDto;
import com.example.light_up_travel.model.AddUserDto;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();


//    boolean isDeleted(Long id);
     AddUserDto add(AddUserDto signupRequest);

    User updateNotDeletedUserById(Long id, UpdateUserDto updateUserDto);

    List<User> getAllNotDeletedUsers();

    List<User> getAllDeletedUsers();

//    List<User> getAllNotDeletedModerators();
//
//    List<User> getAllUserRoles();

    User isUserDeletedCheck(Long id);
}
