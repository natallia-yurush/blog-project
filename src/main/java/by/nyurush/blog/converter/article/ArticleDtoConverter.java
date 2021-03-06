package by.nyurush.blog.converter.article;

import by.nyurush.blog.dto.ArticleDto;
import by.nyurush.blog.entity.Article;
import by.nyurush.blog.entity.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleDtoConverter implements Converter<Article, ArticleDto> {

    @Override
    public ArticleDto convert(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setText(article.getText());
        articleDto.setStatus(article.getStatus());
        articleDto.setTagsId(article.getTags()
                .stream()
                .map(Tag::getId)
                .toList());

        return articleDto;
    }
}
