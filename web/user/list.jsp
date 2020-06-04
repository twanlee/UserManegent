<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/4/2020
  Time: 10:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List users</title>
    <style>
        tr {
            color: white;
        }
    </style>
</head>
<body style="background-color:black;">
<h1 style="color: pink" align="center">Users Management</h1>
<h2 align="center" style="color: blueviolet">
    <a href="/users?action=create">Add New User</a>
</h2>
<div align="center">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
            <th colspan="2">Action</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.getId()}"></c:out></td>
                <td><c:out value="${user.getName()}"></c:out></td>
                <td><c:out value="${user.getEmail()}"></c:out></td>
                <td><c:out value="${user.getCountry()}"></c:out></td>
                <td><a href="/users?action=edit&id=${user.id}">Edit</a></td>
                <td><a href="/users?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
