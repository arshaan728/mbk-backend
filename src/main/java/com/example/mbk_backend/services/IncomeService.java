package com.example.mbk_backend.services;

import com.example.mbk_backend.dto.Addincome;
import com.example.mbk_backend.dto.Fetchexpenses;
import org.springframework.http.ResponseEntity;

public interface IncomeService {

    ResponseEntity<?> addincome(Addincome addincome);

    ResponseEntity<?> changeincome(Addincome addincome);

    ResponseEntity<?> fetchincome(String email);

    ResponseEntity<?> fetchexpenses(Fetchexpenses fetchexpenses);

    ResponseEntity<?> fetchexpensesbycategory(Fetchexpenses fetchexpenses);

    ResponseEntity<?> Allexpenses(String email);

    ResponseEntity<?> trainingdata(String email);

}
