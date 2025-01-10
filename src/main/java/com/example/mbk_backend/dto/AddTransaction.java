package com.example.mbk_backend.dto;

import com.google.api.client.util.DateTime;
import lombok.Data;

@Data
public class AddTransaction {

    private String itemname;
    private Double amount;
    private String dateTime;
    private String expensetype;
    private String repeats;
    private Integer userid;
}
