package dao.impl;

import com.mysql.jdbc.PreparedStatement;
import dao.IUserDAO;
import model.User;

import javax.sound.midi.Soundbank;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demoUser";
    private String jdbcUsername = "tuan";
    private String jdbcPassword = "123123";

    private static final String INSERT_USER_SQL = "insert into users" + "(name,email,country) values" + "(?,?,?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id = ?;";
    private static final String SELECT_ALL_USER = "select * from users;";
    private static final String DELETE_USER_SQL = "delete from users where id = ?;";
    private static final String DELETE_USER_PRO = "call delete_user(?)";
    private static final String UPDATE_USER_SQL = "update users set name=?, email = ?, country=? where id=?;";
    private static final String SEARCH_BY_COUNTRY = "select * from users where country like ?;";
    private static final String SORT_BY_NAME = "select * from users order by name";
    private static final String GET_USER_BY_ID = "call get_user_by_id(?)";
    private static final String INSERT_USER = "call insert_user(?,?,?)";
    private static final String selectAll = "call selectAll()";
    private static final String UPDATE_USER_PRO = "call update_user(?,?,?,?)";
    private static final String SQL_TABLE_CREATE = "CREATE TABLE EMPLOYEE"

            + "("

            + " ID serial,"

            + " NAME varchar(100) NOT NULL,"

            + " SALARY numeric(15, 2) NOT NULL,"

            + " CREATED_DATE timestamp,"

            + " PRIMARY KEY (ID)"

            + ")";
    private static final String SQL_TABLE_DROP = "DROP TABLE IF EXISTS EMPLOYEE";
    private static final String SQL_INSERT = "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)";

    private static final String SQL_UPDATE = "UPDATE EMPLOYEE SET SALARY=? WHERE NAME=?";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            System.out.println("thanh cong");
        } catch (SQLException e) {
            System.out.println("khong thANH CONg");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("khong thNH cong");
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertUser(String name, String email, String country) throws SQLException {
        System.out.println(INSERT_USER_SQL);
        try (
                Connection connection = getConnection();
                PreparedStatement statement = (PreparedStatement) connection.prepareStatement(INSERT_USER_SQL)
        ) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, country);
            System.out.println(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User selectUser(int id) throws SQLException {
        User user = null;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = (PreparedStatement) connection.prepareStatement(SELECT_USER_BY_ID)
        ) {
            statement.setInt(1, id);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
//             PreparedStatement statement = (PreparedStatement) connection.prepareStatement(SELECT_ALL_USER);

             CallableStatement statement = connection.prepareCall(selectAll);

        ) {
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
//             PreparedStatement statement = (PreparedStatement) connection.prepareStatement(DELETE_USER_SQL);
             CallableStatement statement = connection.prepareCall(DELETE_USER_PRO);
        ) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate;
        try (Connection connection = getConnection();
//             PreparedStatement statement = (PreparedStatement) connection.prepareStatement(UPDATE_USER_SQL);
             CallableStatement statement = connection.prepareCall(UPDATE_USER_PRO);
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());
            rowUpdate = statement.executeUpdate() > 0;
        }
        return rowUpdate;
    }

    @Override
    public List<User> findByCountry(String country) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = (PreparedStatement) connection.prepareStatement(SEARCH_BY_COUNTRY);
        ) {
            statement.setString(1, "%" + country + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String countr = resultSet.getString("country");
                users.add(new User(id, name, email, countr));
            }
        }
        return users;
    }

    @Override
    public List<User> sort() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = (PreparedStatement) connection.prepareStatement(SORT_BY_NAME);
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                users.add(new User(id, name, email, country));
            }
        }
        return users;

    }

    @Override
    public User get_user_by_id(int user_id) throws SQLException {
        User user = null;
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(GET_USER_BY_ID);

        statement.setInt(1, user_id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String country = resultSet.getString("country");
            user = new User(user_id, name, email, country);
        }


        return user;
    }

    @Override
    public void insert_user_store(String name, String email, String country) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(INSERT_USER);
        statement.setString(1, name);
        statement.setString(2, email);
        statement.setString(3, country);

        statement.executeUpdate();
    }

    @Override
    public void insert_update_without_tran() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        PreparedStatement psInsert = (PreparedStatement) connection.prepareStatement(SQL_INSERT);
        PreparedStatement psUpdate = (PreparedStatement) connection.prepareStatement(SQL_UPDATE);

        statement.execute(SQL_TABLE_DROP);
        statement.execute(SQL_TABLE_CREATE);

        psInsert.setString(1, "Tuấn");
        psInsert.setBigDecimal(2, new BigDecimal(10));
        psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        psInsert.execute();
        psInsert.setString(1, "Trump");
        psInsert.setBigDecimal(2, new BigDecimal(9));
        psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        psInsert.execute();

        psUpdate.setString(2, "Tuấn");
        psUpdate.execute();


    }

    @Override
    public void insert_update_with_tran() throws SQLException {
        try (Connection conn = getConnection();

             Statement statement = conn.createStatement();

             PreparedStatement psInsert = (PreparedStatement) conn.prepareStatement(SQL_INSERT);

             PreparedStatement psUpdate = (PreparedStatement) conn.prepareStatement(SQL_UPDATE)) {

            statement.execute(SQL_TABLE_DROP);

            statement.execute(SQL_TABLE_CREATE);


            conn.setAutoCommit(false);


            psInsert.setString(1, "Quynh");

            psInsert.setBigDecimal(2, new BigDecimal(10));

            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            psInsert.execute();


            psInsert.setString(1, "Ngan");

            psInsert.setBigDecimal(2, new BigDecimal(20));

            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            psInsert.execute();
            psUpdate.setBigDecimal(1, new BigDecimal(999.99));
            psUpdate.setString(2, "Quynh");

            psUpdate.execute();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addUserTransaction(User user, int[] permissions) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement stAssignment = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = (PreparedStatement) connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            int rowUpdated = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            int user_id = 0;
            if (resultSet.next()) {
                user_id = resultSet.getInt(1);
            }
            if (rowUpdated == 1) {
                String sqlPivot = "insert into user_permission(permission_id,user_id)" + "value(?,?)";
                stAssignment = (PreparedStatement) connection.prepareStatement(sqlPivot);
                for (int permissionId : permissions) {
                    stAssignment.setInt(1, user_id);
                    stAssignment.setInt(2, permissionId);
                    stAssignment.executeUpdate();
                }
                connection.commit();
            }else {
                connection.rollback();
            }
        }catch (SQLException e){
            try {
                if(connection!=null)
                    connection.rollback();
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }finally {
            try {
                if(resultSet!=null) resultSet.close();
                if(statement!=null) statement.close();
                if(stAssignment != null) stAssignment.close();
                if(connection!=null) connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        userDAO.getConnection();
    }

}
