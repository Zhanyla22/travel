package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.entity.PasswordResetToken;
import com.example.light_up_travel.entity.Role;
import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.enums.ERole;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.model.payload.UserDto;
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
    public UserDto add(UserDto userDto) {
        if (userDto.getEmail() != null &
                userRepository.existsByEmail(userDto.getEmail().toLowerCase())) {
            throw new RuntimeException("User with email: " + userDto.getEmail() + " - is already exist");
        }

        // Create new user's account
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail().toLowerCase());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setGender(userDto.getGender());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCountry(userDto.getCountry());
        user.setDob(userDto.getDob());
        user.setDateCreated(new Date());
        user.setEnabled(true);
        user.setStatus(Status.ACTIVE);
        user.setVerificationCode("Verified");


        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = userDto.getRole();

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
        return userDto;
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


    public String hardDeleteAllUsers() {

        userRepository.deleteAll();
        return "All Users deleted";
    }

    public String hardDeleteById(Long id) {

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
                .orElseThrow(() -> new RuntimeException("Could not find user with id: " + id));
        if(user.getDateDeleted() != null) {
            throw new RuntimeException("User with id: " + id + " was deleted!");
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
                orElseThrow(() -> new RuntimeException("User not Found") );
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    public void changePassword(PasswordResetToken token, String newPassword) {
        User user = token.getUser();
        user.setPassword(encoder.encode(newPassword));
        passwordResetTokenRepository.delete(token);
        userRepository.save(user);
    }
}
