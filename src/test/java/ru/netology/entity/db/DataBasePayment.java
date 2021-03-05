package ru.netology.entity.db;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBasePayment {
    private String id;
    private int amount;
    private Timestamp created;
    private String status;
    private String transaction_id;
}
