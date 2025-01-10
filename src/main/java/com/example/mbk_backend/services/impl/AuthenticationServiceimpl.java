package com.example.mbk_backend.services.impl;

import com.example.mbk_backend.dto.JwtAuthenticationResponse;
import com.example.mbk_backend.dto.RefreshTokenRequest;
import com.example.mbk_backend.dto.SignUpRequest;

import com.example.mbk_backend.dto.SigninRequest;

import com.example.mbk_backend.entities.Role;
import com.example.mbk_backend.entities.User;
import com.example.mbk_backend.repository.UserRepository;
import com.example.mbk_backend.services.AuthenticationService;
import com.example.mbk_backend.services.JWTService;
import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceimpl implements AuthenticationService {



    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;



    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        String ut = signUpRequest.getRole();


        if (ut.equals("business")) {
            user.setRole(Role.BUSINESS);
        }
        else if (ut.equals("individual") ){

                user.setRole(Role.INDIVIDUAL);

        }


        user.setUsername(signUpRequest.getUsername());
        user.setPhonenumber(signUpRequest.getPhonenumber());



         return userRepository.save(user);

    }


    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail() , signinRequest.getPassword()));


           System.out.println(signinRequest.getEmail() + signinRequest.getPassword());
           var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("invalid email or password"));

           var jwt = jwtService.generateToken(user);

           var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

           JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

           jwtAuthenticationResponse.setToken(jwt);
           jwtAuthenticationResponse.setRefreshToken(refreshToken);
           jwtAuthenticationResponse.setType(String.valueOf(user.getRole()));

           return jwtAuthenticationResponse;
       }
       catch (Exception e ){
           System.out.println("error" + e.getMessage());
       }

      return null;


    }

    public  JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken() , user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            jwtAuthenticationResponse.setType(String.valueOf(user.getRole()));
            return jwtAuthenticationResponse;
        }
        return null;
    }






}
