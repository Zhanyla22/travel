package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.entity.Form;
import com.example.light_up_travel.model.ForumDto;
import com.example.light_up_travel.repository.ForumRepository;
import com.example.light_up_travel.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumRepository forumRepository;

    @Override
    public List<ForumDto> getAllNotDeleteForums() {
        return null;
    }

    @Override
    public List<ForumDto> getAllDeletedForums() {
        return null;
    }

    @Override
    public ForumDto getNotDeletedForumById(Long id) {
        return null;
    }

    @Override
    public ForumDto insert(ForumDto forumDto) {
        return null;
    }

    @Override
    public ForumDto updateNotDeletedForumById(Long id) {
        return null;
    }

    @Override
    public void deleteNotDeletedForumById(Long id) {

    }

    @Override
    public Form isForumDeletedCheck(Long id) {
        return null;
    }
}
