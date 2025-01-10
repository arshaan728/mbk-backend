package com.example.mbk_backend.controller;

import com.example.mbk_backend.dto.AddTransaction;
import com.example.mbk_backend.dto.Addincome;
import com.example.mbk_backend.services.IncomeService;
import com.example.mbk_backend.services.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {

    private final Inventory inventoryservice;
    private final IncomeService incomeService;


    @PostMapping(value = "/{email}/additems" , consumes = "application/json")
    public ResponseEntity<?> additems(@RequestBody AddTransaction addTransaction , @PathVariable String email) {
        return ResponseEntity.ok(inventoryservice.additems( addTransaction , email));
    }
    @PostMapping(value = "/{email}/fetchitemstobepaid")
    public ResponseEntity<?> fetchitemstobepaid(@PathVariable String email) {
        return ResponseEntity.ok(inventoryservice.fetchitems(email));
    }

    @PostMapping(value = "/{purchaseid}/renewitems")
    public ResponseEntity<?> renewitems(@PathVariable Integer purchaseid) {
        return ResponseEntity.ok(inventoryservice.renewitems(purchaseid));
    }
    @PostMapping(value = "/{purchaseid}/deleteitems")
    public ResponseEntity<?> deleteitems(@PathVariable Integer purchaseid){
        return ResponseEntity.ok(inventoryservice.deletepurchaseitem(purchaseid));
    }

    @PostMapping(value = "/addincome")
    public ResponseEntity<?> addincome(@RequestBody Addincome addincome) {
        return ResponseEntity.ok(inventoryservice.addincome(addincome));
    }



}
