package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.Forum;
import com.example.light_up_travel.enums.Stat;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.mapper.ForumMapper;
import com.example.light_up_travel.dto.ForumDTO;
import com.example.light_up_travel.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final UserService userService;
    private final MentorService mentorService;


    public List<ForumDTO> getAllNotDeletedForums() {
        Iterable<Forum> forums = forumRepository.findAllNotDeletedForums();
        List<ForumDTO> forumDto = new ArrayList<>();

        for (Forum forum : forums) {
            forumDto.add(ForumMapper.ForumToForumDTO(forum));
        }

        return forumDto;
    }


    public List<ForumDTO> getAllForums() {
        Iterable<Forum> forums = forumRepository.findAll();
        List<ForumDTO> forumDto = new ArrayList<>();

        for (Forum forum : forums) {
            forumDto.add(ForumMapper.ForumToForumDTO(forum));
        }

        return forumDto;
    }

    public List<ForumDTO> getAllPendingForums() {
        Iterable<Forum> forums = forumRepository.getAllForumPending();
        List<ForumDTO> forumDto = new ArrayList<>();

        for (Forum forum : forums) {
            forumDto.add(ForumMapper.ForumToForumDTO(forum));
        }

        return forumDto;
    }


    public List<ForumDTO> getAllDeletedForums() {
        Iterable<Forum> forums = forumRepository.findAllDeletedForums();
        List<ForumDTO> forumDto = new ArrayList<>();

        for (Forum forum : forums) {
            forumDto.add(ForumMapper.ForumToForumDTO(forum));
        }

        return forumDto;
    }


    public ForumDTO insert(String desc) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Forum forum = new Forum();
        forum.setDescription(desc);
        forum.setUser(userService.getUserByAuthentication());
        forum.setDateCreated(new Date());
        forum.setStatus(Stat.PENDING);
        return ForumMapper.ForumToForumDTO(forumRepository.save(forum));
    }


    public void deleteNotDeletedForumById(Long id) {
        Forum forum = isForumDeletedCheck(id);
        forum.setStatus(Stat.DISAPPROVED);
        forum.setDateDeleted(new Date());
        forumRepository.save(forum);
    }


    public Forum isForumDeletedCheck(Long id) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find forum with id: " + id));
        if (forum.getDateDeleted() != null) {
            throw new NotFoundException("Forum with id: " + id + " was deleted!");
        }
        return forum;
    }


    public void hardDeleteAllForums() {
        forumRepository.deleteAll();
    }


    public void hardDeleteById(Long id) {
        forumRepository.deleteById(id);
    }

    public void approveForum(Long formId) {
        Forum forum = isForumDeletedCheck(formId);
        mentorService.saveMentor(forum.getUser().getId(), userService.getUserByAuthentication().getId());
        forum.setStatus(Stat.APPROVED);
        forumRepository.save(forum);
    }


    public ForumDTO getNotDeletedForumById(Long id) {
        Forum forum = isForumDeletedCheck(id);
        return ForumMapper.ForumToForumDTO(forum);
    }
}
