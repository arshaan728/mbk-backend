package com.example.mbk_backend.services;

import com.example.mbk_backend.dto.FetchMonthlyincome;
import org.springframework.http.ResponseEntity;

public interface MonthlyService {

    public void addmonthlyservice();

    public void callmonthlyadd();

    ResponseEntity<?> fetchmonthlyincome(FetchMonthlyincome fetchMonthlyincome);
}
