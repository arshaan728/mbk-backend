package com.example.mbk_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "monthlyincome")
public class Monthyincome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer monthlyincomeid;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User userid;
    private ZonedDateTime datetime;

    public Integer getMonthlyincomeid() {
        return monthlyincomeid;
    }

    public void setMonthlyincomeid(Integer monthlyincomeid) {
        this.monthlyincomeid = monthlyincomeid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }
}
