package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.util.DBUtil;

import java.io.IOException;
import java.sql.*;

public class ContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        JSONArray arr = new JSONArray();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psCreate = conn.prepareStatement("CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,message TEXT)");
             PreparedStatement query = conn.prepareStatement("SELECT * FROM contacts");
             ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("name", rs.getString("name"));
                obj.put("email", rs.getString("email"));
                obj.put("message", rs.getString("message"));
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
        String email = req.getParameter("email");
        String message = req.getParameter("message");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psCreate = conn.prepareStatement("CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,message TEXT)");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO contacts(name,email,message) VALUES(?,?,?)")) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, message);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        res.setContentType("application/json");
        res.getWriter().print(new JSONObject().put("message", "Contact submitted").toString());
    }
}
