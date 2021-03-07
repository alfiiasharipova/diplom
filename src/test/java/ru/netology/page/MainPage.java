package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement payButton = $(byText("Купить"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));

    @Step("Нажать на кнопку Купить")
    public BuyTourPage clickPayButton() {
        payButton.click();
        return new BuyTourPage();
    }

    @Step("Нажать на кнопку Купить в кредит")
    public BuyTourPage clickCreditButton() {
        creditButton.click();
        return new BuyTourPage();
    }
}
