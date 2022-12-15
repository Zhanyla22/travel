package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.Forum;
import com.example.light_up_travel.dto.ForumDTO;

public class ForumMapper {
        public static Forum forumDtoToForum(ForumDTO forumDto){
            Forum forum = new Forum();
            forum.setId(forumDto.getId());
            forum.setDescription(forumDto.getDescription());
            forum.setUser(BasicUserMapper.basicUserDtoToUser(forumDto.getUser()));
            forum.setStatus(forumDto.getStatus());
            forum.setDateCreated(forumDto.getDateCreated());
            return forum;
        }

        public static ForumDTO ForumToForumDTO(Forum forum){
            ForumDTO forumDTO = new ForumDTO();
            forumDTO.setId((forum.getId()));
            forumDTO.setDescription(forum.getDescription());
            forumDTO.setUser(BasicUserMapper.basicUserToUserDTO(forum.getUser()));
            forumDTO.setStatus(forum.getStatus());
            forumDTO.setDateCreated(forum.getDateCreated());
            return forumDTO;
        }
    }