<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/4/2020
  Time: 3:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<h1>Edit User</h1>
<fieldset>
    <legend>Edit information</legend>

    <c:if test="${user!=null}">
        <input type="hidden" name="id" value="<c:out value='${user.id}'/>">
    </c:if>

    <form method="post">
        <table border="1">
            <tr>
                <th>User Name</th>
                <td><input type="text" name="name" value="<c:out value="${user.name}"/>"></td>
            </tr>
            <tr>
                <th>User Email</th>
                <td><input type="text" name="email" value="<c:out value="${user.email}"/>"></td>
            </tr>
            <tr>
                <th>User Country</th>
                <td><input type="text" name="country" value="<c:out value="${user.country}"/>"></td>
            </tr>
            <tr>
                <td align="center" colspan="2"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form>
</fieldset>
</body>
</html>
