package ru.netology.entity.managers;

import ru.netology.entity.dto.CardData;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class CardManager {

    private final ArrayList<CardData> cards;

    public CardManager(ArrayList<CardData> cards) {
        this.cards = cards;
    }

    public CardData getCardByStatus(String status) {
        for (CardData card : cards) {
            if (card.getStatus().equals(status)) {
                return card;
            }
        }
        throw new NoSuchElementException();
    }

    public String getStatusByCardNumber(String number) {
        for (CardData card : cards) {
            if (card.getNumber().equals(number)) {
                return card.getStatus();
            }
        }
        // FIXME: Need add error message for a nice logging
        throw new NoSuchElementException();
    }

}
