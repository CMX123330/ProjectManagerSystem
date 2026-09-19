<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="drag-card" draggable="true" data-id="${task.id}">
    <span class="drag-handle">⠿</span>
    <div class="drag-card-main">
        <span class="drag-card-title">${task.title}</span>
        <span class="tag ${task.status == '完成' ? 'tag-done' : 'tag-doing'}">${task.status}</span>
        <span class="drag-card-meta">截止 ${empty task.dueDate ? '-' : fn:substring(task.dueDate, 0, 10)} · 创建于 ${fn:substring(task.createdAt, 0, 16)}</span>
    </div>
    <div class="drag-card-actions">
        <a class="done-link" href="${pageContext.request.contextPath}/tasks?action=done&id=${task.id}&projectId=${task.projectId}">完成</a>
        <a class="link-danger" href="${pageContext.request.contextPath}/tasks?action=delete&id=${task.id}&projectId=${task.projectId}" onclick="return confirm('确认要删除吗？')">删除</a>
    </div>
</div>
