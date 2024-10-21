package BS;

import CS3.DatabaseManager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class ContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            try {
                DatabaseManager.addContact(name, phone, address);
                response.getWriter().print("Contact added successfully");
            } catch (SQLException e) {
                response.getWriter().print("Error adding contact");
                e.printStackTrace();
            }
        } else if ("delete".equals(action)) {
            String name = request.getParameter("name");
            try {
                DatabaseManager.deleteContact(name);
                response.getWriter().print("Contact deleted successfully");
            } catch (SQLException e) {
                response.getWriter().print("Error deleting contact");
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("query".equals(action)) {
            String name = request.getParameter("name");
            try {
                DatabaseManager.queryContact(name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}