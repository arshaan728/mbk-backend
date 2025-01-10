package com.example.mbk_backend.services;

import com.example.mbk_backend.dto.AddTransaction;
import com.example.mbk_backend.dto.Addincome;
import org.springframework.http.ResponseEntity;

public interface Inventory {

    ResponseEntity<?> additems (AddTransaction addTransaction , String email);

    ResponseEntity<?> fetchitems(String email);

    ResponseEntity<?> renewitems(Integer purchaseid);

    ResponseEntity<?> deletepurchaseitem (Integer purchaseid);

    ResponseEntity<?> addincome(Addincome  addincome);


}
