package com.example.light_up_travel.services.serviceZ;

import com.example.light_up_travel.entity.Post;
import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.model.CreatePostDTO;
import com.example.light_up_travel.repository.PostRepository;
import com.example.light_up_travel.repository.UserRepository;
import com.example.light_up_travel.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<Long> createNewPostWithoutImage(CreatePostDTO createPostDTO) throws Exception{
        try {

            User user = userRepository.getById(userService.getUserByAuthentication().getId());
            Post newPost = new Post();
            newPost.setDescription(createPostDTO.getDescription());
            newPost.setDateCreated(LocalDate.now());
            newPost.setStatus(Status.WAITING_FOR_APPROVE);
            newPost.setUser(user);
            newPost = postRepository.saveAndFlush(newPost);
            return new ResponseEntity<Long>(newPost.getId(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Long>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    }
