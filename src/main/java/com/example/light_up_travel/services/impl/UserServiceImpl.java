package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.entity.PasswordResetToken;
import com.example.light_up_travel.entity.Role;
import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.enums.ERole;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.EmailAlreadyExistsException;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.model.UpdateUserDto;
import com.example.light_up_travel.model.AddUserDto;
import com.example.light_up_travel.repository.PasswordResetTokenRepository;
import com.example.light_up_travel.repository.RoleRepository;
import com.example.light_up_travel.repository.UserRepository;
import com.example.light_up_travel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SecurityServiceImpl securityService;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllNotDeletedUsers() {
        return userRepository.findAllNotDeletedUsers();
    }

    public User getNotDeletedUserById(Long id) {
        User user = isUserDeletedCheck(id);
        return userRepository.findNotDeletedUserById(id);
    }

    public User deleteUserById(Long id) {
        User user = isUserDeletedCheck(id);
        user.setDateDeleted(new Date());
        user.setEnabled(false);
        user.setStatus(Status.DELETED_BY_ADMIN);
        user.setVerificationCode(null);
        userRepository.save(user);
        return user;
    }

    @Override
    public AddUserDto add(AddUserDto addUserDto) {
        if (addUserDto.getEmail() != null &
                userRepository.existsByEmail(addUserDto.getEmail().toLowerCase())) {
            throw new EmailAlreadyExistsException("User with email: " + addUserDto.getEmail() + " - is already exist");
        }

        // Create new user's account
        User user = new User();
        user.setName(addUserDto.getName());
        user.setSurname(addUserDto.getSurname());
        user.setEmail(addUserDto.getEmail().toLowerCase());
        user.setPassword(encoder.encode(addUserDto.getPassword()));
        user.setGender(addUserDto.getGender());
        user.setPhoneNumber(addUserDto.getPhoneNumber());
        user.setCountry(addUserDto.getCountry());
        user.setDob(addUserDto.getDob());
        user.setDateCreated(new Date());
        user.setEnabled(true);
        user.setStatus(Status.ACTIVE);
        user.setVerificationCode("Verified-By-Admin");


        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = addUserDto.getRole();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "moderator":
                        Role techRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(techRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return addUserDto;
    }

    @Override
    public User updateNotDeletedUserById(Long id, UpdateUserDto updateUserDto) {
        User user = isUserDeletedCheck(id);
        user.setName(updateUserDto.getName());
        user.setSurname(updateUserDto.getSurname());
        user.setEmail(updateUserDto.getEmail().toLowerCase());
        user.setPassword(encoder.encode(updateUserDto.getPassword()));
        user.setGender(updateUserDto.getGender());
        user.setPhoneNumber(updateUserDto.getPhoneNumber());
        user.setCountry(updateUserDto.getCountry());
        user.setDob(updateUserDto.getDob());
        user.setDateUpdated(new Date());

        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = updateUserDto.getRole();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "moderator":
                        Role techRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(techRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        userRepository.save(user);
        return user;
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
            return true;
        }

    }
//
//    public boolean verifyPasswordResetCode(String resetPasswordCode) {
//        PasswordResetToken passwordResetToken =
//                securityService.validatePasswordResetToken(resetPasswordCode);
//        return passwordResetToken != null;
//    }

    public boolean verifyPasswordResetCode(String resetPasswordCode) {
        PasswordResetToken passwordResetToken =
                securityService.validatePasswordResetToken(resetPasswordCode);
        if (passwordResetToken == null){
            return false;
        }else {
            passwordResetToken.setToken("123");
            passwordResetTokenRepository.save(passwordResetToken);
            return true;
        }
    }
    public void newPassword(String newPassword) {
        PasswordResetToken token = passwordResetTokenRepository.findByToken("123");
        User user = token.getUser();
        user.setPassword(encoder.encode(newPassword));
        passwordResetTokenRepository.delete(token);
        userRepository.save(user);
    }



    public String hardDeleteAllUsers() {

        userRepository.deleteAll();
        return "All Users deleted";
    }

    public String hardDeleteById(Long id) {
        User user = isUserDeletedCheck(id);
        userRepository.deleteById(id);
        return "User with id:" + id + " deleted";
    }


    public Optional<User> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName());
    }

    @Override
    public User isUserDeletedCheck(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find user with id: " + id));
        if(user.getDateDeleted() != null) {
            throw new NotFoundException("User with id: " + id + " was deleted!");
        }
        return user;
    }

    public User getCurrentUser() {
        Optional<User> user = getAuthentication();

        return user.get();
    }

    public User deleteCurrentUser() {
        Optional<User> user = getAuthentication();
        deleteUserById(user.get().getId());
        return user.get();

    }

    public User findUserByEmail(String email) {
        return  userRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("User not Found") );
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

//    public void changePassword(PasswordResetToken token, String newPassword) {
//        User user = token.getUser();
//        user.setPassword(encoder.encode(newPassword));
//        passwordResetTokenRepository.delete(token);
//        userRepository.save(user);
//    }
}
