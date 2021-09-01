package com.epam.tc.news.configuration;

import com.epam.tc.news.service.UserService;
import com.epam.tc.news.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String USERS_ANT_PATTERNS = "/users/**";
    private static final String NEWS_ANT_PATTERNS = "/news/**";
    private static final String AUTH_LOGIN = "/auth/login";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, USERS_ANT_PATTERNS).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, USERS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.PUT, USERS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, USERS_ANT_PATTERNS).hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET, NEWS_ANT_PATTERNS).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, NEWS_ANT_PATTERNS).hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(AUTH_LOGIN).permitAll()
                .and().apply(new JwtConfig(jwtTokenProvider));
    }
}
