package by.nyurush.blog.service;

import by.nyurush.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment save(Comment comment, String email);

    List<Comment> findAllByArticle(Long id);

    Comment findById(Long id);

    void deleteById(Long articleId, Long commentId, String email);

}
