<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>项目列表 - 项目管理系统</title>
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
    <h1 class="page-title">我的项目</h1>

    <div class="card">
        <h2>新建项目</h2>
        <form action="${pageContext.request.contextPath}/projects" method="post">
            <div class="form-row">
                <label>项目名称</label>
                <input type="text" name="name" placeholder="给项目起个名字">
            </div>
            <div class="form-row">
                <label>项目描述</label>
                <input type="text" name="description" placeholder="一句话描述（可选）">
            </div>
            <button type="submit">创建项目</button>
        </form>
    </div>

    <table>
        <tr>
            <th>编号</th>
            <th>名称</th>
            <th>描述</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${projects}" var="p">
            <tr>
                <td>${p.id}</td>
                <td><a href="${pageContext.request.contextPath}/projects?id=${p.id}">${p.name}</a></td>
                <td>${p.description}</td>
                <td><span class="tag tag-doing">${p.status}</span></td>
                <td>${fn:substring(p.createdAt, 0, 16)}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/projects?action=edit&id=${p.id}">编辑</a>
                    <a class="link-danger" href="${pageContext.request.contextPath}/projects?action=delete&id=${p.id}" onclick="return confirm('确定要删除吗？')">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
