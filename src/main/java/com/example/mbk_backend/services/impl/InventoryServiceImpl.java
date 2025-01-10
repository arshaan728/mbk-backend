package com.example.mbk_backend.services.impl;

import com.example.mbk_backend.dto.AddTransaction;
import com.example.mbk_backend.dto.Addincome;
import com.example.mbk_backend.entities.ExpenseType;
import com.example.mbk_backend.entities.Income;
import com.example.mbk_backend.entities.User;
import com.example.mbk_backend.entities.Userspending;
import com.example.mbk_backend.repository.IncomeRepository;
import com.example.mbk_backend.repository.UserRepository;
import com.example.mbk_backend.repository.UserspendingRespository;
import com.example.mbk_backend.services.Inventory;
import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements Inventory {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserspendingRespository userspendingRespository;
    @Autowired
    private IncomeRepository incomeRepository;



    public ResponseEntity <?> additems(AddTransaction addTransaction , String email) {
        System.out.println("email"+email);
        Userspending userspending = new Userspending();
        userspending.setAmount(addTransaction.getAmount());
        userspending.setItemname(addTransaction.getItemname());
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(addTransaction.getDateTime());
        userspending.setDateTime(zonedDateTime);
        if(addTransaction.getRepeats().equals("true")) {
            userspending.setRepeats(true);
            userspending.setPaidstatus(false);
        }
        else {
            userspending.setRepeats(false);
            userspending.setPaidstatus(true);
        }


        userspending.setExpenseType(ExpenseType.valueOf(addTransaction.getExpensetype()));
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not foud")));
        System.out.println(user);
        userspending.setUserid(user.get());

        Userspending savedspending = userspendingRespository.save(userspending);

        return ResponseEntity.ok(savedspending);

    }

    @Override
    public ResponseEntity<?> fetchitems(String email) {
        System.out.println("hi");
        try {

            Optional <User> userdeatils = Optional.of(userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("not found")));
            List<Userspending> items = userspendingRespository.getrepeatingitemsfromeamail(userdeatils.get());
            System.out.println(items);
            return ResponseEntity.ok(items);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }


        return null;
    }

    @Override
    public ResponseEntity<?> renewitems(Integer purchaseid) {
       try {
           Optional <Userspending> userspending = userspendingRespository.findById(purchaseid);
           ZonedDateTime originalDateTime = userspending.get().getDateTime();

           Userspending  userspending1 = new Userspending();
           userspending1.setAmount(userspending.get().getAmount());
           userspending1.setDateTime(originalDateTime.plusMonths(1));
           userspending1.setExpenseType(userspending.get().getExpenseType());
           userspending1.setItemname(userspending.get().getItemname());
           userspending1.setPaidstatus(false);
           userspending1.setRepeats(userspending.get().getRepeats());
           userspending1.setUserid(userspending.get().getUserid());
           System.out.println("in");
          userspendingRespository.save(userspending1);
            System.out.println(userspending.get());
//           userspendingRespository.deleteById(purchaseid);
           int updated =userspendingRespository.updatepaidstatus( userspending.get().getUserid() , purchaseid);

           return ResponseEntity.ok(userspending1.getPaidstatus());


       }
       catch(Exception e) {
           System.out.println(e.getMessage());
       }

       return null;
    }

    @Override
    public ResponseEntity<?> deletepurchaseitem(Integer purchaseid) {
        try {
            Optional<Userspending> userspending = userspendingRespository.findByPurchaseid(purchaseid);

            if(userspending.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error not found userspending" + purchaseid);
            }

            userspendingRespository.deleteByPurchaseid(userspending.get().getPurchaseid());

            return ResponseEntity.ok(purchaseid);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addincome(Addincome addincome) {
        try {
            Optional<User> user = userRepository.findByEmail(addincome.getEmail());
            if(user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user doesnt exist");
            }
            Optional<Income> income = incomeRepository.findByUserid(user.get().getUserid());
            System.out.println(income);
            if(!income.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("user already has a income amount");
            }


            Income income1 = new Income();
            income1.setUserid(user.get());
            income1.setIncome(addincome.getIncome());

            Income savedincome = incomeRepository.save(income1);

            return ResponseEntity.ok(savedincome);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
