package com.javamaster.controller;

import com.google.gson.Gson;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.net.*;
import java.lang.*;
import java.applet.*;
import java.awt.*;
import java.beans.*;
import java.math.*;
import java.time.*;
import java.security.*;
import java.text.*;
import java.rmi.*;
import java.sql.*;
import java.lang.*;

@WebServlet(urlPatterns = "/compiler")
public class complierController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data = req.getParameter("test");
        result result = new result("get", data);
        String json = new Gson().toJson(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
        resp.addHeader("Access-Control-Allow-Origin", "*");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try {
            System.out.println(jb.toString());
            JSONObject jsonObject =  new JSONObject(jb.toString());
            String someString = jsonObject.getString("code");
            System.out.println(someString);

            //complie code
            String phyPath = req.getSession().getServletContext().getRealPath("/");
            System.out.println(phyPath);

            complie complie = new complie();
            String resultComplie = complie.runComplie(someString, phyPath);

            result result = new result("post", resultComplie);
            String json = new Gson().toJson(result);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }
        // Work with the data using methods like...
        // int someInt = jsonObject.getInt("intParamName");
        // JSONObject nestedObj = jsonObject.getJSONObject("nestedObjName");
        // JSONArray arr = jsonObject.getJSONArray("arrayParamName");
        // etc...
    }
}
