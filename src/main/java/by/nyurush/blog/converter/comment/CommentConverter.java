package by.nyurush.blog.converter.comment;

import by.nyurush.blog.dto.CommentDto;
import by.nyurush.blog.entity.Comment;
import by.nyurush.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentConverter implements Converter<CommentDto, Comment> {
    private final ArticleRepository articleRepository;

    @Override
    public Comment convert(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setArticle(articleRepository.findById(commentDto.getArticleId()).orElseThrow());
        comment.setMessage(commentDto.getMessage());

        return comment;
    }
}
