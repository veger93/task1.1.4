package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserServiceImpl implements UserService  {
    //Connection connection = getConnection();

    UserDao userDaoJDBC = new UserDaoJDBCImpl();

    public UserServiceImpl() throws SQLException {
    }

    public void createUsersTable() throws SQLException {
        System.out.println("Создаем таблицу пользователей");
userDaoJDBC.createUsersTable();

    }

    public void dropUsersTable() throws SQLException {
        System.out.println("Удаляем таблицу пользователей");
userDaoJDBC.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        System.out.println("User с именем - " + name + " добавлен в базу данных");
        userDaoJDBC.saveUser(name,lastName,age);
    }

    public void removeUserById(long id) {
        System.out.println("Удаляем пользователя под индексом " + id);
userDaoJDBC.removeUserById(id);
    }

    public List<User> getAllUsers() {
        System.out.println("Вывод всей таблицы");
        try {
            return userDaoJDBC.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanUsersTable() throws SQLException {
        System.out.println("Очищаем таблицу пользователей");
userDaoJDBC.cleanUsersTable();
    }

}
