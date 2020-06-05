<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/4/2020
  Time: 5:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Show Country</title>
</head>
<body>
<h1>List by Country</h1>
<a href="/users">Back to List</a>
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
