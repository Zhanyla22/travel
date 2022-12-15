package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.BasicPostDTO;
import com.example.light_up_travel.entity.Post;

public class BasicPostMapper {
    public static BasicPostDTO PostEntityToBasicPostDto(Post post) {
        BasicPostDTO basicPostDto = new BasicPostDTO();
        basicPostDto.setId(post.getId());
        basicPostDto.setDescription(post.getDescription());
        return basicPostDto;
    }

    public static Post BasicPostDtoToPostEntity(BasicPostDTO basicPostDto) {
        Post post = new Post();
        post.setId(basicPostDto.getId());
        post.setDescription(basicPostDto.getDescription());
        return post;
    }
}
