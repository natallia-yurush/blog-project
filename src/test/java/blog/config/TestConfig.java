package blog.config;

import by.nyurush.blog.security.jwt.JwtTokenFilter;
import by.nyurush.blog.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public GenericFilterBean jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenProvider());
    }

}
