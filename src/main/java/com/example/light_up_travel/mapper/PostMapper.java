package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.Post;
import com.example.light_up_travel.dto.GetPostDTO;
import com.example.light_up_travel.dto.UserForPostDTO;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public static GetPostDTO PostEntityToPostDto(Post post, Long countLikes, boolean like) {
        GetPostDTO postDTO = new GetPostDTO();
        postDTO.setId(post.getId());
        postDTO.setDescription(post.getDescription());
        postDTO.setFilePath(post.getFilePath());
        postDTO.setCounter(countLikes);
        postDTO.setUser(
                new UserForPostDTO(post.getUser().getId(),
                        post.getUser().getProfileUrl(),
                        post.getUser().getName(),
                        post.getUser().getSurname()));
        postDTO.setLike(like);
        return postDTO;
    }
}
