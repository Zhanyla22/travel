package com.example.light_up_travel.controller;


import com.example.light_up_travel.dto.LifehackDTO;
import com.example.light_up_travel.services.FileUploadService;
import com.example.light_up_travel.services.LifehackService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<LifehackDTO> getAllActiveLifehack(@RequestParam Integer page, @RequestParam Integer size){
        return lifehackService.getAllActiveLifehack(page,size);
    }


    @Operation(summary =  "Получение всего удаленного lifehack(для корзинки) ")
    @GetMapping("/get-all-deleted-lifehack") //working good
    public List<LifehackDTO> getAllDeletedLifehack(@RequestParam Integer page, @RequestParam Integer size){
        return lifehackService.getAllDeletedLifehack(page,size);
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
    @PutMapping("/update-lifehack-without-video") // ?????((((((
    public ResponseEntity<Long> updateLifehackWithoutVideo(@RequestBody LifehackDTO lifehackDTO) throws Exception{
        return lifehackService.updateLifehackWithoutVideo(lifehackDTO);
    }

    @Operation(summary = "обновить lifehack по айдишке c фото 2 запрос")
    @PutMapping("/update-lifehack-video/{id}") // ?????((((((
    public ResponseEntity<String> updateLifehack(@PathVariable Long id,@RequestPart MultipartFile multipartFile) throws Exception{
        lifehackService.UpdateVideoForLifehack(id,multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "обновить lifehack")
    @PutMapping("/update-lifehack") // ?????((((((
    public ResponseEntity<Void> updateLifehack(@RequestPart LifehackDTO lifehackDTO,@RequestPart MultipartFile multipartFile) throws Exception{
        lifehackService.updateLifehack(lifehackDTO,multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "удалить lifehack по айдишке")
    @DeleteMapping("/delete-lifehack/{id}") //working good
    public ResponseEntity<Void> deleteLifehackById(@PathVariable Long id) throws Exception{
        lifehackService.deleteLifehackById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "удалить весь лайфхак(только фронтам)")
    @DeleteMapping("/hard-delete-all-lifehack")
    public void hardDeleteAllLifehack(){
        lifehackService.harDeleteAllLifehack();
    }


}
