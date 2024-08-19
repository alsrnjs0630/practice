package com.kwon.springbootdeveloper.controller;

import com.kwon.springbootdeveloper.domain.Article;
import com.kwon.springbootdeveloper.dto.AddArticleRequest;
import com.kwon.springbootdeveloper.dto.ArticleResponse;
import com.kwon.springbootdeveloper.dto.UpdateArticleRequest;
import com.kwon.springbootdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP 응답으로 객체 데이터를 JSON 형식으로 반환
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST 일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody로 요청 본문 값 매핑
    // RequestBody : 클라이언트에게 입력받은 데이터(JSON, xml 등)를 자바 객체로 변환
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    // 전체 글을 조회한 뒤 반환
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    // 블로그 글 조회 메서드
    @GetMapping("/api/articles/{id}")
    // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) { // PathVariable : URL에서 값을 가져오는 애너테이션. 즉 URL의 id값이 파라미터 값으로 들어옴
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // 블로그 글 삭제 메서드
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        // ResponseEntity는 HTTP 응답을 직접 구성할 수 있는 객체

        blogService.delete(id);

        return ResponseEntity.ok()  // .ok() 메서드는 HTTP 상태 코드 200 OK를 의미하는 응답 생성
                .build(); // 본문(body)이 없는 응답 반환 => 데이터 없이 성공 상태 코드만 클라이언트에게 전송
    }

    // 블로그 글 수정 메서드
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
