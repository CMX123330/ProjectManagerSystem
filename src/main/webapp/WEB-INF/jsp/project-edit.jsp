<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑项目 - 项目管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header class="topbar">
    <div class="container">
        <span class="brand">📋 项目管理系统</span>
        <a href="${pageContext.request.contextPath}/logout">退出登录</a>
    </div>
</header>
<div class="container">
    <h1 class="page-title">编辑项目</h1>
    <div class="card">
        <form action="${pageContext.request.contextPath}/projects" method="post">
            <input type="hidden" name="id" value="${project.id}">
            <div class="form-row">
                <label>项目名称</label>
                <input type="text" name="name" value="${project.name}">
            </div>
            <div class="form-row">
                <label>项目描述</label>
                <input type="text" name="description" value="${project.description}">
            </div>
            <button type="submit">保存修改</button>
            <a class="link-muted" href="${pageContext.request.contextPath}/projects?id=${project.id}">取消</a>
        </form>
    </div>
</div>
</body>
</html>
