package by.nyurush.blog.service.impl;

import by.nyurush.blog.entity.Article;
import by.nyurush.blog.entity.Status;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.exception.NoPermissionException;
import by.nyurush.blog.exception.article.ArticleNotFoundException;
import by.nyurush.blog.repository.ArticleRepository;
import by.nyurush.blog.service.ArticleService;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    public Article save(Article article, String email) {
        User user = userService.findByEmail(email);
        article.setUser(user);
        if (article.getId() == null) {
            article.setCreatedAt(LocalDate.now());
        } else {
            article.setUpdatedAt(LocalDate.now());
        }

        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article, String email) {
        User user = userService.findByEmail(email);
        Article oldArticle = articleRepository.findById(article.getId())
                .orElseThrow(ArticleNotFoundException::new);
        if (!user.getId().equals(oldArticle.getUser().getId())) {
            log.warn("IN update (article) User {} has no permission", user.getEmail());
            throw new NoPermissionException();
        }
        article.setUpdatedAt(LocalDate.now());
        return articleRepository.save(article);
    }

    @Override
    public void deleteById(Long id, String email) {
        User user = userService.findByEmail(email);
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        if (!user.getId().equals(article.getUser().getId())) {
            log.warn("IN deleteById(article) User {} has no permission", user);
            throw new NoPermissionException();
        }

        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAllByUser(String email) {
        User user = userService.findByEmail(email);
        return articleRepository.findAllByUser(user);
    }

    @Override
    public Page<Article> findToFilter(String title, Long authorId, Pageable pageable) {
        User user = null;
        if (authorId != null) {
            user = userService.findById(authorId);
        }

        Article example = Article
                .builder()
                .title(title)
                .user(user)
                .build();

        return articleRepository.findAll(Example.of(example), pageable);
    }
}
