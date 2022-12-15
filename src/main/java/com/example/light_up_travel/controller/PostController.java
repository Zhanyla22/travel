package com.example.light_up_travel.controller;

import com.example.light_up_travel.dto.CreatePostDTO;
import com.example.light_up_travel.dto.GetPostDTO;
import com.example.light_up_travel.dto.UpdatePostDTO;
import com.example.light_up_travel.services.PostService;
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

    @Operation(summary = "Create new Post 1 req")
    @PostMapping("/create/new-post")
    public ResponseEntity<Long> createNewPost(@RequestBody CreatePostDTO createPostDTO) throws  Exception{
        return postService.createNewPost(createPostDTO);
    }

    @Operation(summary = "Create new Post image 2 req")
    @PostMapping("/create/new-post-image/{postId}")
    public ResponseEntity<String> createNewPost(@PathVariable Long postId, @RequestPart MultipartFile multipartFile) throws  Exception{
        return ResponseEntity.ok(postService.saveImageForPost(postId,multipartFile));
    }

    @Operation(summary = "approve post(for admin panel's cencership)")
    @PostMapping("/approve-post/{postId}")
    public ResponseEntity<GetPostDTO> approvePost(@PathVariable Long postId){
        postService.approvePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "disapprove post(for admin panel's cencership)")
    @PostMapping("/disapprove-post/{postId}")
    public ResponseEntity<GetPostDTO> disApprovePost(@PathVariable Long postId){
        postService.disApprovePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "edit post  for client side 1 req")
    @PutMapping("/edit-post")  //
    public ResponseEntity<Long> updatePost(@RequestBody UpdatePostDTO updatePostDTO) throws Exception {
      return postService.updatePost(updatePostDTO);
    }

    @Operation(summary = "edit post  for client side 2 req")
    @PutMapping("/edit-post-image/{postId}")  //
    public ResponseEntity<String> updatePostImage(@PathVariable Long postId, @RequestPart MultipartFile multipartFile) throws Exception {
        return ResponseEntity.ok(postService.updateImageForPost(postId, multipartFile));
    }

    @Operation(summary = "Get all disapproved Posts(for admin panel's cencership)")
    @GetMapping("/list-of-disapproved-posts")
    public List<GetPostDTO> getAllDisApproved(@RequestParam Integer page, @RequestParam Integer size){
        return postService.getAllDisApprovedPosts(page, size);
    }

    @Operation(summary = "Get all Posts(for admin panel's cencership)")
    @GetMapping("/list-of-approved-posts")
    public List<GetPostDTO> getAll(@RequestParam Integer page, @RequestParam Integer size){
        return postService.getAllActivePosts(page, size);
    }

    @Operation(summary = "Get all Posts of user(for user only)")
    @GetMapping("/list-of-posts/")
    public List<GetPostDTO> getAllUserPosts(){
        return postService.getAllPostsByUser();
    }

    @Operation(summary = "Get all pending Posts(for admin panel's cencership)")
    @GetMapping("/list-of-pending-posts")
    public List<GetPostDTO> getAllPending(){
        return postService.getAllPendingPosts();
    }
    @Operation(summary = "delete post by id - only for client side")
    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "like Post")
    @PostMapping("/like/{postId}")
    public void like(@PathVariable Long postId){
        postService.likePost(postId);
    }
}
