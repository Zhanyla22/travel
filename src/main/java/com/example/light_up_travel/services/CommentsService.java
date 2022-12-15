package com.example.light_up_travel.services;

import com.example.light_up_travel.dto.CommentsDTO;
import com.example.light_up_travel.entity.Comments;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.mapper.BasicPostMapper;
import com.example.light_up_travel.mapper.CommentsMapper;
import com.example.light_up_travel.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;

    private final UserService userService;

    private final PostService postService;

    public List<CommentsDTO> getAllNotDeletedCommentsByPostId(Long postId){
        Iterable<Comments> comments = commentsRepository.getAllNotDeletedCommentsByPostId(postId);

        List<CommentsDTO> commentDto = new ArrayList<>();

        for (Comments comment : comments){
            commentDto.add(CommentsMapper.CommentsToCommentsDto(comment));
        }
        return commentDto;
    }

    public List<CommentsDTO> getAllNotDeletedCommentsByUserId(Long userId){
        Iterable<Comments> comments = commentsRepository.getAllNotDeletedCommentsByUserId(userId);

        List<CommentsDTO> commentDto = new ArrayList<>();

        for (Comments comment : comments){
            commentDto.add(CommentsMapper.CommentsToCommentsDto(comment));
        }
        return commentDto;
    }

    public List<CommentsDTO> getAllDeletedCommentsByUsersWithPostId(Long postId){
        Iterable<Comments> comments = commentsRepository.getAllDeletedCommentsByUsersWithPostId(postId);

        List<CommentsDTO> commentDto = new ArrayList<>();

        for (Comments comment : comments){
            commentDto.add(CommentsMapper.CommentsToCommentsDto(comment));
        }
        return commentDto;
    }

    public List<CommentsDTO> getAllDeletedCommentsByAdminWithPostId(Long postId){
        Iterable<Comments> comments = commentsRepository.getAllDeletedCommentsByAdminWithPostId(postId);

        List<CommentsDTO> commentDto = new ArrayList<>();

        for (Comments comment : comments){
            commentDto.add(CommentsMapper.CommentsToCommentsDto(comment));
        }
        return commentDto;
    }

    public CommentsDTO insertComment(CommentsDTO commentsDto){
        Comments comment = new Comments();
        comment.setComment(commentsDto.getComment());
        comment.setUser(userService.getUserByAuthentication());
        comment.setPost(BasicPostMapper.BasicPostDtoToPostEntity(commentsDto.getPostId()));
        comment.setDateCreated(LocalDateTime.now());
        comment.setStatus(Status.ACTIVE);
        return CommentsMapper.CommentsToCommentsDto(commentsRepository.save(comment));
    }

    public CommentsDTO getCommentById(Long commentId){
        Comments comment = isCommentDeletedCheck(commentId);
        return CommentsMapper.CommentsToCommentsDto(comment);
    }

    public String deleteCommentByIdForUser(Long commentId){
        Comments comment = isCommentDeletedCheck(commentId);
        if (comment.getUser() == userService.getUserByAuthentication()) {
            comment.setStatus(Status.DELETED_BY_USER);
            comment.setDateDeleted(LocalDateTime.now());
            commentsRepository.save(comment);

            return "Comment with id: " + commentId + " deleted successfully";
        }
        else throw new NotFoundException("User doesn't have a a comment with id: " + commentId);
    }

    public String deleteCommentByIdForAdmin(Long commentId){
        try {
        Comments comment = isCommentDeletedCheck(commentId);
        comment.setStatus(Status.DELETED_BY_ADMIN);
        comment.setDateDeleted(LocalDateTime.now());
        commentsRepository.save(comment);
        return "Comment with id: " + commentId + " deleted successfully";
    } catch (Exception e) {
            throw new NotFoundException("Could not find comment with id: " + commentId);
        }
    }

    public Comments isCommentDeletedCheck(Long id) {
        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find comment with id: " + id));
        if(comment.getDateDeleted() != null) {
            throw new NotFoundException("Comment with id: " + id + " was deleted!");
        }
        return comment;
    }

}
