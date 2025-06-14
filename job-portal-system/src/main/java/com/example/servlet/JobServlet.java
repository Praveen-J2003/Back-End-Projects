package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.util.DBUtil;

import java.io.IOException;
import java.sql.*;

public class JobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        JSONArray arr = new JSONArray();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psCreate = conn.prepareStatement("CREATE TABLE IF NOT EXISTS jobs(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,company TEXT,location TEXT)");
             PreparedStatement query = conn.prepareStatement("SELECT * FROM jobs");
             ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("title", rs.getString("title"));
                obj.put("company", rs.getString("company"));
                obj.put("location", rs.getString("location"));
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
        String company = req.getParameter("company");
        String location = req.getParameter("location");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psCreate = conn.prepareStatement("CREATE TABLE IF NOT EXISTS jobs(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,company TEXT,location TEXT)");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO jobs(title,company,location) VALUES(?,?,?)")) {

            ps.setString(1, title);
            ps.setString(2, company);
            ps.setString(3, location);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        res.setContentType("application/json");
        res.getWriter().print(new JSONObject().put("message", "Job posted").toString());
    }
}
