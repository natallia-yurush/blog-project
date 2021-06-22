package by.nyurush.blog.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetRequestDto {
    @NotNull
    private String code;
    @NotNull
    private String password;
}
