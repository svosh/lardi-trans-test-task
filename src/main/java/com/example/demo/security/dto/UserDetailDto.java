package com.example.demo.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

public class UserDetailDto {

    private String password;

    private String username;

    private Set<String> authorities = new HashSet<>();

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    public UserDetailDto() {
    }

    public UserDetailDto(UserDetails userDetails) {
        this.password = userDetails.getPassword();
        this.username = userDetails.getUsername();
        for (GrantedAuthority ga : userDetails.getAuthorities()) {
            this.authorities.add(ga.getAuthority());
        }
        this.accountNonExpired = userDetails.isAccountNonExpired();
        this.accountNonLocked = userDetails.isAccountNonLocked();
        this.enabled = userDetails.isEnabled();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserDetailDto{"
                + "password='" + password + '\''
                + ", username='" + username + '\''
                + ", authorities=" + authorities
                + ", accountNonExpired=" + accountNonExpired
                + ", accountNonLocked=" + accountNonLocked
                + ", credentialsNonExpired=" + credentialsNonExpired
                + ", enabled=" + enabled
                + '}';
    }
}
