package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String dbURL = "jdbc:mysql://10.4.53.32:3306/csc105_61130500223";

    public static Connection getMySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbURL, "mentor", "g,og9viNmu,");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
