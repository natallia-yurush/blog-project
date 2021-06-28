package by.nyurush.blog.config;

import by.nyurush.blog.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("by.nyurush.blog")
public class WebConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public ConversionService conversionService(final List<Converter> converterList) {
        GenericConversionService conversionService = new GenericConversionService();
        converterList.forEach(conversionService::addConverter);
        return conversionService;
    }

}
