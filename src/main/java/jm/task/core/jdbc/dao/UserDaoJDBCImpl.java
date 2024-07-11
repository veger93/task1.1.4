package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.lang.model.element.Name;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection conn = getConnection();
    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() {
String  sqlCreatTable = "CREATE TABLE users1 (ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(20), LASTNAME VARCHAR(20), AGE TINYINT(120))";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sqlCreatTable);

} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable()  {
        Connection conn = getConnection();
String delTable = "DROP TABLE users1";
try (PreparedStatement statement = conn.prepareStatement(delTable)) {
    statement.executeUpdate();
} catch (SQLException e) {
    throw new RuntimeException(e);
}
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection conn = getConnection();
        String saveUser = "INSERT INTO users1 (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(saveUser);
        try {
            //preparedStatement = conn.prepareStatement(saveUser);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void removeUserById(long id) {
        String delTable = "DELETE FROM users1 WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(delTable)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userlist = new ArrayList<>();

       String userAll = "SELECT * FROM users1";
       Statement statement = null;
       try {
           statement = conn.createStatement();

           ResultSet resultSet = statement.executeQuery(userAll);

           while (resultSet.next()) {
               User user = new User();
               user.setId(resultSet.getLong("ID"));
               user.setName(resultSet.getString("NAME"));
               user.setLastName(resultSet.getString("LASTNAME"));
             //  user.getAge(resultSet.getByte("AGE"));
               userlist.add(user);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           if (statement != null) {
               statement.close();
           }
           if (conn != null) {
               conn.close();
           }
       }
        return userlist;
    }

    public void cleanUsersTable() {
        Connection conn = getConnection();
        String delTable = "TRUNCATE TABLE users1";
        try (PreparedStatement statement = conn.prepareStatement(delTable)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
