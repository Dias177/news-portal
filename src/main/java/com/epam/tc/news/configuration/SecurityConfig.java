package com.epam.tc.news.configuration;

import com.epam.tc.news.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String USERS_ANT_PATTERNS = "/users/**";
    private static final String NEWS_ANT_PATTERNS = "/news/**";

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, USERS_ANT_PATTERNS).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, USERS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.PUT, USERS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, USERS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET, NEWS_ANT_PATTERNS).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, NEWS_ANT_PATTERNS).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(HttpMethod.PUT, NEWS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, NEWS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .and()
                .csrf().disable()
                .formLogin().disable().logout();
    }
}
