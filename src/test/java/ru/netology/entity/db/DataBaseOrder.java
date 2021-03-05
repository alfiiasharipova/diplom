package ru.netology.entity.db;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseOrder {
    private String id;
    private Timestamp created;
    private String credit_id;
    private String payment_id;
}
