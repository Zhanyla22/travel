package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.dto.BasicUserDto;
import com.example.light_up_travel.dto.UpdateUserDto;
import com.example.light_up_travel.dto.AddUserDto;
import com.example.light_up_travel.dto.UserProfileDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public interface UserService {

    List<User> getAllUsers();

    AddUserDto add(AddUserDto signupRequest) throws MessagingException, UnsupportedEncodingException;


    User updateNotDeletedUserById(Long id, UpdateUserDto updateUserDto);

    List<User> getAllNotDeletedUsers();

    List<User> getAllNotDeletedModerators();

    List<BasicUserDto> getAllUsersWithUserRole();

    List<User> getAllDeletedUsers();

    User isUserDeletedCheck(Long id);
}
