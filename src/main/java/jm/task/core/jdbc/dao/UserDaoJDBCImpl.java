package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCreatTable = "CREATE TABLE IF NOT EXISTS users1 (ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(20), LASTNAME VARCHAR(20), AGE TINYINT(120) NOT NULL)";
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            statement.executeUpdate(sqlCreatTable);
        } catch (SQLException e) {
            log.log(Level.WARNING, "Error in createUsersTable", e);
        }
    }

    public void dropUsersTable() {
        String delTable = "DROP TABLE IF EXISTS users1";
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement(); /*ResultSet rs = statement.executeQuery(query)*/) {
            statement.executeUpdate(delTable);
        } catch (SQLException e) {
            log.log(Level.WARNING, "Error in dropUsersTable", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users1 (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try (Connection conn = Util.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(saveUser);) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.WARNING, "Error in saveUser", e);
        }
    }

    public void removeUserById(long id) {
        String delTable = "DELETE FROM users1 WHERE id = ?";
        try (Connection conn = Util.getConnection(); PreparedStatement statement = conn.prepareStatement(delTable)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.WARNING, "Error in removeUserById", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userlist = new ArrayList<>();
        String userAll = "SELECT * FROM users1";
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(userAll)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userlist.add(user);
            }
        } catch (SQLException e) {
            log.log(Level.WARNING, "Error in getAllUsers", e);
        }
        System.out.println(userlist);
        return userlist;
    }

    public void cleanUsersTable() {
        String delTable = "TRUNCATE TABLE users1";
        try (Connection conn = Util.getConnection(); PreparedStatement statement = conn.prepareStatement(delTable)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.WARNING, "Error in cleanUsersTable", e);
        }
    }
}
