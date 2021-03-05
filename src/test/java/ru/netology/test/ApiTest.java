package ru.netology.test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.helpers.ApiHelper;
import ru.netology.helpers.DataHelper;


public class ApiTest {
    DataHelper.CardInfo approvedCardInfo = DataHelper.getCardInfo("APPROVED");
    DataHelper.CardInfo declinedCardInfo = DataHelper.getCardInfo("DECLINED");

    @BeforeAll
    static void setUp(){
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    @DisplayName("Запрос на покупку по карте со статусом APPROVED")
    @Test
    void shouldApprovePayment() {
        ApiHelper.createPayment(approvedCardInfo);
    }

    @DisplayName("Запрос на кредит по карте со статусом APPROVED")
    @Test
    void shouldApproveCredit() {
        ApiHelper.createCredit(approvedCardInfo);
    }

    @DisplayName("Запрос на покупку по карте со статусом DECLINE")
    @Test
    void shouldDeclinePayment() {
        ApiHelper.createPayment(declinedCardInfo);
    }

    @DisplayName("Запрос на кредит по карте со статусом DECLINE")
    @Test
    void shouldDeclineCredit() {
        ApiHelper.createCredit(declinedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на покупку по карте с неправильным номером : {0}")
    @CsvSource(value = {"''", "4444444400000000", "44444444", "44444444444444440"})
    void shouldNotPaymentWithWrongCardNumber(String value) {
        approvedCardInfo.setNumber(value);
        ApiHelper.createPaymentError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на кредит по карте с неправильным номером : {0}")
    @CsvSource(value = {"''", "4444444400000000", "44444444", "44444444444444440"})
    void shouldNotCreditWithWrongCardNumber(String value) {
        approvedCardInfo.setNumber(value);
        ApiHelper.createCreditError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на покупку по карте с неправильным месяцем : {0}")
    @CsvSource(value = {"''", "00", "01", "2", "12", "13", "ab"})
    void shouldNotPaymentWithWrongMonth(String value) {
        approvedCardInfo.setMonth(value);
        ApiHelper.createPaymentError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на кредит по карте с неправильным месяцем : {0}")
    @CsvSource(value = {"''", "00", "01", "2", "12", "13", "a#"})
    void shouldNotCreditWithWrongMonth(String value) {
        approvedCardInfo.setMonth(value);
        ApiHelper.createCreditError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на покупку по карте с неправильным годом : {0}")
    @CsvSource(value = {"''", "20", "21", "26", "27", "a#"})
    void shouldNotPaymentWithWrongYear(String value) {
        approvedCardInfo.setYear(value);
        ApiHelper.createPaymentError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на кредит по карте с неправильным годом : {0}")
    @CsvSource(value = {"''", "20", "21", "26", "27", "a#"})
    void shouldNotCreditWithWrongYear(String value) {
        approvedCardInfo.setYear(value);
        ApiHelper.createCreditError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на покупку по карте с неправильным месяцем и годом {0}.{1}")
    @CsvSource(value = {"-1, 0", "1, 5"})
    void shouldNotPaymentWithWrongMonthYear(int deltaMonth, int deltaYear) {
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(deltaMonth,deltaYear);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        ApiHelper.createPaymentError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на кредит по карте с неправильным месяцем и годом {0}.{1}")
    @CsvSource(value = {"-1, 0", "1, 5"})
    void shouldNotCreditWithWrongMonthYear(int deltaMonth, int deltaYear) {
        DataHelper.MonthYear monthYear = DataHelper.getMonthYear(deltaMonth,deltaYear);
        approvedCardInfo.setMonth(monthYear.getMonth());
        approvedCardInfo.setYear(monthYear.getYear());
        ApiHelper.createCreditError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на покупку по карте с неправильным именем : {0}")
    @CsvSource(value = {"''", "a", "ahdwkeococooddkkdkldldahdwkeococooddkkdkldldahdwkeococooddkkdkldl", "12"})
    void shouldNotPaymentWithWrongName(String value) {
        approvedCardInfo.setHolder(value);
        ApiHelper.createPaymentError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на кредит по карте с неправильным именем : {0}")
    @CsvSource(value = {"''", "a", "ahdwkeococooddkkdkldldahdwkeococooddkkdkldldahdwkeococooddkkdkldl", "12"})
    void shouldNotCreditWithWrongName(String value) {
        approvedCardInfo.setHolder(value);
        ApiHelper.createCreditError(approvedCardInfo);
    }


    @ParameterizedTest(name = "Запрос на покупку по карте с неправильным cvc : {0}")
    @CsvSource(value = {"''", "44", "4444", "abc"})
    void shouldNotPaymentWithWrongCvc(String value) {
        approvedCardInfo.setCvc(value);
        ApiHelper.createPaymentError(approvedCardInfo);
    }

    @ParameterizedTest(name = "Запрос на кредит по карте с неправильным cvc : {0}")
    @CsvSource(value = {"''", "44", "4444", "abc"})
    void shouldNotCreditWithWrongCvc(String value) {
        approvedCardInfo.setCvc(value);
        ApiHelper.createCreditError(approvedCardInfo);
    }
}
