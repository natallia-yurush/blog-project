package by.nyurush.blog.service;

import by.nyurush.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {
    Comment save(Comment comment, String email);

    Page<Comment> findAllByArticle(Long id, Pageable pageable);

    Comment findById(Long id);

    void deleteById(Long articleId, Long commentId, String email);

}
