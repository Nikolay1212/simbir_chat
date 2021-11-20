package com.simbir.chat.service;

import com.simbir.chat.dto.AuthDto;
import com.simbir.chat.dto.TokenDto;
import com.simbir.chat.exception.AccountNotFoundException;
import com.simbir.chat.exception.WrongPasswordException;

public interface SignInService {
    TokenDto signIn(AuthDto auth) throws AccountNotFoundException, WrongPasswordException;
}
