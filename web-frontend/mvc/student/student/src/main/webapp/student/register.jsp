<%--
  Created by IntelliJ IDEA.
  User: zei
  Date: 4/26/24
  Time: 1:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>학생 - 등록</title>
</head>
<body>
<form method="post" action="${action}">
    <table>
        <tr>
            <td><label for="id">아이디</label></td>
            <td><input type="text" id="id" name="id" value="${student.id}" ${action == '/student/update' ? 'readonly' : ''} required></td>
        </tr>
        <tr>
            <td><label for="name">이름</label></td>
            <td><input type="text" id="name" name="name" value="${student.name}" required></td>
        </tr>
        <tr>
            <td>성별</td>
            <td>
                <input type="radio" id="male" name="gender" value="M" ${student.gender == 'M' ? 'checked' : ''} ${action == '/student/update' ? 'disabled' : ''}>
                <label for="male">남성</label>
                <input type="radio" id="female" name="gender" value="F" ${student.gender == 'F' ? 'checked' : ''} ${action == '/student/update' ? 'disabled' : ''}>
                <label for="female">여성</label>
            </td>
        </tr>
        <tr>
            <td><label for="age">나이</label></td>
            <td><input type="number" id="age" name="age" value="${student.age}" required></td>
        </tr>
    </table>
    <input type="submit" value="등록">
</form>
</body>
</html>