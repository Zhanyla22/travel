package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.Forum;
import com.example.light_up_travel.dto.ForumDto;

public class ForumMapper {
        public static Forum forumDtoToForum(ForumDto forumDto){
            Forum forum = new Forum();
            forum.setId(forumDto.getId());
            forum.setDescription(forumDto.getDescription());
            forum.setUser(BasicUserMapper.basicUserDtoToUser(forumDto.getUser()));
            forum.setStatus(forumDto.getStatus());
            forum.setDateCreated(forumDto.getDateCreated());
            return forum;
        }

        public static ForumDto ForumToForumDTO(Forum forum){
            ForumDto forumDTO = new ForumDto();
            forumDTO.setId((forum.getId()));
            forumDTO.setDescription(forum.getDescription());
            forumDTO.setUser(BasicUserMapper.basicUserToUserDTO(forum.getUser()));
            forumDTO.setStatus(forum.getStatus());
            forumDTO.setDateCreated(forum.getDateCreated());
            return forumDTO;
        }
    }