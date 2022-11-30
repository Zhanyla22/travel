package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.Article;
import com.example.light_up_travel.model.ArticleDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public Article articleDtoToArticleEntity(ArticleDTO articleDTO){
        Article article = new Article();
        article.setFilePath(articleDTO.getFilePath());
        article.setTitle(articleDTO.getTitle());
        article.setDateCreated(articleDTO.getDateCreated());
        article.setDescription(articleDTO.getDescription());
        article.setSubtitle(articleDTO.getSubtitle());
        article.setText(articleDTO.getText());

        return article;
    }

    public ArticleDTO articleEntityToArticleDTO(Article article){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setFilePath(article.getFilePath());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setDateCreated(article.getDateCreated());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setSubtitle(article.getSubtitle());
        articleDTO.setText(article.getText());

        return articleDTO;
    }

}
