package ru.netology.helpers;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.*;
import ru.netology.entity.managers.CardManager;
import ru.netology.entity.dto.CardData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.netology.helpers.StringFormater.getCvcWithNull;
import static ru.netology.helpers.StringFormater.getDateWithNull;

public class DataHelper {
  private final static CardManager cardManager = new CardManager((ArrayList<CardData>) getTestedCardInfo());

  private DataHelper() {}

  @Data
  @AllArgsConstructor
  public static class MonthYear {
    private String month;
    private String year;
  }

  @Data
  @AllArgsConstructor
  public static class CardInfo {
    private String number;
    private String month;
    private String year;
    private String holder;
    private String cvc;
  }

  private static List<CardData> getTestedCardInfo()  {
    val REVIEW_TYPE = new TypeToken<List<CardData>>() {
    }.getType();
    Gson gson = new Gson();
    JsonReader reader = null;
    try {
      reader = new JsonReader(new FileReader("gate-simulator/data.json"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    assert reader != null;
    return gson.fromJson(reader, REVIEW_TYPE);
  }

  public static CardInfo getCardInfo(String status) {
    Faker faker = new Faker(new Locale("en"));
    String name = faker.name().fullName();
    String cvc = getCvcWithNull((int)(Math.random()*1000));
    CardData card = cardManager.getCardByStatus(status);
    return new CardInfo(card.getNumber(), "03", "22", name, cvc);
  }

  public static String getNotExistedCardNumber() {
    Faker faker = new Faker(new Locale("en"));
    return faker.finance().creditCard(CreditCardType.VISA).replace("-","");
  }

  public static String getStatusByCard(CardInfo card){
    return cardManager.getStatusByCardNumber(card.getNumber());
  }

  public static MonthYear getMonthYear(int month, int year){
    LocalDate date = LocalDate.now();
    int newMonth = date.plusMonths(month).getMonthValue();
    int newYear = date.plusYears(year).getYear()%100;
    return new MonthYear(getDateWithNull(newMonth), getDateWithNull(newYear));
  }

}


