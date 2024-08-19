package com.kwon.springbootdeveloper.dto;

import com.kwon.springbootdeveloper.domain.Article;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {
    // 뷰에게 데이터를 전달하기 위한 객체

    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
