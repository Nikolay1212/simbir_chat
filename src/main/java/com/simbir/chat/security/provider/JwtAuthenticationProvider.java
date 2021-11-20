package com.simbir.chat.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.simbir.chat.security.authentication.JwtAuthentication;
import com.simbir.chat.security.details.AccountUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("jwtAuthenticationProvider")
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwt = (JwtAuthentication) authentication;
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(authentication.getName());
        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Bad token");
        }
        authentication.setAuthenticated(true);
        AccountUserDetails accountUserDetails = new AccountUserDetails(
                decodedJWT.getClaim("role").asString(),
                decodedJWT.getClaim("state").asString());
        jwt.setAccountUserDetails(accountUserDetails);
        jwt.setAuthority(decodedJWT.getClaim("role").asString());
        return jwt;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthentication .class);
    }
}
