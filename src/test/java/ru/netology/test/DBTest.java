package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.helpers.ApiHelper;
import ru.netology.helpers.DBHelper;
import ru.netology.helpers.DataHelper;
import ru.netology.page.BuyTourPage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTest {

    @BeforeEach
    void clearDB(){
        DBHelper.clearDB();
    }

    @ParameterizedTest
    @ValueSource(strings = {"APPROVED", "DECLINED"})
    void shouldApprovePaymentSQL(String status) throws SQLException {
        DataHelper.CardInfo card = DataHelper.getCardInfo(status);
        ApiHelper.createPayment(card);
        LocalDate localDate = LocalDate.now(ZoneOffset.UTC);
        val newPayment = DBHelper.getDBPayment();
        val newOrder = DBHelper.getDBOrder();
        Assertions.assertAll(
                () -> assertThat(newPayment.getId())
                        .isEqualTo(newOrder.getPayment_id()),
                () -> assertThat(newPayment.getStatus())
                        .isEqualTo(status),
                () -> assertThat(localDate)
                        .isEqualTo(newPayment.getCreated().toLocalDateTime().toLocalDate()),
                () -> assertThat(localDate)
                        .isEqualTo(newOrder.getCreated().toLocalDateTime().toLocalDate())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"APPROVED", "DECLINED"})
    void shouldApproveCreditSQL(String status) throws SQLException {
        DataHelper.CardInfo card = DataHelper.getCardInfo(status);
        ApiHelper.createCredit(card);
        LocalDate localDate = LocalDate.now(ZoneOffset.UTC);
        val newCredit = DBHelper.getDBCredit();
        val newOrder = DBHelper.getDBOrder();
        Assertions.assertAll(
                () -> assertThat(newCredit.getId())
                        .isEqualTo(newOrder.getPayment_id()),
                () -> assertThat(newCredit.getStatus())
                        .isEqualTo(status),
                () -> assertThat(localDate)
                        .isEqualTo(newCredit.getCreated().toLocalDateTime().toLocalDate()),
                () -> assertThat(localDate)
                        .isEqualTo(newOrder.getCreated().toLocalDateTime().toLocalDate())
        );
    }

    @Test
    void shouldAmountsEquals() throws SQLException {
        BuyTourPage buyTourPage = open("http://localhost:8080", BuyTourPage.class);
        String amountFromPage = buyTourPage.getAmount();
        DataHelper.CardInfo card = DataHelper.getCardInfo("APPROVED");
        ApiHelper.createPayment(card);
        val newPayment = DBHelper.getDBPayment();
        assertEquals(amountFromPage.replace(" ",""), String.valueOf(newPayment.getAmount()));
    }
}
