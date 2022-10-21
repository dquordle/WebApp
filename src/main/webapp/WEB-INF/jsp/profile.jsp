<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.school21.cinema.models.Authentication" %>
<%@ page import="edu.school21.cinema.models.Image" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% User user = (User) request.getSession().getAttribute("user"); %>
<% List <Authentication> auth = (List<Authentication>)  request.getSession().getAttribute("auth"); %>
<% List <Image> images = (List<Image>)  request.getSession().getAttribute("img"); %>
<% String avaPath = (String) request.getAttribute("avaPath"); %>
<html lang="en">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">

<head>
    <meta charset="UTF-8">
    <title>Profile</title>
</head>
<body>
<div class="container">
    <h2 id="User">
            <%=user.getFirstName() + " " + user.getLastName()%>
        <br>
            <%=user.getEmail()%>
    </h2>

    <img id="image"
         src= "${avaPath}" class="img-fluid"
         alt="Smth went wrong"
         width="400">

<%--        <div>--%>
            <table id="sessions-list">
                <thead>
                <tr bgcolor="#cfcfcf">
                    <th>Date</th>
                    <th>Time</th>
                    <th>IP</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="s" items="<%=auth%>">
                    <tr>
                        <td>${s.authDate}</td>
                        <td>${s.authTime}</td>
                        <td>${s.ip}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
<%--        </div>--%>
<%--    <div class=>--%>
        <table id="images-list">
            <thead>
            <tr bgcolor="#cfcfcf">
                <th>File name</th>
                <th>Size</th>
                <th>MIME</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="s" items="<%=images%>">
                <tr>
                    <td><a href="images/${s.id}" target="_blank"> ${s.name} </a></td>
                    <td>${s.readableSize}</td>
                    <td>${s.mime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
<%--    </div>--%>
    <p>
    <form method="post" action="images" enctype="multipart/form-data" id="upload">
        <label for="img">Select image:</label>
        <input type="file" id="img" name="img" accept="image/*" required="true">
        <input type="submit">
    </form>
    </p>
    <form action="signOut" method="get" id="sout">
        <input type="submit" value="Sign Out" />
    </form>

</div>
</body>
</html>

