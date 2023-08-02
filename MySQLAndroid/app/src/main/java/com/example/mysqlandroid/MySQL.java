package com.example.mysqlandroid;
import android.widget.Toast;

import java.sql.*;
import java.util.*;

public class MySQL {

    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://127.0.0.1:3306/test";
    static String USER = "root";
    static String PASS = "";
    static Connection conn;
    static Statement stmt;

    String query;

    static int rowsUpdated;


    void addUser(String name, String email){
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            query = "INSERT INTO users (name, email) VALUES ('" + name + "', '" + email + "')";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void deleteUser(String id){
        query = "DELETE FROM users WHERE id = " + id;
        try {
            rowsUpdated = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void updateUser(String id, String name, String email){
        query = "UPDATE users SET name = '" + name + "', email = '" + email + "' WHERE id = " + id;

        try {
            rowsUpdated = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    ResultSet get_A_User(String id){
        query = "SELECT * FROM users WHERE id = " + id;

        ResultSet rs;

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
