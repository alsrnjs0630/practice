package com.kwon.springbootdeveloper.service;

import com.kwon.springbootdeveloper.domain.Article;
import com.kwon.springbootdeveloper.dto.AddArticleRequest;
import com.kwon.springbootdeveloper.dto.UpdateArticleRequest;
import com.kwon.springbootdeveloper.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity()); // JpaRepository에서 지원하는 저장 메서드. AddArticleRequest 클래스에 저장된 값들을 article DB에 저장
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
        // findAll() => JPA 지원 메서드. article 테이블에 저장되어 있는 모든 데이터를 조회
    }

    // 블로그 글 하나 조회
    public Article findById(long id) {
        return blogRepository.findById(id)  // JPA에서 제공하는 findById() 메서드를 사용해 ID를 받아 엔티티를 조회하고 없으면 예외 발생
                .orElseThrow(() -> new IllegalArgumentException("not ofund: " + id));
    }

    // 블로그 글 삭제
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    // 블로그 글 수정
    @Transactional // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
    public Article update(long id, UpdateArticleRequest request) {
        // findByID() 메서드로 엔티티 조회. 없으면 예외 발생
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        // 클라이언트에게 요청받은 정보를 update
        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
