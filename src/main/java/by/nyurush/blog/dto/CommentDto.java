package by.nyurush.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    private Long id;
    @NotNull
    private String message;
    @NotNull
    private Long articleId;

}
