package blog.controller;

import blog.config.EmbeddedRedisConfig;
import blog.config.JpaConfig;
import blog.config.TestConfig;
import by.nyurush.blog.config.SecurityConfig;
import by.nyurush.blog.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        WebConfig.class,
        JpaConfig.class,
        SecurityConfig.class,
        EmbeddedRedisConfig.class
})
@WebAppConfiguration
@Sql("classpath:data.sql")
public abstract class BaseControllerTest {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private Filter springSecurityFilterChain;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }
}
