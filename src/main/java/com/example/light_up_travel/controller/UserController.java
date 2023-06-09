package com.example.light_up_travel.controller;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.dto.UpdateUserDTO;
import com.example.light_up_travel.dto.AddUserDTO;
import com.example.light_up_travel.dto.UserProfileDTO;
import com.example.light_up_travel.payload.response.MessageResponse;
import com.example.light_up_travel.services.SecurityService;
import com.example.light_up_travel.services.UserService;
import com.example.light_up_travel.utils.EmailUtility;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SecurityService securityService;


    @Operation(summary = "Add new user")
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserDTO signupRequest) {
        try {
            userService.add(signupRequest);
            return ResponseEntity.ok(new MessageResponse("User is created successfully"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Update not deleted user by id only for admins")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@RequestBody UpdateUserDTO updateUserDto, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.updateNotDeletedUserById(id, updateUserDto));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
    @Operation(summary = "Update not deleted user's profile by id / 1- request")
    @PutMapping("/update-profile/{id}")
    public Long updateUserProfileById(@RequestBody UserProfileDTO userProfileDto) throws Exception{

        return userService.updateProfilePageById(userProfileDto);
//        try {
//            return ResponseEntity.ok(userService.updateProfilePageById(userProfileDto));
//        } catch (NotFoundException nfe) {
//            throw new NotFoundException(nfe.getMessage());
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
//        }
    }

    @Operation(summary = "Update not deleted user's profile by id / 2- request")
    @PutMapping("/update-profile-pic/{id}")
    public ResponseEntity<String> updateProfileUrl(@PathVariable Long id, @RequestPart MultipartFile multipartFile) throws Exception{

        return ResponseEntity.ok(userService.updateProfileUrl(id, multipartFile));
    }

    @Operation(summary = "Get all not deleted users")
    @GetMapping("/all-not-deleted")
    ResponseEntity<?> getAllNotDeletedUsers() {
        try {
            return ResponseEntity.ok(userService.getAllNotDeletedUsers());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }


    @Operation(summary = "Get all deleted users")
    @GetMapping("/archive")
    ResponseEntity<?> getAllDeletedUsers() {
        try {
            return ResponseEntity.ok(userService.getAllDeletedUsers());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/delete/{id}")
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


    @Operation(summary = "Get all users")
    @GetMapping("/all")
    ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all not deleted users with role: moderator")
    @GetMapping("/all-moderators")
    ResponseEntity<?> getAllNotDeletedModerators() {
        try {
            return ResponseEntity.ok(userService.getAllNotDeletedModerators());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }


    @Operation(summary = "Get all not deleted users dto with role: users")
    @GetMapping("/all-users")
    ResponseEntity<?> getAllUserRoles() {
            return ResponseEntity.ok(userService.getAllUsersWithUserRole());
}

    @Operation(summary = "Hard delete all users")
    @DeleteMapping("/hard-delete-all")
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
    @DeleteMapping("/hard-delete/{id}")
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
    @PostMapping("/reset-password/{userEmail}")
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
    @PostMapping("/verify-reset-password/{code}")
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


    @Operation(summary = "Create new password")
    @PostMapping("/change-password/{newPassword}")
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
