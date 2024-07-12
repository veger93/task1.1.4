package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    private static Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/task1.1.4";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Error connection");
        }
        return connection;
    }
}
