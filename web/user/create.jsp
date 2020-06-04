<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/4/2020
  Time: 3:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create</title>
</head>
<body>
<h1 ALIGN="center"> Add new User</h1>
<fieldset>
    <legend>New User</legend>
    <form method="post">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
        </tr>
        <tr>
            <td><input type="text" name="name"></td>
            <td><input type="text" name="email"></td>
            <td><input type="text" name="country"></td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit" value="Save"></td>
        </tr>
    </table>
    </form>
</fieldset>
</body>
</html>
