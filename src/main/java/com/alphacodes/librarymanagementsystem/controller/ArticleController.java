package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.DTO.ArticleViewDto;
import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleService;
import com.alphacodes.librarymanagementsystem.util.ImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final UserRepository userRepository;

    public ArticleController(ArticleService articleService, UserRepository userRepository) {
        this.articleService = articleService;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<ArticleDto> addArticle(@RequestBody ArticleDto articleDto) {
        try {
            ArticleDto addedArticle = articleService.addArticle(articleDto);
            return ResponseEntity.ok(addedArticle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        try {
            List<ArticleDto> articles = articleService.getAllArticles();
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{articleID}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable int articleID) {
        try {
            ArticleDto article = articleService.getArticleById(articleID);
            return ResponseEntity.ok(article);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{articleID}")
    public ResponseEntity<String> deleteArticle(@PathVariable int articleID) {
        try {
            String result = articleService.deleteArticle(articleID);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // for article view dto
    @GetMapping("/allArticles")
    public List<ArticleViewDto> getAllArticleView() {
        return articleService.getAllArticleView();
    }

    @GetMapping("view/{articleId}")
    public ArticleViewDto getArticleViewById(@PathVariable int articleId) {
        return articleService.getArticleViewById(articleId);
    }

    @GetMapping("viewFull/{articleId}")
    public Article getFullArticleById(@PathVariable int articleId) {
        return articleService.getArticleFullById(articleId);
    }

    @GetMapping("getByUserID/{userId}")
    public List<ArticleViewDto> getArticleByUserId(@PathVariable String userId) {
        return articleService.getArticleByUserId(userId);
    }

    @PostMapping("/addArticle")
    public ResponseEntity<ArticleDto> addArticle(
            @RequestParam  String title,
            @RequestParam  String body,
            @RequestParam  String authorId,
            @RequestParam MultipartFile articleImg
    ) {
        try {
            ArticleDto article = new ArticleDto();

            article.setTitle(title);
            article.setBody(body);
            article.setUserID(authorId);

            if (articleImg != null) {

                //article.setArticleImg(articleImg.getBytes());
                // use image utils
                article.setArticleImg(ImageUtils.compressBytes(articleImg.getBytes()));
            } else {
                article.setArticleImg(null);
            }


            ArticleDto addedArticle = articleService.addArticle(article);
            return ResponseEntity.ok(addedArticle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // TODO: Edit article endpoint
    @PutMapping("/editArticle/{articleId}")
    public ResponseEntity<ArticleDto> editArticle(
            // Path variable from URL
            @PathVariable int articleId,

            // Parameters from form data
            @RequestParam String title,
            @RequestParam String body,
            @RequestParam String authorId,
            @RequestParam(required = false) MultipartFile articleImg
    ) {
        try {
            ArticleDto article = new ArticleDto();
            article.setTitle(title);
            article.setBody(body);
            article.setUserID(authorId);

            if (articleImg != null) {
                article.setArticleImg(articleImg.getBytes()); // Ensure correct handling of MultipartFile
            } else {
                System.out.println("Image null in edit article");
                article.setArticleImg(null);
            }

            ArticleDto editedArticle = articleService.editArticle(article, articleId);
            return ResponseEntity.ok(editedArticle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{userId}/delete/{articleId}")
    public ResponseEntity<String> deleteArticle(
            @PathVariable int articleId,
            @PathVariable String userId
    ) {
        try {
            String result = articleService.DeleteArticle(articleId, userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //TODO: Search Articles By heading
    //TODO: Search Articles By author
    //TODO: Search Articles By date
    //TODO: Search Articles by content
}