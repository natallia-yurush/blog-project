package by.nyurush.blog.service;

import by.nyurush.blog.entity.Article;
import by.nyurush.blog.entity.Status;
import by.nyurush.blog.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ArticleService {
    Optional<Article> findById(Long id);

    Article save(Article article, String email);

    void deleteById(Long id);

    List<Article> findAllByUser(String email);

    List<Article> findAllPublicArticle();

}
