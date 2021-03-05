package ru.netology.entity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Value;


@Value
public class CardData {
    @SerializedName("number")
    public String number;
    @SerializedName("status")
    public String status;

}
