package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.Forum;
import com.example.light_up_travel.model.ForumDto;
import com.example.light_up_travel.model.SendForumDTO;

import java.util.List;
import java.util.Optional;

public interface ForumService {

    List<ForumDto> getAllNotDeletedForums();

    List<ForumDto> getAllDeletedForums();

    List<ForumDto> getAllForums();

    ForumDto insert(SendForumDTO sendForumDTO);

    ForumDto updateNotDeletedForumById(Long id);

    void deleteNotDeletedForumById(Long id);

    Forum isForumDeletedCheck(Long id);

    void hardDeleteAllForums();

    void hardDeleteById(Long id);

    ForumDto getNotDeletedForumById(Long id);
}
