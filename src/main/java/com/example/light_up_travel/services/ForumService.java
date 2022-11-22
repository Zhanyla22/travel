package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.Form;
import com.example.light_up_travel.model.ForumDto;

import java.util.List;

public interface ForumService {

    List<ForumDto> getAllNotDeleteForums();

    List<ForumDto> getAllDeletedForums();

    ForumDto getNotDeletedForumById(Long id);

    ForumDto insert(ForumDto forumDto);

    ForumDto updateNotDeletedForumById(Long id);

    void deleteNotDeletedForumById(Long id);

    Form isForumDeletedCheck(Long id);
}
