package com.example.light_up_travel.services;


import com.example.light_up_travel.entity.Lifehack;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.NotFoundResourceException;
import com.example.light_up_travel.mapper.LifehackMapper;
import com.example.light_up_travel.dto.LifehackDTO;
import com.example.light_up_travel.repository.LifehackRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LifehackService {



    private LifehackRepository lifehackRepository;

    private FileUploadService fileUploadService;

    public LifehackService(LifehackRepository lifehackRepository, FileUploadService fileUploadService){

        this.lifehackRepository = lifehackRepository;
        this.fileUploadService = fileUploadService;
    }




    public List<LifehackDTO> getAllActiveLifehack(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Lifehack> lifehacks = lifehackRepository.findByStatus(Status.ACTIVE, pageable);
        List<LifehackDTO> lifehackMapperList = new ArrayList<>();
        for(Lifehack a:lifehacks.getContent())
            lifehackMapperList.add(LifehackMapper.lifehackEntityToLifehackDTO(a));
        return lifehackMapperList;
    }

    public List<LifehackDTO> getAllDeletedLifehack(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Lifehack> deletedLifehacks = lifehackRepository.findByStatus(Status.DELETED_BY_ADMIN, pageable);
        List<LifehackDTO> lifehackMapperList = new ArrayList<>();
        for(Lifehack r:deletedLifehacks.getContent())
            lifehackMapperList.add(LifehackMapper.lifehackEntityToLifehackDTO(r));
        return lifehackMapperList;
    }

    public ResponseEntity<LifehackDTO> getLifehackById(Long id) throws Exception {
        try {
            Lifehack lifehack = lifehackRepository.findById(id).orElseThrow(
                    ()-> new Exception("Lifehack with id = "+ id +" not found")
            );
            LifehackDTO lifehackDTO = LifehackMapper.lifehackEntityToLifehackDTO(lifehack);

            return new ResponseEntity<>(lifehackDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(lifehack.getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @SneakyThrows
    public String saveVideoForLifehack(Long lifehackId, MultipartFile multipartFile) throws IOException {
        Lifehack lifehack = lifehackRepository.findById(lifehackId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Lifehack was not found with id: " + lifehackId)
                );

        lifehack.setFilePath(fileUploadService.saveVideo(multipartFile));

        lifehackRepository.save(lifehack);

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
            return new ResponseEntity<>(lifehack.getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @SneakyThrows
    public String UpdateVideoForLifehack(Long lifehackId, MultipartFile multipartFile) throws IOException {
        Lifehack lifehack = lifehackRepository.findById(lifehackId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Lifehack was not found with id: " + lifehackId)
                );

        lifehack.setFilePath(fileUploadService.saveVideo(multipartFile));

        lifehackRepository.save(lifehack);

        return "updated video for lifehack with id = " + lifehackId;
    }


    public ResponseEntity<Void> updateLifehack(LifehackDTO lifehackDTO, MultipartFile multipartFile) throws Exception {
        try {
            Lifehack lifehack = lifehackRepository.findById(lifehackDTO.getId()).orElseThrow(
                    () -> new Exception("Lifehack with  id = " + lifehackDTO.getId() + "not found")
            );
            lifehack.setTitle(lifehackDTO.getTitle());
            lifehack.setDateUpdated(LocalDateTime.now()); //check it
            lifehack.setDescription(lifehackDTO.getDescription());
            if (!Objects.isNull(multipartFile))
            lifehack.setFilePath(fileUploadService.saveVideo(multipartFile));
            lifehackRepository.save(lifehack);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<Void> deleteLifehackById(Long id) throws Exception {
        Lifehack lifehack = lifehackRepository.findById(id).orElseThrow(
                () -> new Exception("Lifehack with  id = " + id + "not found")
        );
        lifehack.setStatus(Status.DELETED_BY_ADMIN);
        lifehack.setDateDeleted(LocalDateTime.now());
        lifehackRepository.save(lifehack);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String harDeleteAllLifehack() {
        lifehackRepository.deleteAll();
        return "All lifehacks deleted";
    }
}
