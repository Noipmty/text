package BS;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "053252";

    public static void addContact(String name, String phone, String address) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO User (name, phone, address) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, address);
            pstmt.executeUpdate();
        }
    }

    public static void deleteContact(String name) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM User WHERE name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public static void queryContact(String name) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM User WHERE name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("查询成功: " + rs.getString("phone") + " " + rs.getString("address"));
            } else {
                System.out.println("未找到联系人");
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}