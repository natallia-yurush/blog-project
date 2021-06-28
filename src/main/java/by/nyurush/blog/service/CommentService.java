package by.nyurush.blog.service;

import by.nyurush.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Comment save(Comment comment, String email);

    List<Comment> findAllByArticle(Long id);

    Page<Comment> findAllByArticle(Long id, Pageable pageable);

    Comment findById(Long id);

    void deleteById(Long articleId, Long commentId, String email);

}
