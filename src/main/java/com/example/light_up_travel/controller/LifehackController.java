package com.example.light_up_travel.controller;


import com.example.light_up_travel.model.ArticleDTO;
import com.example.light_up_travel.model.LifehackDTO;
import com.example.light_up_travel.services.serviceZ.ArticleService;
import com.example.light_up_travel.services.serviceZ.FileUploadService;
import com.example.light_up_travel.services.serviceZ.LifehackService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lifehack")
public class LifehackController {

    @Autowired
    LifehackService lifehackService;

    @Autowired
    FileUploadService fileUploadService;


    @Operation(summary =  "Получение всего активного lifehack ")
    @GetMapping("/get-all-active-lifehack") //working good
    public List<LifehackDTO> getAllActiveLifehack(){
        return lifehackService.getAllActiveLifehack();
    }


    @Operation(summary =  "Получение всего удаленного lifehack(для корзинки) ")
    @GetMapping("/get-all-deleted-lifehack") //working good
    public List<LifehackDTO> getAllDeletedLifehack(){
        return lifehackService.getAllDeletedLifehack();
    }


    @Operation(summary = "получение lifehack по айдишке")
    @GetMapping("/get-lifehack/{id}") //working good
    public ResponseEntity<LifehackDTO> getLifehackById(@PathVariable Long id) throws Exception{
        return lifehackService.getLifehackById(id);
    }

    @Operation(summary = "создать новый Lifehack без фото/ 1 запрос")
    @PostMapping("/create-new-lifehack-without-video") //working good
    public ResponseEntity<Long> createNewLifehack(@RequestBody LifehackDTO lifehackDTO) throws Exception{
        return lifehackService.createNewLifehackWithoutVideo(lifehackDTO);
    }

    @Operation(summary = "создать новый Lifehack фото/ 2й запрос")
    @PostMapping("/create-new-lifehack-video/{id}") //working good
    public ResponseEntity<String> createNewLifehackVideo(@PathVariable Long id,@RequestPart MultipartFile multipartFile) throws Exception{
        return ResponseEntity.ok(lifehackService.saveVideoForLifehack(id,multipartFile));
    }


    @Operation(summary = "обновить lifehack по айдишке без фото 1 запрос")
    @PutMapping("/update-lifehack") // ?????((((((
    public ResponseEntity<Long> updateLifehack(@RequestBody LifehackDTO lifehackDTO) throws Exception{
        return lifehackService.updateLifehackWithoutVideo(lifehackDTO);
    }

    @Operation(summary = "обновить lifehack по айдишке c фото 2 запрос")
    @PutMapping("/update-lifehack-video/{id}") // ?????((((((
    public ResponseEntity<String> updateLifehack(@PathVariable Long id,@RequestPart MultipartFile multipartFile) throws Exception{
        return ResponseEntity.ok(lifehackService.UpdateVideoForLifehack(id,multipartFile));
    }

    @Operation(summary = "удалить lifehack по айдишке")
    @DeleteMapping("/delete-lifehack/{id}") //working good
    public void deleteLifehackById(@PathVariable Long id) throws Exception{
        lifehackService.deleteLifehackById(id);
    }



}
