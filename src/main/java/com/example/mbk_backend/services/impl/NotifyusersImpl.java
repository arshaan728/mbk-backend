package com.example.mbk_backend.services.impl;

import com.example.mbk_backend.entities.Userspending;
import com.example.mbk_backend.repository.UserspendingRespository;
import com.example.mbk_backend.services.NotifyusersService;
import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service

public class NotifyusersImpl implements NotifyusersService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserspendingRespository userspendingRespository;

    @Scheduled(cron = "0 34 18 6 * ?")
    public void callEmail() {
        sendEmail();
    }
    @Override
    public void sendEmail() {
        List<Userspending> getrecords = userspendingRespository.getallrepeatingitems();
        System.out.println(getrecords);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("arshanarul@gmail.com");
        getrecords.forEach(entry ->{
            message.setTo(entry.getUserid().getEmail());
            message.setSubject("Reminder From MBK");
            message.setText("This email is a Reminder to pay your " + entry.getItemname() +"before " + entry.getDateTime());
            javaMailSender.send(message);



        });





    }


}
