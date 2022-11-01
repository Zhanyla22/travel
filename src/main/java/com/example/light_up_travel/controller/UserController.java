package com.example.light_up_travel.controller;

import com.example.light_up_travel.model.PasswordResetToken;
import com.example.light_up_travel.model.User;
import com.example.light_up_travel.services.impl.SecurityServiceImpl;
import com.example.light_up_travel.services.impl.UserServiceImpl;
import com.example.light_up_travel.utils.EmailUtility;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

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


    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("userEmail") String userEmail) throws MessagingException, UnsupportedEncodingException {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User with Email " + userEmail + " not found");
        }
        String token = String.valueOf(new Random().nextInt(900000) + 100000);
        userService.createPasswordResetTokenForUser(user, token);
        EmailUtility.sendPasswordResetCode(token, user, mailSender );
        return ResponseEntity.ok("Check your email");
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam("token") String token, String newPassword) {
        try {
            PasswordResetToken passwordResetToken =
                    securityService.validatePasswordResetToken(token);

            userService.changePassword(passwordResetToken, newPassword);
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
