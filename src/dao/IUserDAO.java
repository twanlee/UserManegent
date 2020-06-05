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
    List<User> findByCountry(String country) throws SQLException;
    List<User> sort() throws SQLException;
    User get_user_by_id(int user_id) throws SQLException;
    void insert_user_store(String name, String email, String country) throws SQLException;
    void insert_update_without_tran() throws SQLException;
    void insert_update_with_tran() throws SQLException;

}
