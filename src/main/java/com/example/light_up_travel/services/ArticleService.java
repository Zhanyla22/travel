package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.Article;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.NotFoundResourceException;
import com.example.light_up_travel.mapper.ArticleMapper;
import com.example.light_up_travel.dto.ArticleDTO;
import com.example.light_up_travel.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final FileUploadService fileUploadService;

    

    public List<ArticleDTO> getAllActiveArticle(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleRepository.findByStatus(Status.ACTIVE, pageable);
        List<ArticleDTO> articleMapperList = new ArrayList<>();
        for(Article a : articles.getContent())
            articleMapperList.add(ArticleMapper.articleEntityToArticleDTO(a));
        return articleMapperList;
    }

    public List<ArticleDTO> getAllDeletedArticle(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> deletedArticles = articleRepository.findByStatus(Status.DELETED_BY_ADMIN, pageable);
        List<ArticleDTO> articleMapperList = new ArrayList<>();
        for(Article r: deletedArticles.getContent())
            articleMapperList.add(ArticleMapper.articleEntityToArticleDTO(r));
        return articleMapperList;
    }

    public ResponseEntity<ArticleDTO> getArticleById(Long id) throws Exception {
        try {
            Article article = articleRepository.findById(id).orElseThrow(
                    ()-> new Exception("Article with id = "+ id +" not found")
            );
            ArticleDTO articleDTO = ArticleMapper.articleEntityToArticleDTO(article);

            return new ResponseEntity<>(articleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Long> createNewArticleWithoutFile(ArticleDTO articleDTO) throws Exception {
        try {
            Article article = new Article();
            article.setTitle(articleDTO.getTitle());
            article.setDateCreated(LocalDate.now()); //check it
            article.setDescription(articleDTO.getDescription());
            article.setSubtitle(articleDTO.getSubtitle());
            article.setText(articleDTO.getText());
            article.setStatus(Status.ACTIVE);
            article = articleRepository.saveAndFlush(article);
            return new ResponseEntity<>(article.getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @SneakyThrows
    public String saveImageForArticle(Long articleId, MultipartFile multipartFile) throws IOException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Article was not found with id: " + articleId)
                );

        article.setFilePath(fileUploadService.saveFile(multipartFile));

        articleRepository.save(article);

        return "Saved image for article with id = "+articleId;
    }

    public ResponseEntity<Long> updateArticleWithoutFile(ArticleDTO articleDTO) throws Exception {
        try {
            Article article = articleRepository.findById(articleDTO.getId()).orElseThrow(
                    () -> new Exception("Article with  id = " + articleDTO.getId() + "not found")
            );
            article.setTitle(articleDTO.getTitle());
            article.setDateUpdated(LocalDateTime.now()); //check it
            article.setDescription(articleDTO.getDescription());
            article.setSubtitle(articleDTO.getSubtitle());
            article.setText(articleDTO.getText());
            articleRepository.save(article);
            return new ResponseEntity<Long>(article.getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @SneakyThrows
    public String updateImageForArticle(Long articleId, MultipartFile multipartFile) throws IOException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Article was not found with id: " + articleId)
                );

        article.setFilePath(fileUploadService.saveFile(multipartFile));

        articleRepository.save(article);

        return "updated image for article with id = "+articleId;
    }


    public void deleteArticleById(Long id) throws Exception {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new Exception("Article with  id = " + id + "not found")
        );
        article.setStatus(Status.DELETED_BY_ADMIN);
        article.setDateDeleted(LocalDateTime.now());
        articleRepository.save(article);
    }




    public ResponseEntity<String> createNewArticle(ArticleDTO articleDTO, MultipartFile file) throws Exception {
        try {
            Article article = new Article();
            article.setTitle(articleDTO.getTitle());
            article.setDateCreated(LocalDate.now()); //check it
            article.setDescription(articleDTO.getDescription());
            article.setSubtitle(articleDTO.getSubtitle());
            article.setText(articleDTO.getText());
            article.setStatus(Status.ACTIVE);
            article.setFilePath(fileUploadService.saveFile(file));
            articleRepository.save(article);
            return new ResponseEntity<String>("you created new article",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    public String harDeleteAllArticle() {
        articleRepository.deleteAll();
        return "All articles deleted";
    }





}
