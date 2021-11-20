package com.simbir.chat.security.authentication;

import com.simbir.chat.security.details.AccountUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthentication implements Authentication {

    private boolean isAuthenticated;
    private final String token;
    private String authority;
    private AccountUserDetails accountUserDetails;

    public void setAccountUserDetails(AccountUserDetails accountUserDetails) {
        this.accountUserDetails = accountUserDetails;
    }

    public JwtAuthentication(String token) {
        this.token = token;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return accountUserDetails;
    }

    @Override
    public Object getPrincipal() {
        return accountUserDetails ;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return token;
    }
}
