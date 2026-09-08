<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>项目详情 - 项目管理系统</title>
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
    <h1 class="page-title">${project.name}</h1>

    <div class="card">
        <h2>新建任务</h2>
        <form action="${pageContext.request.contextPath}/tasks" method="post">
            <input type="hidden" name="projectId" value="${project.id}">
            <div class="form-row">
                <label>任务标题</label>
                <input type="text" name="title" placeholder="接下来要做什么？">
            </div>
            <div class="form-row">
                <label>截止日期</label>
                <input type="date" name="dueDate">
            </div>
            <button type="submit">添加任务</button>
        </form>
    </div>

    <div class="card">
        <h2>任务列表</h2>
        <table>
            <tr>
                <th>标题</th>
                <th>状态</th>
                <th>截止日期</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${tasks}" var="t">
            <tr>
                <td>${t.title}</td>
                <td><span class="tag ${t.status == '完成' ? 'tag-done' : 'tag-doing'}">${t.status}</span></td>
                <td>${empty t.dueDate ? '-' : fn:substring(t.dueDate, 0, 10)}</td>
                <td>${fn:substring(t.createdAt, 0, 16)}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/tasks?action=done&id=${t.id}&projectId=${project.id}">完成</a>
                    <a class="link-danger" href="${pageContext.request.contextPath}/tasks?action=delete&id=${t.id}&projectId=${project.id}" onclick="return confirm('确认要删除吗？')">删除</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>

    <div class="card">
        <h2>附件</h2>
        <table>
            <tr>
                <th>文件名</th>
                <th>上传时间</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${attachments}" var="a">
                <tr>
                    <td>${a.originalName}</td>
                    <td>${fn:substring(a.uploadTime, 0, 16)}</td>
                    <td><a href="${pageContext.request.contextPath}/download?id=${a.id}">下载</a></td>
                </tr>
            </c:forEach>
        </table>
        <p style="margin-top: 14px;">
            <a href="${pageContext.request.contextPath}/projects?action=upload&id=${project.id}">＋ 上传附件</a>
            <a class="link-muted" href="${pageContext.request.contextPath}/projects">← 返回列表</a>
        </p>
    </div>
</div>
</body>
</html>
