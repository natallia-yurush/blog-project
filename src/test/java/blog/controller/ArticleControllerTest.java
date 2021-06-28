package blog.controller;

import by.nyurush.blog.entity.UserRole;
import by.nyurush.blog.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArticleControllerTest extends BaseControllerTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/articles/my"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteArticleWithNotExistingId() throws Exception {
        UserRole role = new UserRole();
        role.setName("ROLE_USER");
        String token = jwtTokenProvider.createToken("nika.yurush@gmail.com", List.of(role));

        assertNotNull(token);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/articles/25")
                .header("Authorization", "Bearer_" + token))
                .andExpect(status().isNotFound());
    }
}
