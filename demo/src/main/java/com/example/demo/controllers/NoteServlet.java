package com.example.demo.controllers;

import com.example.demo.models.ErrorResponse;
import com.example.demo.models.Note;
import com.example.demo.models.NoteQueryModel;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "NoteServlet", value = "/NoteServlet")
@MultipartConfig
public class NoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json");
        Middleware.setCORS(request, response);
        try {
            int userId = Middleware.checkAuthentication(request, response);
            NoteQueryModel noteQueryModel = new NoteQueryModel();
            ArrayList<Note> notes = noteQueryModel.getNotes(userId);
            out.print(gson.toJson(notes));
        } catch (ErrorResponse e) {
            response.setStatus(e.getStatusCode());
            out.print(gson.toJson(e));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.toString(), 500);
            response.setStatus(500);
            out.print(gson.toJson(errorResponse));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json");
        Middleware.setCORS(request, response);
        try {
            int userId = Middleware.checkAuthentication(request, response);
//            int userId = 1;
            String content = request.getParameter("content");
            if (content == null) {
                ErrorResponse errorResponse = new ErrorResponse("Content field is required", 400);
                response.setStatus(errorResponse.getStatusCode());
                out.print(gson.toJson(errorResponse));
                return;
            }

            NoteQueryModel noteQueryModel = new NoteQueryModel();
            noteQueryModel.insertNote(content, userId);

            ArrayList<Note> notes = noteQueryModel.getNotes(userId);
            response.setStatus(201);
            out.print(gson.toJson(notes));
        } catch (ErrorResponse e) {
            response.setStatus(e.getStatusCode());
            out.print(gson.toJson(e));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.toString(), 500);
            response.setStatus(500);
            out.print(gson.toJson(errorResponse));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json");
        Middleware.setCORS(request, response);
        try {
            int userId = Middleware.checkAuthentication(request, response);
            String content = request.getParameter("content");
            String noteId = request.getParameter("noteId");
            if (content == null || noteId == null) {
                ErrorResponse errorResponse = new ErrorResponse("Content and noteId are required", 400);
                response.setStatus(errorResponse.getStatusCode());
                out.print(gson.toJson(errorResponse));
                return;
            }

            NoteQueryModel noteQueryModel = new NoteQueryModel();
            boolean validOwnership = noteQueryModel.checkNoteOwnership(Integer.parseInt(noteId), userId);
            if (!validOwnership) {
                ErrorResponse errorResponse = new ErrorResponse("Unauthorized", 401);
                response.setStatus(errorResponse.getStatusCode());
                out.print(gson.toJson(errorResponse));
                return;
            }

            noteQueryModel.updateNote(Integer.parseInt(noteId), content);
            ArrayList<Note> notes = noteQueryModel.getNotes(userId);
            out.print(gson.toJson(notes));
        } catch (ErrorResponse e) {
            response.setStatus(e.getStatusCode());
            out.print(gson.toJson(e));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.toString(), 500);
            response.setStatus(500);
            out.print(gson.toJson(errorResponse));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json");
        Middleware.setCORS(request, response);
        try {
            int userId = Middleware.checkAuthentication(request, response);
            String noteId = request.getParameter("noteId");
            if (noteId == null) {
                ErrorResponse errorResponse = new ErrorResponse("NoteId is required", 400);
                response.setStatus(errorResponse.getStatusCode());
                out.print(gson.toJson(errorResponse));
                return;
            }

            NoteQueryModel noteQueryModel = new NoteQueryModel();
            boolean validOwnership = noteQueryModel.checkNoteOwnership(Integer.parseInt(noteId), userId);
            if (!validOwnership) {
                ErrorResponse errorResponse = new ErrorResponse("Unauthorized", 401);
                response.setStatus(errorResponse.getStatusCode());
                out.print(gson.toJson(errorResponse));
                return;
            }

            noteQueryModel.deleteNote(Integer.parseInt(noteId));
            ArrayList<Note> notes = noteQueryModel.getNotes(userId);
            out.print(gson.toJson(notes));
        } catch (ErrorResponse e) {
            response.setStatus(e.getStatusCode());
            out.print(gson.toJson(e));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.toString(), 500);
            response.setStatus(500);
            out.print(gson.toJson(errorResponse));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Middleware.setCORS(req, resp);
        super.doOptions(req, resp);
    }
}
