package com.javamaster.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@WebServlet(urlPatterns = "/Tra-Cuu")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            req.getRequestDispatcher("WEB-INF/view/tra-cuu.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String Id = req.getParameter("Id");
        JSONObject json = null;
        String Repair_Id = "";
        String Customer_Name = "";
        String Laptop_Name = "";
        String Email = "";
        String Status = "";
        try {
            getAPI getAPI = new getAPI();

            json = getAPI.readJsonFromUrl("https://dht-api.000webhostapp.com/APIDoAnJava/get.php?Id=" + Id);

            JSONArray jsonArray = json.getJSONArray("token");

            System.out.println(jsonArray.get(0));

            String token = jsonArray.get(0).toString();
            String[] chunks = token.split("\\.");

            Base64.Decoder decoder = Base64.getDecoder();

            String header = new String(decoder.decode(chunks[0]));
            String payload = new String(decoder.decode(chunks[1]));

            String[] strArray = new String[] {payload};

            JSONObject json2 = new JSONObject(strArray[0]);

            System.out.println(json2);

            JSONArray jsonArray2 = json2.getJSONArray("data");

            System.out.println(jsonArray2.getJSONObject(0).getString("Repair_Id"));

            Customer_Name = jsonArray2.getJSONObject(0).getString("Customer_Name");
            Laptop_Name = jsonArray2.getJSONObject(0).getString("Laptop_Name");
            Repair_Id = jsonArray2.getJSONObject(0).getString("Repair_Id");
            Email = jsonArray2.getJSONObject(0).getString("Email");
            Status = jsonArray2.getJSONObject(0).getString("Status");

            req.setAttribute("Id",Id);

            req.setAttribute("Repair_Id", Repair_Id);
            req.setAttribute("Customer_Name", Customer_Name);
            req.setAttribute("Laptop_Name", Laptop_Name);
            req.setAttribute("Email", Email);
            req.setAttribute("Status", Status);

            req.getRequestDispatcher("WEB-INF/view/tra-cuu.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("Id",Id);
            req.setAttribute("Repair_Id", "Không tìm thấy");
            req.setAttribute("Customer_Name", "Không tìm thấy");
            req.setAttribute("Laptop_Name", "Không tìm thấy");
            req.setAttribute("Email", "Không tìm thấy");
            req.setAttribute("Status", "Không tìm thấy");
            try {
                req.getRequestDispatcher("WEB-INF/view/tra-cuu.jsp").forward(req, resp);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
//            e.printStackTrace();
        }
    }
}
