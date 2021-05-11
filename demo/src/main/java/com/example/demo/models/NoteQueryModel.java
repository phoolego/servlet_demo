package com.example.demo.models;

import com.example.demo.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoteQueryModel {
    Connection con;

    public void insertNote(String content, int userId) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO notes (content, user_id) VALUE (?, ?)");
            preparedStatement.setString(1, content);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
        } finally {
            con.close();
        }
    }

    public void updateNote(int noteId, String content) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE notes SET content = ? WHERE id = ?");
            preparedStatement.setString(1, content);
            preparedStatement.setInt(2, noteId);
            preparedStatement.execute();
        } finally {
            con.close();
        }
    }

    public void deleteNote(int noteId) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM notes WHERE id = ?");
            preparedStatement.setInt(1, noteId);
            preparedStatement.execute();
        } finally {
            con.close();
        }
    }

    public boolean checkNoteOwnership(int nodeId, int userId) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM notes WHERE id = ? AND  user_id = ?");
            preparedStatement.setInt(1, nodeId);
            preparedStatement.setInt(2, userId);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } finally {
            con.close();
        }
    }

    public ArrayList<Note> getNotes(int userId) throws SQLException {
        try {
            con = DBConnection.getMySQLConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM notes WHERE user_id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Note> notes = new ArrayList<Note>();
            while (rs.next()) {
                notes.add(new Note(rs));
            }
            return notes;
        } finally {
            con.close();
        }
    }
}
