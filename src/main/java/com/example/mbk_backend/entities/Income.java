package com.example.mbk_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZoneOffset;

@Data
@Entity
@Table(name = "income")
public class Income {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer incomeid;
   @ManyToOne
   @JoinColumn(name = "userid")
   private User userid;
   private Double income;


    public Integer getIncomeid() {
        return incomeid;
    }

    public void setIncomeid(Integer incomeid) {
        this.incomeid = incomeid;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public Double getIncome(ZoneOffset utc) {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }


}
