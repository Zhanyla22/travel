package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.dto.BasicUserDTO;


public class BasicUserMapper {

    public static User basicUserDtoToUser(BasicUserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles());
        return user;
    }

    public static BasicUserDTO basicUserToUserDTO(User user){
        BasicUserDTO userDTO = new BasicUserDTO();
        userDTO.setId((user.getId()));
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}