package ru.netology.helpers;

public class StringFormater {
    public static String getDateWithNull(int number){
        return String.format("%02d", number);
    }

    public static String getCvcWithNull(int number){
        return String.format("%03d", number);
    }
}
