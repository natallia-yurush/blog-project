package by.nyurush.blog.controller;

import by.nyurush.blog.dto.ArticleDto;
import by.nyurush.blog.dto.tag.TagCloudInterface;
import by.nyurush.blog.entity.Article;
import by.nyurush.blog.service.ArticleService;
import by.nyurush.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final ArticleService articleService;
    private final TagService tagService;
    private final ConversionService conversionService;

    @GetMapping("/tags")
    public List<ArticleDto> getArticlesByTags(@RequestParam List<Long> tagIds) {

        List<Article> articles = articleService.findAllByTags(tagIds);
        return articles.stream()
                .map(article -> conversionService.convert(article, ArticleDto.class))
                .toList();

    }

    @GetMapping("/tags-cloud")
    public List<TagCloudInterface> getTagCloud() {
        return tagService.findTagsCloud();
    }

}
