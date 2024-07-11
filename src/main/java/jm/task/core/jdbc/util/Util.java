package jm.task.core.jdbc.util;

import javax.sound.midi.Soundbank;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
 //   private static final String DB_Driver = "org.h2.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/task1.1.4";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connekt ok");
        } catch (SQLException e) {
           e.printStackTrace();
            System.out.println("error");
        }
        return connection;
    }

}
