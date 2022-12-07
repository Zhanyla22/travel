package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.entity.PasswordResetToken;
import com.example.light_up_travel.entity.Role;
import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.enums.ERole;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.EmailAlreadyExistsException;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.mapper.BasicUserMapper;
import com.example.light_up_travel.model.BasicUserDto;
import com.example.light_up_travel.model.UpdateUserDto;
import com.example.light_up_travel.model.AddUserDto;
import com.example.light_up_travel.repository.PasswordResetTokenRepository;
import com.example.light_up_travel.repository.RoleRepository;
import com.example.light_up_travel.repository.UserRepository;
import com.example.light_up_travel.services.UserService;
import com.example.light_up_travel.utils.EmailUtility;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SecurityServiceImpl securityService;
    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;
    private final RoleRepository roleRepository;
    private final MentorServiceImpl mentorService;

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

    @Override
    public List<User> getAllNotDeletedModerators() {
        return userRepository.findAllNotDeletedModerators();
    }


    @Override
    public List<User> getAllDeletedUsers() {
        return userRepository.findAllDeletedUsers();
    }

    public User getNotDeletedUserById(Long id) {
        User user = isUserDeletedCheck(id);
        return userRepository.findNotDeletedUserById(id);
    }
    @Override
    public List<BasicUserDto> getAllUsersWithUserRole() {
        List<User> users = userRepository.findAllUserRoles();
        List<BasicUserDto> basicUserDtos = new ArrayList<>();

        for(User user: users){
            BasicUserDto basicUserDto = BasicUserMapper.basicUserToUserDTO(user);
            try {
                User mentor = isUserDeletedCheck(mentorService.getMentorById(user.getId()).getMentorId());
                basicUserDto.setMentorName(mentor.getName() + " " + mentor.getSurname());
            }
            catch (NotFoundException ignored) {
            }
            basicUserDtos.add(basicUserDto);
        }
        return basicUserDtos;
    }

    public void deleteUserById(Long id) {
        User user = isUserDeletedCheck(id);
        user.setDateDeleted(new Date());
        user.setEnabled(false);
        user.setStatus(Status.DELETED_BY_ADMIN);
        user.setVerificationCode(null);
        userRepository.save(user);
    }

    @Override
    public AddUserDto add(AddUserDto addUserDto) throws MessagingException {
        String password = RandomString.make(8);
        if (addUserDto.getEmail() != null &
                userRepository.existsByEmail(addUserDto.getEmail().toLowerCase())) {
            throw new EmailAlreadyExistsException("User with email: " + addUserDto.getEmail() + " - is already exist");
        }
        User user = new User();
        user.setName(addUserDto.getName());
        user.setSurname(addUserDto.getSurname());
        user.setEmail(addUserDto.getEmail().toLowerCase());
        user.setPassword(encoder.encode(password));
        user.setDateCreated(new Date());
        user.setEnabled(true);
        user.setStatus(Status.ACTIVE);
        user.setVerificationCode("Verified-By-Admin");

        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = addUserDto.getRole();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new NotFoundException("Error: Role is not found."));
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
        EmailUtility.sendLoginAndPassword(user.getEmail(), password, javaMailSender);
        return addUserDto;
    }

    @Override
    public User updateNotDeletedUserById(Long id, UpdateUserDto updateUserDto) {
        User user = isUserDeletedCheck(id);
        if (updateUserDto.getName() != null){
        user.setName(updateUserDto.getName());}
        if (updateUserDto.getSurname() != null){
        user.setSurname(updateUserDto.getSurname());}
        if (updateUserDto.getEmail() != null){
        user.setEmail(updateUserDto.getEmail().toLowerCase());}
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



    public void hardDeleteAllUsers() {
        userRepository.deleteAll();
    }

    public void hardDeleteById(Long id) {
        User user = isUserDeletedCheck(id);
        userRepository.deleteById(id);
    }


    public User getUserByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new RuntimeException("User is not logged in!"));
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

    public User findUserByEmail(String email) {
        return  userRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("User not Found") );
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

}
