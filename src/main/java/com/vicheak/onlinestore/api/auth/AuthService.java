package com.vicheak.onlinestore.api.auth;

import com.vicheak.onlinestore.api.auth.web.*;
import jakarta.mail.MessagingException;

public interface AuthService {

    void register(RegisterDto registerDto) throws MessagingException;

    void verify(VerifyDto verifyDto);

    AuthDto login(LoginDto loginDto);

    AuthDto refreshToken(RefreshTokenDto refreshTokenDto);

}
