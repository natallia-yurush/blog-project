package by.nyurush.blog.dto;

import by.nyurush.blog.entity.Status;
import by.nyurush.blog.entity.Tag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ArticleDto {
    private Long id;
    @NotNull
    @Size(max = 50)
    private String title;
    @NotNull
    private String text;
    @NotNull
    private Status status;
    private List<Tag> tags;

}
