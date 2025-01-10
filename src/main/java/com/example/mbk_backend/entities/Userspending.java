package com.example.mbk_backend.entities;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "userspending")
public final class Userspending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseid;
    private String itemname;
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;
    private Double amount;
    private ZonedDateTime dateTime;
    private Boolean paidstatus;
    private Boolean repeats;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User userid;

    public Integer getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(Integer purchaseid) {
        this.purchaseid = purchaseid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getPaidstatus() {
        return paidstatus;
    }

    public void setPaidstatus(Boolean paidstatus) {
        this.paidstatus = paidstatus;
    }

    public Boolean getRepeats() {
        return repeats;
    }

    public void setRepeats(Boolean repeats) {
        this.repeats = repeats;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }
}
