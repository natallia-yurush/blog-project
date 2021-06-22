package by.nyurush.blog.service.impl;

import by.nyurush.blog.entity.Article;
import by.nyurush.blog.entity.Status;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.exception.user.UserNotFoundException;
import by.nyurush.blog.repository.ArticleRepository;
import by.nyurush.blog.service.ArticleService;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article save(Article article, String email) {
        User user = userService.findByEmail(email);
        article.setUser(user);
        if(article.getId() == null) {
            article.setCreatedAt(LocalDate.now());
        } else {
            article.setUpdatedAt(LocalDate.now());
        }

        return articleRepository.save(article);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAllByUser(String email) {
        User user = userService.findByEmail(email);
        return articleRepository.findAllByUser(user);
    }

    @Override
    public List<Article> findAllPublicArticle() {
        return articleRepository.findAllByStatus(Status.PUBLIC);
    }
}
