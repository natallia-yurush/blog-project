package by.nyurush.blog.converter.comment;

import by.nyurush.blog.dto.CommentDto;
import by.nyurush.blog.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setMessage(comment.getMessage());
        commentDto.setArticleId(comment.getArticle().getId());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setAuthorId(comment.getUser().getId());

        return commentDto;
    }
}
