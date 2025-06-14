package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.util.DBUtil;

import java.io.IOException;
import java.sql.*;

public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        JSONArray arr = new JSONArray();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psCreate = conn.prepareStatement("CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,price REAL)");
             PreparedStatement query = conn.prepareStatement("SELECT * FROM products");
             ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("name", rs.getString("name"));
                obj.put("price", rs.getDouble("price"));
                arr.put(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        res.setContentType("application/json");
        res.getWriter().print(arr.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psCreate = conn.prepareStatement("CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,price REAL)");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO products(name,price) VALUES(?,?)")) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        res.setContentType("application/json");
        res.getWriter().print(new JSONObject().put("message", "Product added").toString());
    }
}
