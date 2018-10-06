package com.example.demo.security.config;

import com.example.demo.model.service.UserService;
import com.example.demo.security.AuthenticationFilter;
import com.example.demo.security.AuthenticationHandler;
import com.example.demo.security.FailureAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationHandler successHandler;

    @Autowired
    private FailureAuthenticationHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RequestMatcher requestMatcher = new RequestMatcher() {
            private Pattern allowedMethods =
                    Pattern.compile("^(POST)$");
            private Pattern allowedURL =
                    Pattern.compile(".*/login$");

            @Override
            public boolean matches(HttpServletRequest request) {
                return (allowedMethods.matcher(request.getMethod()).matches() && allowedURL.matcher(request.getRequestURL()).matches());
            }

        };

        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .addFilterBefore(new AuthenticationFilter(requestMatcher, userService, successHandler, failureHandler), UsernamePasswordAuthenticationFilter.class);
    }
}




