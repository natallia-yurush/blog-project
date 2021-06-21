package by.nyurush.blog.repository;

import by.nyurush.blog.entity.Article;
import by.nyurush.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByUser(User user);
}
