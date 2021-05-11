package com.example.demo.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Note {
    private int id;
    private String content;

    public Note(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Note(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.content = rs.getString("content");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
