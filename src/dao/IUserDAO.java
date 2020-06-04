package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void insertUser(String name,String email, String country) throws SQLException;
    User selectUser(int id) throws SQLException;
    List<User> selectAllUsers() throws SQLException;
    boolean deleteUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
}
