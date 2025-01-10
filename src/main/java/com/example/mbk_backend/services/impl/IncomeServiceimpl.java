package com.example.mbk_backend.services.impl;

import com.example.mbk_backend.config.RestTemplateConfig;
import com.example.mbk_backend.dto.Addincome;
import com.example.mbk_backend.dto.Fetchexpenses;
import com.example.mbk_backend.entities.ExpenseType;
import com.example.mbk_backend.entities.Income;
import com.example.mbk_backend.entities.User;
import com.example.mbk_backend.entities.Userspending;
import com.example.mbk_backend.repository.IncomeRepository;
import com.example.mbk_backend.repository.UserRepository;
import com.example.mbk_backend.repository.UserspendingRespository;
import com.example.mbk_backend.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
public class IncomeServiceimpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserspendingRespository userspendingRespository;
    @Autowired
    RestTemplateConfig restTemplateConfig;

    @Value("${python.url}")
    private String apiurl;

    @Override
    public ResponseEntity<?> addincome(Addincome addincome) {
        try {
            System.out.println(addincome.getEmail() + addincome.getIncome());
            String userid = addincome.getEmail();
            Optional<User> users = userRepository.findByEmail(addincome.getEmail());
            if(users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user doesnt exist");
            }
             Optional<Income> income = incomeRepository.findByUserid(users.get().getUserid());
            System.out.println(income);

            if(income.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user already has a income amount");
            }

            Income income1 = new Income();
            income1.setUserid(users.get());
            income1.setIncome(addincome.getIncome());
            Income savedincome = incomeRepository.save(income1);

            return ResponseEntity.ok(savedincome);


        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> changeincome(Addincome addincome) {
        try {
            Optional<User> users = userRepository.findByEmail(addincome.getEmail());

            if(users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

            }

            incomeRepository.updateincome(addincome.getIncome() , users.get().getUserid());

            return ResponseEntity.ok("updated");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> fetchincome(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);

            if(user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
            }

            Optional<Income> income = incomeRepository.findByUserid(user.get().getUserid());

            if(income.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("doesnt have a income");
            }

            return ResponseEntity.ok(income);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> fetchexpenses(Fetchexpenses fetchexpenses) {
       try {
           Optional<User> user = userRepository.findByEmail(fetchexpenses.getEmail());

           if(user.isEmpty()) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
           }

           List<Userspending> fetchexp = userspendingRespository.gettimeMonth(user.get().getUserid());
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX");

           ZonedDateTime date = ZonedDateTime.parse(fetchexpenses.getDateTime() , formatter);

           String monthva = String.format("%02d", date.getMonthValue());

           int year = date.getYear();

           ZonedDateTime startdate = ZonedDateTime.parse(year+"-"+monthva+"-01 01:40:00.092494+05:30" ,formatter);

           ZonedDateTime enddate = ZonedDateTime.parse(year+"-"+monthva+"-31 01:40:00.092494+05:30" ,formatter);

           List<Userspending> fetchexpbyyear = userspendingRespository.getexpensesmonth(user.get().getUserid() , startdate , enddate);



           return ResponseEntity.ok(fetchexpbyyear);





       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
       }
    }

    @Override
    public ResponseEntity<?> fetchexpensesbycategory(Fetchexpenses fetchexpenses) {
        try {

            List<ExpenseType> expenseTypes = List.of(ExpenseType.values());
            ArrayList<HashMap<String , String>> finalvalues = new ArrayList<>();
            Optional<User> user = userRepository.findByEmail(fetchexpenses.getEmail());

            if(user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
            }

            List<Userspending> fetchexp = userspendingRespository.gettimeMonth(user.get().getUserid());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX");

            ZonedDateTime date = ZonedDateTime.parse(fetchexpenses.getDateTime() , formatter);


            String monthva = String.format("%02d", date.getMonthValue());
//            System.out.println(Integer.parseInt(monthva));

            int year = date.getYear();

            ZonedDateTime startdate = ZonedDateTime.parse(year+"-"+monthva+"-01 01:40:00.092494+05:30" ,formatter);

            ZonedDateTime enddate = ZonedDateTime.parse(year+"-"+monthva+"-31 01:40:00.092494+05:30" ,formatter);



           expenseTypes.forEach(val ->{
               Double indval = userspendingRespository.getexpenesmonthforeachcategory(val ,user.get().getUserid() , startdate , enddate );
               HashMap<String , String> values = new HashMap<>();

               values.put("value" , String.valueOf(indval));
               values.put("label", String.valueOf(val));

               finalvalues.add(values);


           });


//            fetchexpbyyear.forEach(entry ->{
//
//              if (checkvlaues.isEmpty()) {
//
//                  checkvlaues.put(String.valueOf(entry.getExpenseType()) , entry.getAmount());
//
//              }
//              else {
//
//                  Map<String , Double> updatedcheckvlaues = new HashMap<>(checkvlaues);
//                  updatedcheckvlaues.forEach((exptype , amount)->{
//
//                       System.out.println(exptype);
//
//                      if(exptype.equals(entry.getExpenseType())) {
//
//
//                          checkvlaues.put(String.valueOf(entry.getExpenseType()) , entry.getAmount() + amount);
//                      }
//
//                      else {
//
//                          checkvlaues.put(String.valueOf(entry.getExpenseType()) , entry.getAmount());
//                      }
//
//
//
//
//
//                  });
//              }
//
//
////              values.forEach((category , cost) ->{
////               if(category.equals(entry.getExpenseType())) {
////                   values.put(category ,  (cost + entry.getAmount()));
////               }
////               else {
////                   values.put(String.valueOf(entry.getExpenseType()) , entry.getAmount());
////               }
////              });
//
//
//            });

          System.out.println(finalvalues);
         return ResponseEntity.ok(finalvalues);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> Allexpenses(String email) {
        try {

            Optional<User> useremail = userRepository.findByEmail(email);

            if(useremail.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found" + email);

            }

            List<Userspending> expenses = userspendingRespository.getuserspending(useremail.get().getUserid());



             return  ResponseEntity.ok(expenses);

//            return null;




        }
        catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> trainingdata(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);


            ArrayList expenseTypes = new ArrayList<>(List.of(ExpenseType.values()));
//            ArrayList<HashMap<String , Double>> monthlycategorisedexpenses = new ArrayList<>();
            Map<YearMonth , HashMap<String , Double>> monthylcategorisedexpenses = new HashMap<>();


            if(user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("email not found"+ email);
            }

            Optional<Userspending> firstrecord = userspendingRespository.getfirstrecord(user.get().getUserid());

            Optional<Userspending> lastrecord = userspendingRespository.gelastrecord(user.get().getUserid());


            Duration duration = Duration.between(firstrecord.get().getDateTime() , lastrecord.get().getDateTime());

            String firstmonth = firstrecord.get().getDateTime().format(DateTimeFormatter.ofPattern("MMMM yyyy"));
            String lastmonth = lastrecord.get().getDateTime().format(DateTimeFormatter.ofPattern("MMMM yyyy"));

            YearMonth yearMonthstart = YearMonth.parse(firstmonth ,DateTimeFormatter.ofPattern("MMMM yyyy") );
            YearMonth yearMonthend = YearMonth.parse(lastmonth , DateTimeFormatter.ofPattern("MMMM yyyy"));



            if(duration.toHours() > 1500) {

               List<Userspending> expensesoftheuser = userspendingRespository.getuserspending(user.get().getUserid());

                 YearMonth yeartemp = yearMonthstart;

                do {
                    ArrayList<Userspending> local = new ArrayList<>();
                    for (int i= 0; i < expensesoftheuser.size();i++) {

                        YearMonth yearMonthtemp = YearMonth.parse(expensesoftheuser.get(i).getDateTime().format(DateTimeFormatter.ofPattern("MMMM yyyy")) , DateTimeFormatter.ofPattern("MMMM yyyy"));


                        if (yearMonthtemp.equals(yeartemp)) {
                            local.add(expensesoftheuser.get(i));
                        }
                    }

                    yeartemp = yeartemp.plusMonths(1);

                    for (int i =0; i < expenseTypes.size();i++) {
                        Double amount= (double) 0;
                        ZonedDateTime dt= ZonedDateTime.now();
                        ExpenseType type =ExpenseType.CAR;

                        for(int j=0; j < local.size();j++) {
                            if(local.get(j).getExpenseType().equals(expenseTypes.get(i))) {

                                amount = amount+local.get(j).getAmount();
                                dt = local.get(j).getDateTime();
                                type = local.get(j).getExpenseType();


                            }
                        }




                        HashMap <String ,Double> temporary = new HashMap<>();
                        temporary.put(String.valueOf(type) ,amount);
                        monthylcategorisedexpenses.putIfAbsent(YearMonth.from(dt) , temporary);
                        monthylcategorisedexpenses.get(YearMonth.from(dt)).put(String.valueOf(type) ,amount);



                    }


                }
                while(!yeartemp.isAfter(yearMonthend));

                String url = apiurl + "/getpredicteddata";


                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                HttpEntity<Map<YearMonth, HashMap<String, Double>>> entity = new HttpEntity<>(monthylcategorisedexpenses, headers);

                ResponseEntity<List> response= restTemplateConfig.restTemplate().exchange(url , HttpMethod.POST , entity , List.class);

                return ResponseEntity.ok(response.getBody());

            }
            else {
                return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Need more data to give a proper prediction");
            }


        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e.getMessage());
        }

    }
}
