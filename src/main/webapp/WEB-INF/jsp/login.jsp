<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - 项目管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-wrap">
    <div class="login-card">
        <h1>📋 项目管理系统</h1>
        <p class="login-sub">登录以管理你的项目</p>
        <c:if test="${not empty error}">
            <p class="error-msg">${error}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-row">
                <label>用户名</label>
                <input type="text" name="username" placeholder="admin">
            </div>
            <div class="form-row">
                <label>密码</label>
                <input type="password" name="password">
            </div>
            <button type="submit">登 录</button>
        </form>
    </div>
</div>
</body>
</html>
