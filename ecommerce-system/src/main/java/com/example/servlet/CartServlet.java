package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import com.example.util.DBUtil;
import java.io.IOException;
import java.sql.*;

public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS cart(id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,productId INTEGER,quantity INTEGER)");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO cart(userId,productId,quantity) VALUES(?,?,?)")) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        res.setContentType("application/json");
        res.getWriter().print(new JSONObject().put("message", "Item added to cart").toString());
    }
}
