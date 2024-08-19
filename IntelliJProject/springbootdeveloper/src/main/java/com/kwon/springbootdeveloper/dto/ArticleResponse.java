package com.kwon.springbootdeveloper.dto;

import com.kwon.springbootdeveloper.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    // 응답을 위한 DTO

    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
