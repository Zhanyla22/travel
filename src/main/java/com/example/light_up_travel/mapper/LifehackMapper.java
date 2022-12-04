package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.Lifehack;
import com.example.light_up_travel.model.LifehackDTO;
import org.springframework.stereotype.Component;

@Component
public class LifehackMapper {
    public Lifehack lifehackDTOtoLifehackEntity(LifehackDTO lifehackDTO){
        Lifehack lifehack = new Lifehack();
        lifehack.setId(lifehackDTO.getId());
        lifehackDTO.setDescription(lifehack.getDescription());
        lifehack.setFilePath(lifehackDTO.getFilePath());

        return lifehack;
    }

    public LifehackDTO lifehackEntityToLifehackDTO(Lifehack lifehack){
        LifehackDTO lifehackDTO = new LifehackDTO();
        lifehackDTO.setId(lifehack.getId());
        lifehackDTO.setTitle(lifehack.getTitle());
        lifehackDTO.setDescription(lifehack.getDescription());
        lifehackDTO.setFilePath(lifehack.getFilePath());

        return lifehackDTO;

    }
}
