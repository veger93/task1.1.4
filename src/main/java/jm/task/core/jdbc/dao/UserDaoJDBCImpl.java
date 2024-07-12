package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.lang.model.element.Name;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    //Connection conn = getConnection();
    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() throws SQLException {
        String sqlCreatTable = "CREATE TABLE users1 (ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(20), LASTNAME VARCHAR(20), AGE TINYINT(120) NOT NULL)";
        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE TABLE_NAME = 'users1' LIMIT 1";
        try (Connection conn = getConnection(); Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(query)) {
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Таблица уже существует, удаляем! (сообщ из UserDaoJDBCImpl)");
                dropUsersTable();
            }
            System.out.println("Cоздаем таблицу! (сообщ из UserDaoJDBCImpl)");
            statement.executeUpdate(sqlCreatTable);
        }
    }


    public void dropUsersTable() throws SQLException {
        String delTable = "DROP TABLE users1";
        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE TABLE_NAME = 'users1' LIMIT 1";
        try (Connection conn = getConnection(); Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(query)) {
            if (rs.next() && rs.getInt(1) > 0) {
                statement.executeUpdate(delTable);
            } else {
                System.out.println("Таблица удалена! (сообщ из UserDaoJDBCImpl)");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection conn = getConnection();
        String saveUser = "INSERT INTO users1 (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
             preparedStatement = conn.prepareStatement(saveUser);

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
        try ( Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(delTable)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userlist = new ArrayList<>();
        String userAll = "SELECT * FROM users1";

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(userAll)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userlist.add(user);
            }
        }
        System.out.println(userlist);
        return userlist;
    }

    public void cleanUsersTable() {
        String delTable = "TRUNCATE TABLE users1";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(delTable)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //метод проверки наличия таблицы
//    public static boolean tableSql() throws SQLException {
//        System.out.println("Проверка наличия таблицы методом! (сообщ из UserDaoJDBCImpl)");
//        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'task1.1.4' AND table_name = 'users1'";
//        try (Connection conn = getConnection();
//             Statement s = conn.createStatement();
//             ResultSet rs = s.executeQuery(query);) {
////             Statement statement = conn.prepareStatement("SELECT count(*) "
////                + "FROM information_schema.tables "
////                + "WHERE table_name = users1"
////                + "LIMIT 1;");) {
////            ResultSet resultSet = statement.executeQuery();
////            resultSet.next();
////            return resultSet.getInt(1) != 0;
//
//            if (rs.next() && rs.getInt(1) > 0) {
//                // Таблица существует, выводим trye
//                return true;
//            } else return false;
//        }
//    }

}
