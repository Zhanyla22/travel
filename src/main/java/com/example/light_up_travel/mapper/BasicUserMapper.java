package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.model.BasicUserDto;


public class BasicUserMapper {

    public static User basicUserDtoToUser(BasicUserDto userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public static BasicUserDto basicUserToUserDTO(User user){
        BasicUserDto userDTO = new BasicUserDto();
        userDTO.setId((user.getId()));
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}