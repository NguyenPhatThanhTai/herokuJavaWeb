<%@ page import="com.javamaster.controller.getAPI" %>
<%@ page import="javax.json.JsonObject" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="10" >
    <title>Trợ Giúp</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        body {
            margin: 0 auto;
            max-width: 800px;
            padding: 0 20px;
        }

        .box{
            width: 900px;
            height: 500px;
            overflow: scroll;
        }

        .container {
            border: 2px solid #dedede;
            background-color: #f1f1f1;
            border-radius: 5px;
            padding: 10px;
            margin: 10px 0;
        }

        .darker {
            border-color: #ccc;
            background-color: #ddd;
        }

        .darker p{
            float: right;
        }

        .container::after {
            content: "";
            clear: both;
            display: table;
        }

        .container img {
            float: left;
            max-width: 60px;
            width: 100%;
            margin-right: 20px;
            border-radius: 50%;
        }

        @media only screen and (max-width: 768px) {
            .box{
                width: 500px;
            }
        }
    </style>
</head>
<body>
<h2>Khu vực trợ giúp (Beta)</h2>

<div class="box" id="message">
    <%
        System.out.println("Id bên jsp "+session.getAttribute("Id"));
            if (session.getAttribute("Id") != null){
                JSONObject json = null;
                getAPI getAPI = new getAPI();
                json = getAPI.readJsonFromUrl("https://dht-api.000webhostapp.com/APIDoAnJava/getMessage.php?Id="+session.getAttribute("Id"));
                JSONArray jsonArray = json.getJSONArray("Check");
                if (jsonArray != null){
                    for (int i = 0; i < jsonArray.length(); i ++){
                        if (jsonArray.getJSONObject(i).getString("msg_Staff").equals("Admin")){
                            out.print("    <div class=\"container darker\">\n" +
                                    "        <p>"+jsonArray.getJSONObject(i).getString("msg_Message")+"</p>\n" +
                                    "    </div>");
                        }else {
                            out.print("    <div class=\"container\">\n" +
                                    "        <p>"+jsonArray.getJSONObject(i).getString("msg_Message")+"</p>\n" +
                                    "    </div>");
                        }
                    }
                }
            }else {
        }
    %>

<%--    <div class="container darker">--%>
<%--        <p>Hey! I'm fine. Thanks for asking!</p>--%>
<%--    </div>--%>

<%--    <div class="container">--%>
<%--        <p>Hello. How are you today?</p>--%>
<%--    </div>--%>

<%--    <div class="container darker">--%>
<%--        <p>Hey! I'm fine. Thanks for asking!</p>--%>
<%--    </div>--%>

<%--    <div class="container">--%>
<%--        <p>Hello. How are you today?</p>--%>
<%--    </div>--%>

<%--    <div class="container darker">--%>
<%--        <p>Hey! I'm fine. Thanks for asking!</p>--%>
<%--    </div>--%>
</div>

<form action="Message" method="POST">
    <div class="form-group">
        <label for="pwd">Tin nhắn</label>
        <input type="text" class="form-control" id="pwd" name="Message">
    </div>
    <button type="submit" class="btn btn-primary">Gửi</button>
</form>

</body>
</html>