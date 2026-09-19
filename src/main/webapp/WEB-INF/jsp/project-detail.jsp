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
    <p style="margin-bottom: 16px;"><a class="link-muted" href="${pageContext.request.contextPath}/projects">← 返回列表</a></p>

    <div class="card">
        <h2>新建任务</h2>
        <form id="addTaskForm" action="${pageContext.request.contextPath}/tasks" method="post">
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
        <div id="taskList">
            <c:forEach items="${tasks}" var="t">
                <div class="drag-card" draggable="true" data-id="${t.id}">
                    <span class="drag-handle">⠿</span>
                    <div class="drag-card-main">
                        <span class="drag-card-title">${t.title}</span>
                        <span class="tag ${t.status == '完成' ? 'tag-done' : 'tag-doing'}">${t.status}</span>
                        <span class="drag-card-meta">截止 ${empty t.dueDate ? '-' : fn:substring(t.dueDate, 0, 10)} · 创建于 ${fn:substring(t.createdAt, 0, 16)}</span>
                    </div>
                    <div class="drag-card-actions">
                        <a class="done-link" href="${pageContext.request.contextPath}/tasks?action=done&id=${t.id}&projectId=${project.id}">完成</a>
                        <a class="link-danger" href="${pageContext.request.contextPath}/tasks?action=delete&id=${t.id}&projectId=${project.id}" onclick="return confirm('确认要删除吗？')">删除</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <script>
            var ctx = '${pageContext.request.contextPath}';
            document.getElementById("addTaskForm").addEventListener('submit',function(e){
                e.preventDefault();
                var form=e.target;
                var data =new FormData(form);
                data.append('action', 'addAjax');  
                fetch(ctx+'/tasks',{method:'post' , body:data}).then(function(resp){return resp.text();}).then(function(html){
                    document.getElementById('taskList')
                .insertAdjacentHTML('beforeend',html);
            form.reset();
        });
            });
            (function () {
                var list = document.getElementById('taskList');
                var dragEl = null;
                list.addEventListener('dragstart', function (e) {
                    if (!e.target.classList.contains('drag-card')) return;
                    dragEl = e.target;
                    dragEl.classList.add('dragging');
                    e.dataTransfer.effectAllowed = 'move';
                });
                list.addEventListener('dragend', function () {
                    if (dragEl) dragEl.classList.remove('dragging');
                    dragEl = null;
                });
                list.addEventListener('dragover', function (e) {
                    e.preventDefault();
                    var after = getAfterElement(list, e.clientY);
                    if (after == null) list.appendChild(dragEl);
                    else list.insertBefore(dragEl, after);
                });
                list.addEventListener('drop', function (e) {
                    e.preventDefault();
                    var ids = Array.prototype.map.call(list.querySelectorAll('.drag-card'), function (c) { return c.dataset.id; }).join(',');
                    fetch(ctx + '/tasks', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: 'action=reorder&projectId=${project.id}&ids=' + encodeURIComponent(ids)
                    });
                });
                function getAfterElement(container, y) {
                    var els = Array.prototype.filter.call(container.querySelectorAll('.drag-card'), function (c) { return c !== dragEl; });
                    var closest = { offset: -Infinity, element: null };
                    els.forEach(function (child) {
                        var box = child.getBoundingClientRect();
                        var offset = y - box.top - box.height / 2;
                        if (offset < 0 && offset > closest.offset) closest = { offset: offset, element: child };
                    });
                    return closest.element;
                }
            })();
            // 无刷新"完成"：事件委托，新插入的卡片同样生效
            (function () {
                var list = document.getElementById('taskList');
                list.addEventListener('click', function (e) {
                    var link = e.target.closest('.done-link');
                    if (!link) return;
                    e.preventDefault();
                    var card = link.closest('.drag-card');
                    var id = card.dataset.id;
                    fetch(ctx + '/tasks', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: 'action=doneAjax&id=' + id + '&projectId=${project.id}'
                    }).then(function (r) { return r.text(); }).then(function (txt) {
                        if (txt === 'ok') {
                            var tag = card.querySelector('.tag');
                            tag.textContent = '完成';
                            tag.className = 'tag tag-done';
                            link.remove();
                        }
                    });
                });
            })();
        </script>
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
