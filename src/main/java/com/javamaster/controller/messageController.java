package com.javamaster.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/Message")
public class messageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher("WEB-INF/view/message.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");
        String Message = req.getParameter("Message");
        session.getAttribute("Id");
        String Id = String.valueOf(session.getAttribute("Id"));
        try {
            if (session.getAttribute("Id") == null){
                getAPI getAPI = new getAPI();
                String result = getAPI.sendMessage(Message, "null");
                session.setAttribute("Id", result);
            }
            else {
                getAPI getAPI = new getAPI();
                System.out.println("Id hiện tại tuyền vào " + Id);
                String result = getAPI.sendMessage(Message, (String) session.getAttribute("Id"));
                session.setAttribute("Id", result);
            }
            req.getRequestDispatcher("WEB-INF/view/message.jsp").forward(req, resp);
            System.out.println("Ok");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
