package ru.netology.entity.enums;

import lombok.Getter;

@Getter
public enum ErrorText {
    INVALID_FORMAT ("Неверный формат"),
    REQUIRED_FIELD ("Поле обязательно для заполнения"),
    INVALID_CARD_DATE ("Неверно указан срок действия карты"),
    EXPIRED_CARD ("Истёк срок действия карты");

    private final String errorText;

    ErrorText(String errorText) {
        this.errorText = errorText;
    }
}
