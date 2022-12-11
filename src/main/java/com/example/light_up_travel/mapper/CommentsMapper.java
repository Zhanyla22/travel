package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.CommentsDto;
import com.example.light_up_travel.entity.Comments;

public class CommentsMapper {

    public static CommentsDto CommentsToCommentsDto(Comments comments){
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(comments.getId());
        commentsDto.setComment(comments.getComment());
        commentsDto.setUserId(BasicUserMapper.basicUserToUserDTO(comments.getUser()));
        commentsDto.setPostId(BasicPostMapper.PostEntityToBasicPostDto(comments.getPost()));
        return commentsDto;
    }

    public static Comments CommentsToComments(CommentsDto commentsDto){
        Comments comments = new Comments();
        comments.setId(commentsDto.getId());
        comments.setComment(commentsDto.getComment());
        comments.setUser(BasicUserMapper.basicUserDtoToUser(commentsDto.getUserId()));
        comments.setPost(BasicPostMapper.BasicPostDtoToPostEntity(commentsDto.getPostId()));
        return comments;
    }
}
