<%--
  Created by IntelliJ IDEA.
  User: zei
  Date: 4/28/24
  Time: 3:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>

<table>
    <tbody>
    <tr>
        <th>status_code</th>
        <td>${status_code}</td>
    </tr>
    <tr>
        <th>exception_type</th>
        <td>${exception_type}</td>
    </tr>
    <tr>
        <th>message</th>
        <td>${message}</td>
    </tr>
    <tr>
        <th>exception</th>
        <td>${exception}</td>
    </tr>
    <tr>
        <th>request_uri</th>
        <td>${request_uri}</td>
    </tr>
    </tbody>

</table>
</body>
</html>