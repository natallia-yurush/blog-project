package by.nyurush.blog.config;

import by.nyurush.blog.security.jwt.JwtConfigurer;
import by.nyurush.blog.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //todo
    /*
    authorizeRequests() позволяет ограничить доступ на основе HttpServletRequest с использованием реализаций RequestMatcher .

    permitAll() это позволит получить публичный доступ, то есть любой желающий может получить доступ к конечной точке PUBLIC_URL без аутентификации.

    anyRequest().authenticated() ограничит доступ для любой другой конечной точки , отличной от PUBLIC_URL, и пользователь должен быть аутентифицирован.
     */


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
//                .antMatchers("/auth/registration/**").permitAll()
//                .antMatchers("/auth/confirm/**").permitAll()
//                .antMatchers(LOGIN_ENDPOINT).permitAll()
//                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // todo: to exclude some requests
        // web.ignoring().antMatchers(...);
        web.ignoring().antMatchers("/auth/login", "/auth/registration/**", "/auth/confirm/**", "/auth/forgot_password/**"
                , "/auth/reset/**", "/auth/check_code");

    }
}
