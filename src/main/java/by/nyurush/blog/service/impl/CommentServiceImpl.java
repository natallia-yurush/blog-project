package by.nyurush.blog.service.impl;

import by.nyurush.blog.entity.Article;
import by.nyurush.blog.entity.Comment;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.exception.CommentNotFoundException;
import by.nyurush.blog.exception.NoPermissionException;
import by.nyurush.blog.repository.CommentRepository;
import by.nyurush.blog.service.ArticleService;
import by.nyurush.blog.service.CommentService;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;

    @Override
    public Comment save(Comment comment, String email) {
        User user = userService.findByEmail(email);
        comment.setUser(user);
        comment.setCreatedAt(LocalDate.now());

        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> findAllByArticle(Long id, Pageable pageable) {
        Article article = articleService.findById(id);

        return commentRepository.findAllByArticle(article, pageable);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public void deleteById(Long articleId, Long commentId, String email) {
        User user = userService.findByEmail(email);
        Article article = articleService.findById(articleId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (user.getId().equals(article.getUser().getId())
                || user.getId().equals(comment.getUser().getId())) {
            commentRepository.deleteById(commentId);
        } else {
            log.warn("IN - deleteById (comment) : User {} has no permission to delete comment with id {}",
                    email, commentId);
            throw new NoPermissionException();
        }
    }

}
