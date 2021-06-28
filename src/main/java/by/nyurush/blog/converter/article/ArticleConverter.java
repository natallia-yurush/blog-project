package by.nyurush.blog.converter.article;

import by.nyurush.blog.dto.ArticleDto;
import by.nyurush.blog.entity.Article;
import by.nyurush.blog.exception.TagNotFoundException;
import by.nyurush.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleConverter implements Converter<ArticleDto, Article> {
    private final TagRepository tagRepository;

    @Override
    public Article convert(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setStatus(articleDto.getStatus());
        article.setTags(articleDto.getTagsId()
                .stream()
                .map(id -> tagRepository.findById(id).orElseThrow(TagNotFoundException::new))
                .toList());

        return article;
    }
}
