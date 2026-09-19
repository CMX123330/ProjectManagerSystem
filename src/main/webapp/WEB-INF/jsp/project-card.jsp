<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="drag-card" draggable="true" data-id="${project.id}">
    <span class="drag-handle">⠿</span>
    <div class="drag-card-main">
        <a href="${pageContext.request.contextPath}/projects?id=${project.id}">${project.name}</a>
        <span class="tag tag-doing">${project.status}</span>
        <span class="drag-card-meta">#${project.id} · 创建于 ${fn:substring(project.createdAt, 0, 16)}</span>
        <div class="drag-card-desc">${project.description}</div>
    </div>
    <div class="drag-card-actions">
        <a href="${pageContext.request.contextPath}/projects?action=edit&id=${project.id}">编辑</a>
        <c:choose>
            <c:when test="${project.taskCount > 0}">
                <form action="${pageContext.request.contextPath}/projects" method="post"
                    style="display:inline"
                    onsubmit="return confirm('该项目下有 ${project.taskCount} 个任务，删除将一并删除！确认？');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${project.id}">
                    <button type="submit">删除</button>
                </form>
            </c:when>
            <c:otherwise>
                <form action="${pageContext.request.contextPath}/projects" method="post"
                    style="display:inline" onsubmit="return confirm('确认删除该项目？');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${project.id}">
                    <button type="submit">删除</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>
