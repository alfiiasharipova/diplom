package ru.netology.helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiHelper {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void createPayment(DataHelper.CardInfo cardData){
        String status = DataHelper.getStatusByCard(cardData);
        given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200)
                .body("status", equalTo(status));
    }

    public static void createCredit(DataHelper.CardInfo cardData){
        String status = DataHelper.getStatusByCard(cardData);
        given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(200)
                .body("status", equalTo(status));
    }

    public static void createPaymentError(DataHelper.CardInfo cardData){
        given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(500);
    }

    public static void createCreditError(DataHelper.CardInfo cardData){
        given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(500);
    }
}
