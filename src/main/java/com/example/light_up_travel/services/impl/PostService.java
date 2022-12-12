package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.entity.Likes;
import com.example.light_up_travel.entity.Post;
import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.mapper.PostMapper;
import com.example.light_up_travel.dto.CreatePostDTO;
import com.example.light_up_travel.dto.GetPostDTO;
import com.example.light_up_travel.dto.UpdatePostDTO;
import com.example.light_up_travel.repository.LikesRepository;
import com.example.light_up_travel.repository.PostRepository;
import com.example.light_up_travel.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    private final FileUploadService fileUploadService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final LikesRepository likesRepository;

    public PostService(FileUploadService fileUploadService, PostRepository postRepository, UserRepository userRepository, UserServiceImpl userService, LikesRepository likesRepository) {
        this.fileUploadService = fileUploadService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.likesRepository = likesRepository;
    }


    public ResponseEntity<Void> createNewPost(CreatePostDTO createPostDTO, MultipartFile multipartFile) throws Exception {
        try {

            User user = userRepository.findNotDeletedUserById(userService.getUserByAuthentication().getId());
            Post newPost = new Post();
            newPost.setDescription(createPostDTO.getDescription());
            newPost.setDateCreated(LocalDate.now());
            newPost.setFilePath(fileUploadService.saveFile(multipartFile));
            newPost.setStatus(Status.WAITING_FOR_APPROVE);
            newPost.setUser(user);
            postRepository.save(newPost);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public void approvePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post with " + postId + "not found")
        );
        post.setStatus(Status.ACTIVE);
        postRepository.save(post);
    }

    public void disApprovePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post with " + postId + "not found")
        );
        post.setStatus(Status.DISAPPROVED);
        postRepository.save(post);
    }

    public List<GetPostDTO> getAllDisApprovedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByStatus(Status.DISAPPROVED, pageable);
        List<GetPostDTO> result = new ArrayList<>();
        for (Post p : posts.getContent())
            result.add(PostMapper.PostEntityToPostDto(p, likesRepository.countById(p.getId())));
        return result;
    }

    public List<GetPostDTO> getAllActivePosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByStatus(Status.ACTIVE, pageable);
        List<GetPostDTO> result = new ArrayList<>();
        for (Post p : posts.getContent())
            result.add(PostMapper.PostEntityToPostDto(p, likesRepository.countById(p.getId())));
        return result;
    }

    public ResponseEntity<Void> updatePost(UpdatePostDTO updatePostDTO, MultipartFile multipartFile) throws Exception {
        try {
            Post post = postRepository.findById(updatePostDTO.getId()).orElseThrow(
                    () -> new Exception("Post with  id = " + updatePostDTO.getId() + " not found")
            );
            post.setDescription(updatePostDTO.getDescription());
            post.setDateUpdated(LocalDateTime.now()); //check it
            if (!Objects.isNull(multipartFile))
                post.setFilePath(fileUploadService.saveFile(multipartFile));
            postRepository.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<Void> deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found " + id + " Post")
        );
        post.setStatus(Status.DELETED_BY_USER);
        post.setDateDeleted(LocalDateTime.now());
        postRepository.save(post);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public void LikePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Not found " + postId + " Post")
        );
        Likes likes = new Likes();
        likes.setPost(post);
        likes.setUser(userRepository.getById(userService.getUserByAuthentication().getId()));
        likesRepository.save(likes);
    }
}
