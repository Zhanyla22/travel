package com.example.light_up_travel.controller;

import com.example.light_up_travel.model.ForumDto;
import com.example.light_up_travel.payload.response.MessageResponse;
import com.example.light_up_travel.services.impl.ForumServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumServiceImpl forumService;

//    @Operation(summary = "Add new forum")
//    @PostMapping("/add")
//    public ResponseEntity<?> addForum(@Valid @RequestBody ForumDto forumDto) {
//        try{
//            forumService.insert(forumDto);
//            return ResponseEntity.ok(new MessageResponse("Forum is added successfully"));
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
//        }
//    }

    @Operation(summary = "Get all forums")
    @GetMapping("/all")
    ResponseEntity<?> getAllForums() {
        try{
            return ResponseEntity.ok(forumService.getAllForums());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all not deleted forums")
    @GetMapping("/all-not-deleted")
    ResponseEntity<?> getAllNotDeletedForums() {
        try{
            return ResponseEntity.ok(forumService.getAllNotDeletedForums());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
    @Operation(summary = "Get all deleted forums")
    @GetMapping("/all-deleted")
    ResponseEntity<?> getAllDeletedForums() {
        try{
            return ResponseEntity.ok(forumService.getAllDeletedForums());
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
    @Operation(summary = "Get not deleted forum by id")
    @GetMapping("/{id}")
    ResponseEntity<?> getNotDeletedForumById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(forumService.getNotDeletedForumById(id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }


    @Operation(summary = "Delete forum by id")
    @DeleteMapping("delete/{id}")
    ResponseEntity<?> deleteForumById(@PathVariable Long id) {
        try {
            forumService.deleteNotDeletedForumById(id);
            return ResponseEntity.ok(new MessageResponse("Deleted forum with id = " + id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
    @Operation(summary = "Hard delete all forums")
    @DeleteMapping("/hard-delete-all")
    ResponseEntity<?> hardDeleteAllForums() {
        try {
            forumService.hardDeleteAllForums();
            return ResponseEntity.ok(new MessageResponse("Hard deleted all forums"));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Hard delete forum by id")
    @DeleteMapping("/hard-delete/{id}")
    ResponseEntity<?> hardDeleteForumById(@PathVariable Long id) {
        try {
            forumService.hardDeleteById(id);
            return ResponseEntity.ok(new MessageResponse("Hard deleted forum with id = " + id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
}
