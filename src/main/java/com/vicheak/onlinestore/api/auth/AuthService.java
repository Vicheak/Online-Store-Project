package com.vicheak.onlinestore.api.auth;

import com.vicheak.onlinestore.api.auth.web.LoginDto;
import com.vicheak.onlinestore.api.auth.web.RegisterDto;
import com.vicheak.onlinestore.api.auth.web.VerifyDto;
import jakarta.mail.MessagingException;

public interface AuthService {

    void register(RegisterDto registerDto) throws MessagingException;

    void verify(VerifyDto verifyDto);

    void login(LoginDto loginDto);

}
