package com.example.light_up_travel.services.serviceZ;

import com.example.light_up_travel.entity.Article;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.NotFoundResourceException;
import com.example.light_up_travel.mapper.ArticleMapper;
import com.example.light_up_travel.model.ArticleDTO;
import com.example.light_up_travel.repository.ArticleRepository;
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
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ImageService imageService;

    public ArticleService(ArticleRepository articleRepository){

        this.articleRepository = articleRepository;
    }

    public List<ArticleDTO> getAllActiveArticle(){
        List<ArticleDTO> articleMapperList = new ArrayList<>();
        for(Article a:articleRepository.getAllActiveArticles()){
            articleMapperList.add(articleMapper.articleEntityToArticleDTO(a));
        }
        return articleMapperList;
    }

    public List<ArticleDTO> getAllDeletedArticle(){
        List<ArticleDTO> articleMapperList = new ArrayList<>();
        for(Article r:articleRepository.getAllDeletedArticles()){
            articleMapperList.add(articleMapper.articleEntityToArticleDTO(r));
        }
        return articleMapperList;
    }

    public ResponseEntity<ArticleDTO> getArticleById(Long id) throws Exception {
        try {
            Article article = articleRepository.findById(id).orElseThrow(
                    ()-> new Exception("Article with id = "+ id +" not found")
            );
            ArticleDTO articleDTO = articleMapper.articleEntityToArticleDTO(article);

            return new ResponseEntity<ArticleDTO>(articleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ArticleDTO>(HttpStatus.NOT_FOUND);
        }
    }

//    public ResponseEntity<String> createNewArticle(ArticleDTO articleDTO, MultipartFile file) throws Exception {
//        try {
//            Article article = new Article();
//            article.setTitle(articleDTO.getTitle());
//            article.setDateCreated(LocalDateTime.now()); //check it
//            article.setDescription(articleDTO.getDescription());
//            article.setSubtitle(articleDTO.getSubtitle());
//            article.setText(articleDTO.getText());
//            article.setStatus(Status.ACTIVE);
//            article.setFilePath(imageService.saveFile(file));
//            articleRepository.save(article);
//            return new ResponseEntity<String>("you created new article",HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
//        }
//    }


    public ResponseEntity<Long> createNewArticleWithoutFile(ArticleDTO articleDTO) throws Exception {
        try {
            Article article = new Article();
            article.setTitle(articleDTO.getTitle());
            article.setDateCreated(LocalDateTime.now()); //check it
            article.setDescription(articleDTO.getDescription());
            article.setSubtitle(articleDTO.getSubtitle());
            article.setText(articleDTO.getText());
            article.setStatus(Status.ACTIVE);
            articleRepository.save(article);
            return new ResponseEntity<Long>(article.getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @SneakyThrows
    public String saveImageForArticle(Long articleId, MultipartFile multipartFile) throws IOException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Article was not found with id: " + articleId)
                );

        article.setFilePath(imageService.saveFile(multipartFile));

        articleRepository.save(article);

        return "Saved image for article with id = "+articleId;
    }

    public ResponseEntity<Long> updateArticleWithoutFile(ArticleDTO articleDTO) throws Exception {
        try {
            Article article = articleRepository.findById(articleDTO.getId()).orElseThrow(
                    () -> new Exception("Content with  id = " + articleDTO.getId() + "not found")
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
    public String UpdateImageForArticle(Long articleId, MultipartFile multipartFile) throws IOException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Article was not found with id: " + articleId)
                );

        article.setFilePath(imageService.saveFile(multipartFile));

        articleRepository.save(article);

        return "updated image for article with id = "+articleId;
    }



//    public ResponseEntity<String> updateArticle(ArticleDTO articleDTO,MultipartFile file) throws Exception {
//        try {
//            Article article = new Article();
//            article.setTitle(articleDTO.getTitle());
//            article.setDateUpdated(LocalDateTime.now()); //check it
//            article.setDescription(articleDTO.getDescription());
//            article.setSubtitle(articleDTO.getSubtitle());
//            article.setText(articleDTO.getText());
//            article.setFilePath(imageService.saveFile(file));
//            articleRepository.save(article);
//            return new ResponseEntity<String>("you updated article",HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
//        }
//    }

    public void deleteArticleById(Long id) throws Exception {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new Exception("Article with  id = " + id + "not found")
        );
        article.setStatus(Status.DELETED_BY_ADMIN);
        article.setDateDeleted(LocalDateTime.now());
        articleRepository.save(article);
    }





}
