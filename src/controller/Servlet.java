package controller;

import dao.impl.UserDAO;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Servlet", urlPatterns = "/users")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                try {
                    insertUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "edit":
                try {
                    updateUser(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findByCountry":
                try {
                    searchResult(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        response.setContentType("text/html;character=utf-8");
    }

    private void searchResult(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<User> users;
        String country = request.getParameter("country");
        users = userDAO.findByCountry(country);
        request.setAttribute("users",users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/listByCountry.jsp");
        dispatcher.forward(request,response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User user = new User(id,name,email,country);
        userDAO.updateUser(user);
        listUser(request,response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        userDAO.insert_user_store(name, email, country);
        listUser(request, response);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showAddForm(request, response);
                break;
            case "edit":
                try {
                    showEditForm(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                try {
                    deleteUser(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findByCountry":
                showFindForm(request,response);
                break;
            case "sortByName":
                try {
                    sortByName(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "info":
                try {
                    showInfo(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "test-without-tran":
                try {
                    test_without_tran(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "test-use-tran":

                try {
                    testUseTran(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void testUseTran(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        userDAO.insert_update_with_tran();
    }

    private void test_without_tran(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        userDAO.insert_update_without_tran();

    }

    private void showInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.get_user_by_id(id);
        request.setAttribute("user",user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/infoUser.jsp");
        dispatcher.forward(request,response);
    }

    private void sortByName(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<User> users = userDAO.sort();
        request.setAttribute("users",users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/sortByName.jsp");
        dispatcher.forward(request,response);
    }

    private void showFindForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/findCountryForm.jsp");
        dispatcher.forward(request,response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("users", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        User checkUser = userDAO.selectUser(id);
        request.setAttribute("user",checkUser);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/edit.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(request, response);
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<User> users = userDAO.selectAllUsers();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }
}
