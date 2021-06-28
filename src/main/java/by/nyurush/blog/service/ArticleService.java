package by.nyurush.blog.service;

import by.nyurush.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    Article findById(Long id);

    Article save(Article article, String email);

    Article update(Article article, String email);

    void deleteById(Long id, String email);

    List<Article> findAllByUser(String email);

    Page<Article> findToFilter(String title, Long authorId, Pageable pageable);

}
