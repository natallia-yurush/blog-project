package by.nyurush.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TagDto {
    private Long id;
    @NotNull
    private String name;
}
