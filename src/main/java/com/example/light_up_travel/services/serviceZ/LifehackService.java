package com.example.light_up_travel.services.serviceZ;


import com.example.light_up_travel.entity.Lifehack;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.NotFoundResourceException;
import com.example.light_up_travel.mapper.LifehackMapper;
import com.example.light_up_travel.model.LifehackDTO;
import com.example.light_up_travel.repository.LifehackRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LifehackService {


    @Autowired
    private LifehackMapper lifehackMapper;

    @Autowired
    private LifehackRepository lifehackRepository;

    @Autowired
    private FileUploadService fileUploadService;

    public LifehackService(LifehackRepository lifehackRepository){

        this.lifehackRepository = lifehackRepository;
    }




    public List<LifehackDTO> getAllActiveLifehack(){
        List<LifehackDTO> lifehackMapperList = new ArrayList<>();
        for(Lifehack a:lifehackRepository.getAllActiveLifehack()){
            lifehackMapperList.add(lifehackMapper.lifehackEntityToLifehackDTO(a));
        }
        return lifehackMapperList;
    }

    public List<LifehackDTO> getAllDeletedLifehack(){
        List<LifehackDTO> lifehackMapperList = new ArrayList<>();
        for(Lifehack r:lifehackRepository.getAllDeletedLifehack()){
            lifehackMapperList.add(lifehackMapper.lifehackEntityToLifehackDTO(r));
        }
        return lifehackMapperList;
    }

    public ResponseEntity<LifehackDTO> getLifehackById(Long id) throws Exception {
        try {
            Lifehack lifehack = lifehackRepository.findById(id).orElseThrow(
                    ()-> new Exception("Lifehack with id = "+ id +" not found")
            );
            LifehackDTO lifehackDTO = lifehackMapper.lifehackEntityToLifehackDTO(lifehack);

            return new ResponseEntity<LifehackDTO>(lifehackDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<LifehackDTO>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Long> createNewLifehackWithoutVideo(LifehackDTO lifehackDTO) throws Exception {
        try {
            Lifehack lifehack = new Lifehack();
            lifehack.setTitle(lifehackDTO.getTitle());
            lifehack.setDateCreated(LocalDateTime.now()); //check it
            lifehack.setDescription(lifehackDTO.getDescription());
            lifehack.setStatus(Status.ACTIVE);
            lifehack = lifehackRepository.saveAndFlush(lifehack);
            return new ResponseEntity<Long>(lifehack.getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @SneakyThrows
    public String saveVideoForLifehack(Long lifehackId, MultipartFile multipartFile) throws IOException {
        Lifehack lifehack = lifehackRepository.findById(lifehackId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Lifehack was not found with id: " + lifehackId)
                );

        lifehack.setFilePath(fileUploadService.saveVideo(multipartFile));

        lifehack = lifehackRepository.saveAndFlush(lifehack);

        return "Saved video for lifehack with id = "+lifehackId;
    }

    public ResponseEntity<Long> updateLifehackWithoutVideo(LifehackDTO lifehackDTO) throws Exception {
        try {
            Lifehack lifehack = lifehackRepository.findById(lifehackDTO.getId()).orElseThrow(
                    () -> new Exception("Lifehack with  id = " + lifehackDTO.getId() + "not found")
            );
            lifehack.setTitle(lifehackDTO.getTitle());
            lifehack.setDateUpdated(LocalDateTime.now()); //check it
            lifehack.setDescription(lifehackDTO.getDescription());
            lifehack = lifehackRepository.saveAndFlush(lifehack);
            return new ResponseEntity<Long>(lifehack.getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @SneakyThrows
    public String UpdateVideoForLifehack(Long lifehackId, MultipartFile multipartFile) throws IOException {
        Lifehack lifehack = lifehackRepository.findById(lifehackId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Lifehack was not found with id: " + lifehackId)
                );

        lifehack.setFilePath(fileUploadService.saveVideo(multipartFile));

        lifehack = lifehackRepository.saveAndFlush(lifehack);

        return "updated video for lifehack with id = " + lifehackId;
    }


    public void deleteLifehackById(Long id) throws Exception {
        Lifehack lifehack = lifehackRepository.findById(id).orElseThrow(
                () -> new Exception("Lifehack with  id = " + id + "not found")
        );
        lifehack.setStatus(Status.DELETED_BY_ADMIN);
        lifehack.setDateDeleted(LocalDateTime.now());
        lifehack = lifehackRepository.saveAndFlush(lifehack);
    }
}
