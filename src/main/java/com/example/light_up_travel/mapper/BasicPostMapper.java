package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.BasicPostDto;
import com.example.light_up_travel.entity.Post;

public class BasicPostMapper {
    public static BasicPostDto PostEntityToBasicPostDto(Post post) {
        BasicPostDto basicPostDto = new BasicPostDto();
        basicPostDto.setId(post.getId());
        basicPostDto.setDescription(post.getDescription());
        return basicPostDto;
    }

    public static Post BasicPostDtoToPostEntity(BasicPostDto basicPostDto) {
        Post post = new Post();
        post.setId(basicPostDto.getId());
        post.setDescription(basicPostDto.getDescription());
        return post;
    }
}
