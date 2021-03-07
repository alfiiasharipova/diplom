package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.netology.helpers.DataHelper;
import ru.netology.helpers.DataHelper.CardInfo;
import ru.netology.entity.enums.ErrorText;
import ru.netology.page.BuyTourPage;
import ru.netology.page.MainPage;


import static com.codeborne.selenide.AssertionMode.SOFT;
import static com.codeborne.selenide.Selenide.*;

@ExtendWith({SoftAssertsExtension.class})
public class PaymentTest {
    MainPage mainPage;
    BuyTourPage buyTourPage;
    CardInfo approvedCardInfo = DataHelper.getCardInfo("APPROVED");
    CardInfo declinedCardInfo = DataHelper.getCardInfo("DECLINED");
    CardInfo cardInfoWrongFormat = new DataHelper.CardInfo("55", "1", "2", "!", "1");


    @BeforeAll
    static void configuration(){
        Configuration.assertionMode = SOFT;
    }

    @Step("Перейти на сайт")
    @BeforeEach
    void setUp(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true));
        mainPage = open("http://localhost:8080", MainPage.class);
    }

    @Test
    @DisplayName("Покупка тура по карте со статусом APPROVED")
    void shouldPayWithApprovedStatusCard() {
        buyTourPage =  mainPage.clickPayButton();
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkNotificationOk(DataHelper.getStatusByCard(approvedCardInfo));
    }

    @Test
    @DisplayName("Ошибка при покупке тура по карте со статусом DECLINED")
    void shouldNotPayWithDeclinedStatusCard() {
        buyTourPage =  mainPage.clickPayButton();
        buyTourPage.fillForm(declinedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkNotificationOk(DataHelper.getStatusByCard(declinedCardInfo));
    }

    @Test
    @DisplayName("Оформление кредита по карте со статусом APPROVED")
    void shouldCreditWithApprovedStatusCard() {
        buyTourPage =  mainPage.clickCreditButton();
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkNotificationOk(DataHelper.getStatusByCard(approvedCardInfo));
    }

    @Test
    @DisplayName("Ошибка при оформлении кредита по карте со статусом DECLINED")
    void shouldNotCreditWithDeclinedStatusCard() {
        buyTourPage =  mainPage.clickCreditButton();
        buyTourPage.fillForm(declinedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkNotificationOk(DataHelper.getStatusByCard(declinedCardInfo));
    }

    @Test
    @DisplayName("Покупка тура по несуществующей карте")
    void shouldNotPayWithNotExistedCard() {
        buyTourPage =  mainPage.clickPayButton();
        String status = DataHelper.getStatusByCard(declinedCardInfo);
        declinedCardInfo.setNumber(DataHelper.getNotExistedCardNumber());
        buyTourPage.fillForm(declinedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkNotificationOk(status);
    }

    @Test
    @DisplayName("Ошибка при оформлении кредита по несуществующей карте")
    void shouldNotCreditWithNotExistedCard() {
        buyTourPage =  mainPage.clickCreditButton();
        String status = DataHelper.getStatusByCard(declinedCardInfo);
        declinedCardInfo.setNumber(DataHelper.getNotExistedCardNumber());
        buyTourPage.fillForm(declinedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkNotificationOk(status);
    }

    @Test
    @DisplayName("Ошибки при покупке тура с пустыми полями")
    void shouldNotPayWithEmptyFields() {
        buyTourPage =  mainPage.clickPayButton();
        buyTourPage.clickConfirmButton();
        buyTourPage.checkCardNumberError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkMonthError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkYearError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkNameError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkCvcError(ErrorText.REQUIRED_FIELD);
    }

    @Test
    @DisplayName("Ошибки при оформлении кредита с пустыми полями")
    void shouldNotCreditWithEmptyFields() {
        buyTourPage =  mainPage.clickCreditButton();
        buyTourPage.clickConfirmButton();
        buyTourPage.checkCardNumberError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkMonthError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkYearError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkNameError(ErrorText.REQUIRED_FIELD);
        buyTourPage.checkCvcError(ErrorText.REQUIRED_FIELD);
    }

    @Test
    @DisplayName("Ошибки при покупке тура с полями неверного формата")
    void shouldNotPayWithWrongFormatFields() {
        buyTourPage =  mainPage.clickPayButton();
        buyTourPage.clickConfirmButton();
        buyTourPage.fillForm(cardInfoWrongFormat);
        buyTourPage.checkCardNumberError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkMonthError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkYearError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkNameError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkCvcError(ErrorText.INVALID_FORMAT);
    }

    @Test
    @DisplayName("Ошибки при оформлении кредита с полями неверного формата")
    void shouldNotCreditWithWrongFormatFields() {
        buyTourPage =  mainPage.clickCreditButton();
        buyTourPage.clickConfirmButton();
        buyTourPage.fillForm(cardInfoWrongFormat);
        buyTourPage.checkCardNumberError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkMonthError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkYearError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkNameError(ErrorText.INVALID_FORMAT);
        buyTourPage.checkCvcError(ErrorText.INVALID_FORMAT);
    }

    // date
    @Test
    @DisplayName("Проверка истекшей карты с полем месяц при покуке тура")
    void shouldNotPayWithExpiredDataMonth() {
        buyTourPage =  mainPage.clickPayButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(-1,0);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkMonthError(ErrorText.EXPIRED_CARD);
    }

    @Test
    @DisplayName("Проверка истекшей карты с полем год при покуке тура")
    void shouldNotPayWithExpiredDataYear() {
        buyTourPage =  mainPage.clickPayButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(0,-1);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkYearError(ErrorText.EXPIRED_CARD);
    }

    @Test
    @DisplayName("Проверка невалидного срока карты больше 5 лет с полем месяц при покуке тура")
    void shouldNotPayWithInvalidDataMonth() {
        buyTourPage =  mainPage.clickPayButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(1,5);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkMonthError(ErrorText.INVALID_CARD_DATE);
    }

    @Test
    @DisplayName("Проверка невалидного срока карты больше 5 лет с полем год при покуке тура")
    void shouldNotPayWithInvalidDataYear() {
        buyTourPage =  mainPage.clickPayButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(0,6);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkYearError(ErrorText.INVALID_CARD_DATE);
    }

    @Test
    @DisplayName("Проверка истекшей карты с полем месяц при оформлении кредита")
    void shouldNotCreditWithExpiredDataMonth() {
        buyTourPage =  mainPage.clickCreditButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(-1,0);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkMonthError(ErrorText.EXPIRED_CARD);
    }

    @Test
    @DisplayName("Проверка истекшей карты с полем год при оформлении кредита")
    void shouldNotCreditWithExpiredDataYear() {
        buyTourPage =  mainPage.clickCreditButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(0,-1);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkYearError(ErrorText.EXPIRED_CARD);
    }

    @Test
    @DisplayName("Проверка невалидного срока карты больше 5 лет с полем месяц при оформлении кредита")
    void shouldNotCreditWithInvalidDataMonth() {
        buyTourPage =  mainPage.clickCreditButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(1,5);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkMonthError(ErrorText.INVALID_CARD_DATE);
    }

    @Test
    @DisplayName("Проверка невалидного срока карты больше 5 лет с полем год при оформлении кредита")
    void shouldNotCreditWithInvalidDataYear() {
        buyTourPage =  mainPage.clickCreditButton();
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(0,6);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        buyTourPage.fillForm(approvedCardInfo);
        buyTourPage.clickConfirmButton();
        buyTourPage.checkYearError(ErrorText.INVALID_CARD_DATE);
    }
}
