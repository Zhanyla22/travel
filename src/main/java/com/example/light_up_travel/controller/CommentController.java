package com.example.light_up_travel.controller;

import com.example.light_up_travel.dto.CommentsDTO;
import com.example.light_up_travel.payload.response.MessageResponse;
import com.example.light_up_travel.services.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentsService commentsService;


    @Operation(summary = "Add new comment to a post")
    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CommentsDTO comment) {
        try{
            commentsService.insertComment(comment);
            return ResponseEntity.ok(new MessageResponse("Comment is added successfully"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get not deleted comment by id")
    @GetMapping("/{id}")
    ResponseEntity<?> getNotDeletedCommentById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(commentsService.getCommentById(id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all not deleted comments of single post")
    @GetMapping("/all-not-deleted-comments-by-post-id/{postId}")
    ResponseEntity<?> getAllNotDeletedCommentsByPostId(@PathVariable Long postId) {
        try{
            return ResponseEntity.ok(commentsService.getAllNotDeletedCommentsByPostId(postId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all not deleted comments of a user")
    @GetMapping("/all-not-deleted-comments-by-user-id/{userId}")
    ResponseEntity<?> getAllNotDeletedCommentsByUserId(@PathVariable Long userId){
        try {
            return ResponseEntity.ok(commentsService.getAllNotDeletedCommentsByUserId(userId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all deleted comments of a post which are deleted by users")
    @GetMapping("/all-deleted-comments-by-users-with-post-id/{postId}")
    ResponseEntity<?> getAllDeletedCommentsByUsersWithPostId(@PathVariable Long postId) {
        try{
            return ResponseEntity.ok(commentsService.getAllDeletedCommentsByUsersWithPostId(postId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all deleted comments of a post which are deleted by admins")
    @GetMapping("/all-deleted-comments-by-admins-with-post-id/{postId}")
    ResponseEntity<?> getAllDeletedCommentsByAdminWithPostId(@PathVariable Long postId){
        try {
            return ResponseEntity.ok(commentsService.getAllDeletedCommentsByAdminWithPostId(postId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Delete not deleted comment by id for user")
    @DeleteMapping("/delete-comment-for-user/{commentId}")
    ResponseEntity<?> deleteNotDeletedCommentByIdForUser(@PathVariable Long commentId){
        try {
            return ResponseEntity.ok(commentsService.deleteCommentByIdForUser(commentId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Delete not deleted comment by id for admin")
    @DeleteMapping("/delete-comment-for-admin/{commentId}")
    ResponseEntity<?> deleteNotDeletedCommentByIdForAdmin(@PathVariable Long commentId){
        try {
            return ResponseEntity.ok(commentsService.deleteCommentByIdForAdmin(commentId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
}
