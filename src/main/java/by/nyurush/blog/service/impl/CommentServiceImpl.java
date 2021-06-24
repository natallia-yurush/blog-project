package by.nyurush.blog.service.impl;

import by.nyurush.blog.service.CommentService;
import by.nyurush.blog.entity.Article;
import by.nyurush.blog.entity.Comment;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.exception.CommentNotFoundException;
import by.nyurush.blog.exception.NoPermissionException;
import by.nyurush.blog.repository.CommentRepository;
import by.nyurush.blog.service.ArticleService;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public List<Comment> findAllByArticle(Long id) {
        return commentRepository.findAllByArticle(articleService.findById(id));
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
