package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.model.BasicUserDto;
import com.example.light_up_travel.model.UpdateUserDto;
import com.example.light_up_travel.model.AddUserDto;
import com.example.light_up_travel.model.UserProfileDto;

import javax.mail.MessagingException;
import java.util.List;


public interface UserService {

    List<User> getAllUsers();


//    boolean isDeleted(Long id);
     AddUserDto add(AddUserDto signupRequest) throws MessagingException;


    User updateNotDeletedUserById(Long id, UpdateUserDto updateUserDto);

    UserProfileDto updateProfilePageById(UserProfileDto userProfileDto);


    List<User> getAllNotDeletedUsers();

    List<User> getAllNotDeletedModerators();

    List<BasicUserDto> getAllUsersWithUserRole();

    List<User> getAllDeletedUsers();

    User isUserDeletedCheck(Long id);
}
