package com.example.demo.security;

import com.example.demo.model.entity.User;
import com.example.demo.model.service.UserService;
import com.example.demo.security.dto.UserDetailDto;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;

    private AuthenticationHandler successHandler;

    private AuthenticationFailureHandler failureHandler;

    public AuthenticationFilter(RequestMatcher requestMatcher, UserService userService, AuthenticationHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(requestMatcher);
        this.userService = userService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<String> roleNames) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String str : roleNames) {
            authorities.add(new SimpleGrantedAuthority(str));
        }
        return authorities;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User loginUser = new User();
        loginUser.setLogin(request.getParameter("login"));
        loginUser.setPassword(request.getParameter("password"));
        UserDetailDto userDetailDto = userService.getUserDetailDto(loginUser).orElse(null);
        if (userDetailDto != null) {
            List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(userDetailDto.getAuthorities());
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(userDetailDto.getUsername())
                    .password(userDetailDto.getPassword())
                    .authorities(grantedAuthorities)
                    .accountExpired(!userDetailDto.isAccountNonExpired())
                    .accountLocked(!userDetailDto.isAccountNonLocked())
                    .credentialsExpired(userDetailDto.isCredentialsNonExpired())
                    .disabled(!userDetailDto.isEnabled())
                    .build();
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            return authentication;

        } else {
            throw new AuthenticationCredentialsNotFoundException("User not found");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }
        SecurityContextHolder.getContext().setAuthentication(authResult);
        this.getRememberMeServices().loginSuccess(request, response, authResult);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Authentication request failed: " + failed.toString(), failed);
            this.logger.debug("Updated SecurityContextHolder to contain null Authentication");
            this.logger.debug("Delegating to authentication failure handler " + this.failureHandler);
        }

        this.getRememberMeServices().loginFail(request, response);
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
