package com.simbir.chat.controller;

import com.simbir.chat.dto.AuthDto;
import com.simbir.chat.dto.TokenDto;
import com.simbir.chat.exception.AccountNotFoundException;
import com.simbir.chat.exception.WrongPasswordException;
import com.simbir.chat.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class SignInController {

    @Autowired
    private SignInService signInService;

    @PermitAll
    @PostMapping("/signIn")
    public TokenDto signIn(@RequestBody AuthDto auth) throws AccountNotFoundException, WrongPasswordException {
        return signInService.signIn(auth);
    }
}
