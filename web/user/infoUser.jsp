<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/5/2020
  Time: 10:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Information</title>
</head>
<body>
<h1>Information</h1>
<a href="/users">Back to list</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Mail</th>
        <th>Country</th>
    </tr>
    <tr>
        <td>${requestScope["user"].getId()}</td>
        <td>${requestScope["user"].getName()}</td>
        <td>${requestScope["user"].getEmail()}</td>
        <td>${requestScope["user"].getCountry()}</td>


        <td><a href="/users?action=edit&id=${user.id}">Edit</a></td>
        <td><a href="/users?action=delete&id=${user.id}">Delete</a></td>
    </tr>
</table>
</body>
</html>
