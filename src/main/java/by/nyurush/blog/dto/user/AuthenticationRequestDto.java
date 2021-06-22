package by.nyurush.blog.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
