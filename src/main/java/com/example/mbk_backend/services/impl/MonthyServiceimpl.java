package com.example.mbk_backend.services.impl;

import com.example.mbk_backend.dto.FetchMonthlyincome;
import com.example.mbk_backend.entities.Income;
import com.example.mbk_backend.entities.Monthyincome;
import com.example.mbk_backend.entities.User;
import com.example.mbk_backend.repository.IncomeRepository;
import com.example.mbk_backend.repository.MonthlyservicesRepository;
import com.example.mbk_backend.repository.UserRepository;
import com.example.mbk_backend.services.MonthlyService;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MonthyServiceimpl implements MonthlyService {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private MonthlyservicesRepository monthlyservicesRepository;
    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 06 17 17 * ? ")
    public void  callmonthlyadd() {
        addmonthlyservice();
    }

    @Override
    public ResponseEntity<?> fetchmonthlyincome(FetchMonthlyincome fetchMonthlyincome) {
        try {
            AtomicReference<Optional<?>> mi= new AtomicReference<>();
//            AtomicReference<HttpStatus> error = new AtomicReference<>();

            Optional<User> user = userRepository.findByEmail(fetchMonthlyincome.getEmail());

            if(user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found" + fetchMonthlyincome.getEmail());
            }

            Integer userid = user.get().getUserid();

            List<Monthyincome> usersincome = monthlyservicesRepository.montlyincomeusers(user.get().getUserid());


           usersincome.stream().forEach(entry ->{

               if(entry.getUserid().getUserid().equals(userid)) {
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss.SSSSSSXXX");
                   ZonedDateTime dateTimecheck = ZonedDateTime.parse(fetchMonthlyincome.getDateTime() , formatter);
                   ZonedDateTime dateTimedb = entry.getDatetime();
                   if(dateTimecheck.getMonth().equals(dateTimedb.getMonth())) {
                       if(dateTimecheck.getYear() == dateTimedb.getYear()) {
                           System.out.println(entry);
//                       Optional<Monthyincome> mon = monthlyservicesRepository.monthlyincome(userid , dateTimedb);

                           mi.set(Optional.of(entry));
                       }




                   }
//                     error.set((HttpStatus.SERVICE_UNAVAILABLE));



               }
           });


           if(!(mi.get() == null)) {

               return ResponseEntity.ok(mi);
           }
           else {
               return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("no income found for this month");
           }









//               return ResponseEntity.status(error.get()).body("no income found for this month");




//
//             if(error.get() == null){
//
//                 return ResponseEntity.ok(mi);
//              }
//            else {
//
//                return ResponseEntity.status(error.get()).body("no income found for this month");
//            }






        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error"+ e.getMessage());
        }
    }

    @Override
    public void addmonthlyservice() {

            List<Income> incomes = incomeRepository.findAll();

            incomes.forEach(entry ->{
                Monthyincome monthyincome = new Monthyincome();
                monthyincome.setAmount(entry.getIncome());

                monthyincome.setDatetime(ZonedDateTime.now());
                monthyincome.setUserid(entry.getUserid());

                monthlyservicesRepository.save(monthyincome);
            });




    }
}
