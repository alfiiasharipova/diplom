package ru.netology.helpers;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.entity.db.DataBaseCredit;
import ru.netology.entity.db.DataBaseOrder;
import ru.netology.entity.db.DataBasePayment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(
                    System.getenv("DB_URL"),
                    "app",
                    "pass");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static void clearDB()  {
        val codeSQL ="DELETE FROM order_entity;";
        val codeSQL2 ="DELETE FROM credit_request_entity;";
        val codeSQL3 ="DELETE FROM payment_entity;";
        val runner =new QueryRunner();
        try (
                val conn = getDBConnection();
            ) {
                runner.update(conn, codeSQL);
                runner.update(conn, codeSQL2);
                runner.update(conn, codeSQL3);
            }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DataBasePayment getDBPayment() throws SQLException {
        val paymentSQL = "SELECT * FROM payment_entity;";
        val runner = new QueryRunner();

        try (
                val conn = getDBConnection();
        ) {
            return runner.query(conn, paymentSQL, new BeanHandler<>(DataBasePayment.class));
        }
    }

    public static DataBaseCredit getDBCredit() throws SQLException {
        val creditSQL = "SELECT * FROM credit_request_entity;";
        val runner = new QueryRunner();

        try (
                val conn = getDBConnection();
        ) {
            return runner.query(conn, creditSQL, new BeanHandler<>(DataBaseCredit.class));
        }
    }

    public static DataBaseOrder getDBOrder() throws SQLException {
        val orderSQL = "SELECT * FROM order_entity;";
        val runner = new QueryRunner();

        try (
                val conn = getDBConnection();
        ) {
            return runner.query(conn, orderSQL, new BeanHandler<>(DataBaseOrder.class));
        }
    }
}
