package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import com.example.util.DBUtil;
import java.io.IOException;
import java.sql.*;

public class ApplicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int jobId = Integer.parseInt(req.getParameter("jobId"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String resume = req.getParameter("resume");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS applications(id INTEGER PRIMARY KEY AUTOINCREMENT,jobId INTEGER,name TEXT,email TEXT,resume TEXT,created TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO applications(jobId,name,email,resume) VALUES(?,?,?,?)")) {
            ps.setInt(1, jobId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, resume);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        res.setContentType("application/json");
        res.getWriter().print(new JSONObject().put("message", "Application submitted").toString());
    }
}
