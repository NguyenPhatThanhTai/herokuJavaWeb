package com.javamaster.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.json.JsonArray;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class getAPI {
    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String sendMessage(String message, String Id){
        String query_url = "https://dht-api.000webhostapp.com/APIDoAnJava/createMessage.php";
        String User = "Admin";
        String Admin = "User";
        String json = "{ \"name\" : \""+Id+"\", " +
                        "\"name1\" : \""+User+"\", " +
                        "\"name2\" : \""+Admin+"\", " +
                        "\"name3\" : \""+message+"\"}";
        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            BufferedReader br = null;
            if (100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String array1 = br.readLine();

            JSONObject json2 = new JSONObject(array1);

            System.out.println(json2.get("Check"));

            conn.disconnect();
            return (String) json2.get("Check");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
