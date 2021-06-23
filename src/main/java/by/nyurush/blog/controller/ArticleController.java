package by.nyurush.blog.controller;

import by.nyurush.blog.dto.ArticleDto;
import by.nyurush.blog.entity.Article;
import by.nyurush.blog.security.jwt.JwtTokenProvider;
import by.nyurush.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/articles")
@Slf4j
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ConversionService conversionService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articleService.findAllPublicArticle();
        List<ArticleDto> articleDtos = new ArrayList<>();
        articles.forEach(article ->
                articleDtos.add(conversionService.convert(article, ArticleDto.class))
        );
        return articleDtos;
    }

    @GetMapping("/my")
    public List<ArticleDto> getAllUserArticles(HttpServletRequest req) {
        List<Article> articles = articleService.findAllByUser(getEmail(req));
        List<ArticleDto> articleDtos = new ArrayList<>();
        articles.forEach(article ->
                articleDtos.add(conversionService.convert(article, ArticleDto.class))
        );
        return articleDtos;
    }

    @PostMapping
    public void addArticle(@RequestBody ArticleDto articleDto,
                           HttpServletRequest req) {
        Article article = conversionService.convert(articleDto, Article.class);
        articleService.save(article, getEmail(req));
    }

    private String getEmail(HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        return jwtTokenProvider.getEmail(token);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id,
                              HttpServletRequest req) {
        articleService.deleteById(id, getEmail(req));
    }

    @PatchMapping("/{id}")
    public void updateArticle(@PathVariable Long id,
                              @RequestBody ArticleDto articleDto,
                              HttpServletRequest req) {
        articleDto.setId(id);
        Article article = conversionService.convert(articleDto, Article.class);

        articleService.update(article, getEmail(req));
    }

}
