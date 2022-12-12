package com.example.light_up_travel.controller;


import com.example.light_up_travel.dto.ArticleDTO;
import com.example.light_up_travel.services.impl.ArticleService;
import com.example.light_up_travel.services.impl.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    FileUploadService fileUploadService;

    @Operation(summary =  "Получение всего активного артикла ")
    @GetMapping("/get-all-active-article") //working good
    public List<ArticleDTO> getAllActiveArticle(@RequestParam Integer page, @RequestParam Integer size){
        return articleService.getAllActiveArticle(page,size);
    }

    @Operation(summary =  "Получение всего удаленного артикла(для корзинки) ")
    @GetMapping("/get-all-deleted-article") //working good
    public List<ArticleDTO> getAllDeletedArticle(@RequestParam Integer page, @RequestParam Integer size){
        return articleService.getAllDeletedArticle(page,size);
    }

    @Operation(summary = "получение артикла по айдишке")
    @GetMapping("/get-article/{id}") //working good
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) throws Exception{
        return articleService.getArticleById(id);
    }
    @Operation(summary = "создать новый артикл без фото/ 1 запрос")
    @PostMapping("/create-new-article-without-photo") //working good
    public ResponseEntity<Long> createNewArticle(@RequestBody ArticleDTO articleDTO) throws Exception{
        return articleService.createNewArticleWithoutFile(articleDTO);
    }

    @Operation(summary = "создать новый артикл фото/ 2й запрос")
    @PostMapping("/create-new-article-photo/{id}")
    //consumes = "multipart/form-data")
    public ResponseEntity<String> createNewArticlePhoto(@PathVariable Long id,
                                                        @RequestPart MultipartFile multipartFile) throws Exception{
        return ResponseEntity.ok(articleService.saveImageForArticle(id,multipartFile));
    }

    @Operation(summary = "обновить артикл по айдишке без фото 1 запрос")
    @PutMapping("/update-article") // ?????((((((
    public ResponseEntity<Long> updateArticle(@RequestBody ArticleDTO articleDTO) throws Exception{
        return articleService.updateArticleWithoutFile(articleDTO);
    }

    @Operation(summary = "обновить артикл по айдишке c фото 2 запрос")
    @PutMapping("/update-article-photo/{id}") // ?????((((((
    public ResponseEntity<String> updateArticle(@PathVariable Long id,
                                                @RequestPart MultipartFile multipartFile) throws Exception{
        return ResponseEntity.ok(articleService.UpdateImageForArticle(id,multipartFile));
    }


    @Operation(summary = "создать новый артикл тестовый")
    @PostMapping(value = "/create-new-article")
    public ResponseEntity<String> createNewArticle(@RequestPart ArticleDTO articleDTO, @RequestPart MultipartFile file) throws Exception{
        return articleService.createNewArticle(articleDTO, file);
    }
//
//    @Operation(summary = "обновить артикл")
//    @PutMapping("/update-article") // ?????((((((
//    public ResponseEntity<String> updateArticle(@RequestBody ArticleDTO articleDTO,@RequestPart MultipartFile multipartFile) throws Exception{
//        return articleService.updateArticle(articleDTO, multipartFile);
//    }


    @Operation(summary = "удалить артикл по айдишке")
    @DeleteMapping("/delete-article/{id}") //working good
    public void deleteArticleById(@PathVariable Long id) throws Exception{
        articleService.deleteArticleById(id);
    }

    @Operation(summary = "удалить весь артикл(только фронтам)")
    @DeleteMapping("/hard-delete-all-article")
    public void hardDeleteAllArticle(){
        articleService.harDeleteAllArticle();
    }






}
