package com.example.mbk_backend.services.impl;

import com.example.mbk_backend.config.RestTemplateConfig;
import com.example.mbk_backend.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class ApiServiceimpl  implements ApiService {

    @Autowired
    RestTemplateConfig restTemplateConfig;

    @Value("${python.url}")
    private String apiurl;


    @Override
    public ResponseEntity<?> callexternalapi() {

        String url = apiurl +  "/<refreshtoken>/getpredicteddata";

//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arra);

        return null;
    }
}
