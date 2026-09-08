<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>上传附件 - 项目管理系统</title>
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
    <h1 class="page-title">上传附件<span class="sub">到「${project.name}」</span></h1>
    <div class="card">
        <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="projectId" value="${project.id}">
            <div class="form-row">
                <label>选择文件</label>
                <input type="file" name="file">
            </div>
            <button type="submit">上传</button>
            <a class="link-muted" href="${pageContext.request.contextPath}/projects?id=${project.id}">← 返回详情</a>
        </form>
    </div>
</div>
</body>
</html>
