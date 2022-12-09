package com.example.light_up_travel.controller;

import com.example.light_up_travel.model.CreatePostDTO;
import com.example.light_up_travel.model.GetPostDTO;
import com.example.light_up_travel.model.UpdatePostDTO;
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
    public ResponseEntity<Void> createNewPost(@RequestPart CreatePostDTO createPostDTO,@RequestPart MultipartFile multipartFile) throws  Exception{
        postService.createNewPost(createPostDTO,multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "approve post(for admin panel's cencership)")
    @PostMapping("/approve/post")
    public ResponseEntity<GetPostDTO> approvePost(@RequestBody GetPostDTO getPostDTO){
        postService.approvePost(getPostDTO.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "disapprove post(for admin panel's cencership)")
    @PostMapping("/disapprove/post")
    public ResponseEntity<GetPostDTO> disApprovePost(@RequestBody GetPostDTO getPostDTO){
        postService.disApprovePost(getPostDTO.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "edit post  for client side")
    @PutMapping("/edit/post/{id}")  //
    public ResponseEntity<Void> updatePost(@RequestPart UpdatePostDTO updatePostDTO, @RequestPart MultipartFile multipartFile) throws Exception {
        postService.updatePost(updatePostDTO, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all disapproved Posts(for admin panel's cencership)")
    @GetMapping("/list-Of-disapproved")
    public List<GetPostDTO> getAllDisApproved(@RequestParam Integer page, @RequestParam Integer size){
        return postService.getAllDisApprovedPosts(page, size);
    }

    @Operation(summary = "Get all Posts(for admin panel's cencership)")
    @GetMapping("/list")
    public List<GetPostDTO> getAll(@RequestParam Integer page, @RequestParam Integer size){
        return postService.getAllActivePosts(page, size);
    }

    @Operation(summary = "delete post by id - only for client side")
    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<Void> deletePostById(@RequestBody GetPostDTO getPostDTO){
        postService.deletePostById(getPostDTO.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "like Post")
    @PostMapping("/like/{postId}")
    public void like(@PathVariable Long postId){
        postService.LikePost(postId);
    }
}
