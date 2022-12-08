package com.example.light_up_travel.controller;

import com.example.light_up_travel.model.CreatePostDTO;
import com.example.light_up_travel.model.GetPostDTO;
import com.example.light_up_travel.services.serviceZ.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create new Post")
    @PostMapping("/create/new-post")
    public ResponseEntity<Void> createNewPost(@RequestParam CreatePostDTO createPostDTO,@RequestParam MultipartFile multipartFile) throws  Exception{
        postService.createNewPost(createPostDTO,multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "approve post(for admin panel's cencership)")
    @PostMapping("/approve/post")
    public ResponseEntity<GetPostDTO> approvePost(@RequestBody GetPostDTO getPostDTO){
        postService.approvePost(getPostDTO.getId());
        return new ResponseEntity<GetPostDTO>(HttpStatus.OK);
    }
    @Operation(summary = "approve post(for admin panel's cencership)")
    @GetMapping("/list")
    public List<GetPostDTO> getAll(@RequestParam Integer page, @RequestParam Integer size){
        return postService.getAllPosts(page, size);
    }

}
