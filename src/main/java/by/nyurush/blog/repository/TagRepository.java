package by.nyurush.blog.repository;

import by.nyurush.blog.dto.tag.TagCloudInterface;
import by.nyurush.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    List<Tag> findAllByIdIn(List<Long> ids);

    @Query("select distinct t.name as tagName, count(a) as countOfArticle from Tag t " +
            "join t.articles AS a GROUP BY t.id")
    List<TagCloudInterface> findTagsCloud();

}
