package com.example.mbk_backend.dto;


import lombok.Data;

@Data
public class SignUpRequest {

    private String email;
    private String phonenumber;
    private String username;
    private String password ;
    private String role;





}
