package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import com.example.util.DBUtil;
import java.io.IOException;
import java.sql.*;

public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        double total = Double.parseDouble(req.getParameter("total"));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS orders(id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,total REAL,created TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO orders(userId,total) VALUES(?,?)")) {
            ps.setInt(1, userId);
            ps.setDouble(2, total);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        res.setContentType("application/json");
        res.getWriter().print(new JSONObject().put("message", "Order placed").toString());
    }
}
