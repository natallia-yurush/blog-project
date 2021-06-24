package by.nyurush.blog.service;

import by.nyurush.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    Article findById(Long id);

    Article save(Article article, String email);

    Article update(Article article, String email);

    void deleteById(Long id, String email);

    List<Article> findAllByUser(String email);

    Page<Article> findToFilter(String title, Long authorId, Pageable pageable);

    List<Article> findAllByTags(List<Long> articleIds);


}
