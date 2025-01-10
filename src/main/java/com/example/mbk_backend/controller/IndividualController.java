package com.example.mbk_backend.controller;

import com.example.mbk_backend.dto.AddTransaction;
import com.example.mbk_backend.dto.Addincome;
import com.example.mbk_backend.dto.FetchMonthlyincome;
import com.example.mbk_backend.dto.Fetchexpenses;
import com.example.mbk_backend.services.IncomeService;
import com.example.mbk_backend.services.Inventory;
import com.example.mbk_backend.services.MonthlyService;
import com.example.mbk_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/individual")
@RequiredArgsConstructor
public class IndividualController {

    private final Inventory inventoryservice;
    private final IncomeService incomeService;
    private final MonthlyService monthlyService;



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
    @PostMapping(value = "/changeincome")
    public ResponseEntity<?> chnageincome(@RequestBody Addincome addincome) {
        return ResponseEntity.ok(incomeService.changeincome(addincome));
    }
    @PostMapping(value = "/{email}/getincome")
    public ResponseEntity<?>  getincome(@PathVariable String email) {
        return ResponseEntity.ok(incomeService.fetchincome(email));
    }
    @PostMapping(value = "/fetchmonthlyincomeuser")
    public ResponseEntity<?> fetchmonthlyincomeuser(@RequestBody FetchMonthlyincome fetchMonthlyincome) {

        return ResponseEntity.ok(monthlyService.fetchmonthlyincome(fetchMonthlyincome));
    }

    @PostMapping(value ="/fetchexpensesbymonth")
    public ResponseEntity<?> fetchexpensesbymonth(@RequestBody Fetchexpenses fetchexpenses) {
        return ResponseEntity.ok(incomeService.fetchexpenses(fetchexpenses));
    }

    @PostMapping(value = "/fetchexpensesbycategory")
    public ResponseEntity<?> fetchexpensesbycategory(@RequestBody Fetchexpenses fetchexpenses) {
        return ResponseEntity.ok(incomeService.fetchexpensesbycategory(fetchexpenses));
    }

    @PostMapping(value = "/{email}/getallexpenses")
    public ResponseEntity<?> getallexpenses(@PathVariable String email) {
        return ResponseEntity.ok(incomeService.Allexpenses(email));
    }

    @PostMapping(value = "/{email}/trainingdata")
    public ResponseEntity<?> trainingdata(@PathVariable String email) {
        return ResponseEntity.ok(incomeService.trainingdata(email));
    }


}
