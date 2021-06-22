package by.nyurush.blog.converter.article;

import by.nyurush.blog.dto.ArticleDto;
import by.nyurush.blog.entity.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverter implements Converter<ArticleDto, Article> {
    @Override
    public Article convert(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setStatus(articleDto.getStatus());
        article.setTags(articleDto.getTags());

        return article;
    }
}
