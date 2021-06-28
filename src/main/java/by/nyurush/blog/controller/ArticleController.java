package by.nyurush.blog.controller;

import by.nyurush.blog.controller.util.SortOrderUtil;
import by.nyurush.blog.dto.ArticleDto;
import by.nyurush.blog.entity.Article;
import by.nyurush.blog.security.jwt.JwtTokenProvider;
import by.nyurush.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/my")
    public List<ArticleDto> getAllUserArticles(HttpServletRequest req) {
        List<Article> articles = articleService.findAllByUser(jwtTokenProvider.getEmail(req));
        List<ArticleDto> articleDtos = new ArrayList<>();
        articles.forEach(article ->
                articleDtos.add(conversionService.convert(article, ArticleDto.class))
        );
        return articleDtos;
    }

    @PostMapping
    public ArticleDto addArticle(@RequestBody ArticleDto articleDto,
                           HttpServletRequest req) {
        Article article = conversionService.convert(articleDto, Article.class);
        Article savedArticle = articleService.save(article, jwtTokenProvider.getEmail(req));
        return conversionService.convert(savedArticle, ArticleDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id,
                              HttpServletRequest req) {
        articleService.deleteById(id, jwtTokenProvider.getEmail(req));
    }

    @PatchMapping("/{id}")
    public ArticleDto updateArticle(@PathVariable Long id,
                              @RequestBody ArticleDto articleDto,
                              HttpServletRequest req) {
        articleDto.setId(id);
        Article article = conversionService.convert(articleDto, Article.class);
        Article updatedArticle = articleService.update(article, jwtTokenProvider.getEmail(req));

        return conversionService.convert(updatedArticle, ArticleDto.class);
    }

    //  articles?skip=0&limit=10&q=post_title&author=id&sort=field_name&order=asc|desc
    @GetMapping
    public List<ArticleDto> getAllArticles(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "100") Integer size,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) Long id,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String fieldToSort,
            @RequestParam(name = "order", required = false, defaultValue = "asc") String order

    ) {

        Sort.Order sortOrder = new Sort.Order(SortOrderUtil.getSortOrder(order), fieldToSort);

        List<Article> articles;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        Page<Article> articlePage = articleService.findToFilter(title, id, pageable);
        articles = articlePage.getContent();

        return articles.stream()
                .map(article -> conversionService.convert(article, ArticleDto.class))
                .toList();

    }


}
