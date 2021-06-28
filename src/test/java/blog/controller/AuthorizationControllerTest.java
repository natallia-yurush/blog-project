package blog.controller;


import blog.config.JpaConfig;
import blog.config.TestConfig;
import by.nyurush.blog.config.SecurityConfig;
import by.nyurush.blog.config.WebConfig;
import by.nyurush.blog.dto.user.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorizationControllerTest extends BaseControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registrationSuccess() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("natallia.yurush@gmail.com");
        userDto.setPassword("admin");
        userDto.setFirstName("Natallia");
        userDto.setLastName("Yurush");

        mockMvc.perform(
                post("/auth/registration")
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("Natallia"));

    }

    @Test
    public void registrationFailed() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nika.yurush@gmail.com");
        userDto.setPassword("admin");
        userDto.setFirstName("Veronika");
        userDto.setLastName("Yurush");

        mockMvc.perform(
                post("/auth/registration")
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

    }

}
