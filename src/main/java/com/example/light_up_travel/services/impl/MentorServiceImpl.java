package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.entity.Mentor;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.repository.MentorRepository;
import org.springframework.stereotype.Service;

@Service

public class MentorServiceImpl {

    private final MentorRepository mentorRepository;

    public MentorServiceImpl(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }


    public Mentor saveMentor(Long menteeId, Long mentorId){
       return mentorRepository.save(new Mentor(menteeId,mentorId));
    }

    public Mentor getMentorById(Long userId){
        return mentorRepository.findByMentorId(userId).orElseThrow(() -> new NotFoundException("User not Found") );
    }
}
