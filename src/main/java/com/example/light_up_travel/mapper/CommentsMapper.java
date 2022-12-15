package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.CommentsDTO;
import com.example.light_up_travel.entity.Comments;

public class CommentsMapper {

    public static CommentsDTO CommentsToCommentsDto(Comments comments){
        CommentsDTO commentsDto = new CommentsDTO();
        commentsDto.setId(comments.getId());
        commentsDto.setComment(comments.getComment());
        commentsDto.setUserId(BasicUserMapper.basicUserToUserDTO(comments.getUser()));
        commentsDto.setPostId(BasicPostMapper.PostEntityToBasicPostDto(comments.getPost()));
        return commentsDto;
    }

    public static Comments CommentsToComments(CommentsDTO commentsDto){
        Comments comments = new Comments();
        comments.setId(commentsDto.getId());
        comments.setComment(commentsDto.getComment());
        comments.setUser(BasicUserMapper.basicUserDtoToUser(commentsDto.getUserId()));
        comments.setPost(BasicPostMapper.BasicPostDtoToPostEntity(commentsDto.getPostId()));
        return comments;
    }
}
