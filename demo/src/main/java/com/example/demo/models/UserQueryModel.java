package com.example.demo.models;

import com.example.demo.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQueryModel {
    Connection con;
    public UserQueryModel() {

    }

    public User insertNewUser(String username, String password) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users (username, password) VALUE (?, ?);");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            return this.getUser(username);
        } finally {
            con.close();
        }
    }

    public User getUser(String username) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs);
            }
            return user;
        } finally {
            con.close();
        }
    }

    public User getUser(int id) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs);
            }
            return user;
        } finally {
            con.close();
        }
    }

    public User signInUser(String username, String password) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs);
            }
            return user;
        } finally {
            con.close();
        }
    }
}
