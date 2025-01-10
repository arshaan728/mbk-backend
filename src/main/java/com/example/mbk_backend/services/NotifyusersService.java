package com.example.mbk_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface NotifyusersService {

  public void sendEmail();

  public void callEmail();
}
