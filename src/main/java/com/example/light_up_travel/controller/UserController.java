package com.example.light_up_travel.controller;

import com.example.light_up_travel.entity.PasswordResetToken;
import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.model.payload.UpdateUserDto;
import com.example.light_up_travel.model.payload.UserDto;
import com.example.light_up_travel.model.payload.response.MessageResponse;
import com.example.light_up_travel.services.impl.SecurityServiceImpl;
import com.example.light_up_travel.services.impl.UserServiceImpl;
import com.example.light_up_travel.utils.EmailUtility;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SecurityServiceImpl securityService;


    @Operation(summary = "Add new user")
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto signupRequest) {
        userService.add(signupRequest);
        return ResponseEntity.ok("User is created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@RequestBody UpdateUserDto updateUserDto, @PathVariable Long id) {
        try{
            return ResponseEntity.ok(userService.updateNotDeletedUserById(id, updateUserDto));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all not deleted users")
    @GetMapping("/allnotdeleted")
    ResponseEntity<?> getAllNotDeletedUsers() {
        return ResponseEntity.ok(userService.getAllNotDeletedUsers());
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @Operation(summary = "Get not deleted user by id")
    @GetMapping("/{id}")
    ResponseEntity<?> getNotDeletedUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getNotDeletedUserById(id));
    }

    @Operation(summary = "Get current user")
    @GetMapping("/me")
    ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(summary = "Get current user")
    @DeleteMapping("/me")
    ResponseEntity<?> deleteCurrentUser() {
        User user = userService.deleteCurrentUser();

        return ResponseEntity.ok("user with id " + user.getId() + " deleted");
    }

    @Operation(summary = "Get all users")
    @GetMapping("/all")
    ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Hard delete all users")
    @DeleteMapping("/harddeleteall")
    ResponseEntity<?> hardDeleteAllUsers() {
        return ResponseEntity.ok(userService.hardDeleteAllUsers());
    }

    @Operation(summary = "Hard delete user by id")
    @DeleteMapping("/harddelete/{id}")
    ResponseEntity<?> hardDeleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.hardDeleteById(id));
    }


    @PostMapping("/resetPassword/{userEmail}")
    public ResponseEntity<?> resetPassword(@PathVariable String userEmail) throws MessagingException, UnsupportedEncodingException {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User with Email " + userEmail + " not found");
        }
        String token = String.valueOf(new Random().nextInt(900000) + 100000);
        userService.createPasswordResetTokenForUser(user, token);
        EmailUtility.sendPasswordResetCode(token, user, mailSender );
        return ResponseEntity.ok("Check your email");
    }

    @GetMapping("/verifyResetPasswordCode/{code}")
    public ResponseEntity<?> verifyResetPasswordCode(@PathVariable String code) {
        if (userService.verifyPasswordResetCode(code)) {
            return ResponseEntity.ok("verify_success");
        } else {
            return ResponseEntity.ok("verify_fail");
        }
    }

//    @PostMapping("/changePassword")
//    public ResponseEntity<?> changePassword(@RequestParam("token") String token, String newPassword) {
//        try {
//            PasswordResetToken passwordResetToken =
//                    securityService.validatePasswordResetToken(token);
//
//            userService.changePassword(passwordResetToken, newPassword);
//            return ResponseEntity.ok("Password changed");
//        }
//        catch (RuntimeException notFoundException){
//            return ResponseEntity.status(404).body(notFoundException.getMessage());
//        }
//        catch (Exception e){
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//
//    }
    @PostMapping("/changePassword/{newPassword}")
    public ResponseEntity<?> changePassword(@PathVariable String newPassword) {
        try {
            userService.newPassword(newPassword);
            return ResponseEntity.ok("Password changed");
        }
        catch (RuntimeException notFoundException){
            return ResponseEntity.status(404).body(notFoundException.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
