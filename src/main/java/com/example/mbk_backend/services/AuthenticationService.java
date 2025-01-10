package com.example.mbk_backend.services;

import com.example.mbk_backend.dto.JwtAuthenticationResponse;
import com.example.mbk_backend.dto.RefreshTokenRequest;
import com.example.mbk_backend.dto.SignUpRequest;
import com.example.mbk_backend.dto.SigninRequest;
import com.example.mbk_backend.entities.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
