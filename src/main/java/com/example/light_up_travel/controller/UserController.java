package com.example.light_up_travel.controller;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.model.UpdateUserDto;
import com.example.light_up_travel.model.AddUserDto;
import com.example.light_up_travel.payload.response.MessageResponse;
import com.example.light_up_travel.services.impl.SecurityServiceImpl;
import com.example.light_up_travel.services.impl.UserServiceImpl;
import com.example.light_up_travel.utils.EmailUtility;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserDto signupRequest) {
        try {
        userService.add(signupRequest);
            return ResponseEntity.ok(new MessageResponse("User is created successfully"));
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Update not deleted user by id")
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
        try{
            return ResponseEntity.ok(userService.getAllNotDeletedUsers());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(new MessageResponse("Deleted user with id = " + id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get not deleted user by id")
    @GetMapping("/{id}")
    ResponseEntity<?> getNotDeletedUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getNotDeletedUserById(id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
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
        try{
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Hard delete all users")
    @DeleteMapping("/harddeleteall")
    ResponseEntity<?> hardDeleteAllUsers() {
        try {
            userService.hardDeleteAllUsers();
            return ResponseEntity.ok(new MessageResponse("Deleted all user"));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Hard delete user by id")
    @DeleteMapping("/harddelete/{id}")
    ResponseEntity<?> hardDeleteUserById(@PathVariable Long id) {
        try {
            userService.hardDeleteById(id);
            return ResponseEntity.ok(new MessageResponse("Deleted user with id = " + id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }


    @Operation(summary = "Reset password request with email")
    @PostMapping("/resetPassword/{userEmail}")
    public ResponseEntity<?> resetPassword(@PathVariable String userEmail) throws MessagingException, UnsupportedEncodingException {
        try {
            User user = userService.findUserByEmail(userEmail);
            if (user == null) {
                throw new NotFoundException("User with Email " + userEmail + " not found");
            }
            String token = String.valueOf(new Random().nextInt(900000) + 100000);
            userService.createPasswordResetTokenForUser(user, token);
            EmailUtility.sendPasswordResetCode(token, user, mailSender);
            return ResponseEntity.ok("Check your email");
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Verify 6 digit reset password code")
    @PostMapping("/verifyResetPasswordCode/{code}")
    public ResponseEntity<?> verifyResetPasswordCode(@PathVariable String code) {
        try {
            if (userService.verifyPasswordResetCode(code)) {
                return ResponseEntity.ok("verify_success");
            } else {
                return ResponseEntity.ok("verify_fail");
            }
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
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

    @Operation(summary = "Create new password")
    @PutMapping("/changePassword/{newPassword}")
    public ResponseEntity<?> changePassword(@PathVariable String newPassword) {
        try {
            userService.newPassword(newPassword);
            return ResponseEntity.ok("Password changed");
        }
        catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }

    }

}
