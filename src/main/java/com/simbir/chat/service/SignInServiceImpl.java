package com.simbir.chat.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.simbir.chat.dto.AuthDto;
import com.simbir.chat.dto.TokenDto;
import com.simbir.chat.exception.AccountNotFoundException;
import com.simbir.chat.exception.WrongPasswordException;
import com.simbir.chat.model.Account;
import com.simbir.chat.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private AccountRepo accountRepo;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDto signIn(AuthDto auth) throws AccountNotFoundException, WrongPasswordException {
        Optional<Account> accountOptional = accountRepo.findByEmail(auth.getEmail());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (passwordEncoder.matches(auth.getPassword(), account.getHashPassword())) {
                String token = JWT.create()
                        .withSubject(account.getId().toString())
                        .withClaim("email", account.getEmail())
                        .withClaim("role", account.getRole().toString())
                        .withClaim("state", account.getState().toString())
                        .sign(Algorithm.HMAC256(secretKey));
                return new TokenDto(token);
            } else {
                throw new WrongPasswordException("Wrong password");
            }
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }
}
