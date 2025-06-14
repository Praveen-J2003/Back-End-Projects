package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.util.DBUtil;

import java.io.IOException;
import java.sql.*;

public class ProjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        JSONArray arr = new JSONArray();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS projects(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,description TEXT)");
             PreparedStatement query = conn.prepareStatement("SELECT * FROM projects");
             ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("title", rs.getString("title"));
                obj.put("description", rs.getString("description"));
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
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psCreate = conn.prepareStatement("CREATE TABLE IF NOT EXISTS projects(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,description TEXT)");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO projects(title, description) VALUES(?, ?)")) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        res.setContentType("application/json");
        res.getWriter().print(new JSONObject().put("message", "Project added").toString());
    }
}
