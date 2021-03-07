package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.netology.helpers.DataHelper;
import ru.netology.entity.enums.ErrorText;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class BuyTourPage {
    private final SelenideElement cardNumber = $x(createXPathForField("Номер карты"));
    private final SelenideElement month = $x(createXPathForField("Месяц"));
    private final SelenideElement year = $x(createXPathForField("Год"));
    private final SelenideElement name = $x(createXPathForField("Владелец"));
    private final SelenideElement cvc = $x(createXPathForField("CVC/CVV"));
    private final SelenideElement cardNumberError = $x(createXPathForError("Номер карты"));
    private final SelenideElement monthError = $x(createXPathForError("Месяц"));
    private final SelenideElement yearError = $x(createXPathForError("Год"));
    private final SelenideElement nameError = $x(createXPathForError("Владелец"));
    private final SelenideElement cvcError = $x(createXPathForError("CVC/CVV"));
    private final SelenideElement confirmButton = $(byText("Продолжить"));
    private final SelenideElement notificationOk = $(".notification_status_ok");
    private final SelenideElement tourAmount = $x("//*[contains(text(), 'руб')]");

    @Step("Ввести номер карты")
    public void enterCardNumber(String cardNumber) {
        this.cardNumber.setValue(cardNumber);
    }

    @Step("Ввести месяц окончания действия карты")
    public void enterMonth(String month) {
        this.month.setValue(month);
    }

    @Step("Ввести год окончания действия карты")
    public void enterYear(String year) {
        this.year.setValue(year);
    }

    @Step("Ввести имя владельца карты")
    public void enterName(String name) {
        this.name.setValue(name);
    }

    @Step("Ввести cvc код")
    public void enterCVC(String cvc) {
        this.cvc.setValue(cvc);
    }

    @Step("Нажать на кнопку Продолжить")
    public void clickConfirmButton() {
        confirmButton.click();
    }

    @Step("Проверить нотификацию")
    public void checkNotificationOk(String status) {
        String text = "Операция одобрена Банком";
        if (!status.equals("APPROVED")) {
            text = "Банк отказал в проведении операции";
        }
        notificationOk.shouldHave(text(text), Duration.ofSeconds(10));
    }

    @Step("Заполнить форму данными")
    public void fillForm(DataHelper.CardInfo cardInfo){
        enterCardNumber(cardInfo.getNumber());
        enterMonth(cardInfo.getMonth());
        enterYear(cardInfo.getYear());
        enterName(cardInfo.getHolder());
        enterCVC(cardInfo.getCvc());

    }

    private String createXPathForField(String filter){
        return String.format("//span[text() = '%s']/..//input", filter);
    }

    private String createXPathForError(String filter){
        return String.format("//span[text() = '%s']/..//span[@class = 'input__sub']", filter);
    }

    @Step("Проверить текст ошибки {text}")
    private void checkErrorOk(SelenideElement error, ErrorText text) {
        error.shouldHave(text(text.getErrorText()));
    }

    public void checkCardNumberError(ErrorText text){ checkErrorOk(cardNumberError, text); }

    public void checkMonthError(ErrorText text){
        checkErrorOk(monthError, text);
    }

    public void checkYearError(ErrorText text){
        checkErrorOk(yearError, text);
    }

    public void checkNameError(ErrorText text){
        checkErrorOk(nameError, text);
    }

    public void checkCvcError(ErrorText text){
        checkErrorOk(cvcError, text);
    }

    @Step("Получить цену тура")
    public String getAmount() {
        String result ="";
        String tourInfo = tourAmount.getText();
        final Pattern pattern = Pattern.compile("\\d+ \\d+");
        final Matcher matcher = pattern.matcher(tourInfo);
        while (matcher.find()) {
            result = matcher.group(0);
        }
        return result;
    }

}
