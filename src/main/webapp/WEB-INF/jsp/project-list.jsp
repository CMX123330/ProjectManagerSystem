<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                        <form id="addProjectForm" action="${pageContext.request.contextPath}/projects" method="post">
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

                    <div id="projectList">
                        <c:forEach items="${projects}" var="p">
                            <div class="drag-card" draggable="true" data-id="${p.id}">
                                <span class="drag-handle">⠿</span>
                                <div class="drag-card-main">
                                    <a href="${pageContext.request.contextPath}/projects?id=${p.id}">${p.name}</a>
                                    <span class="tag tag-doing">${p.status}</span>
                                    <span class="drag-card-meta">#${p.id} · 创建于 ${fn:substring(p.createdAt, 0, 16)}</span>
                                    <div class="drag-card-desc">${p.description}</div>
                                </div>
                                <div class="drag-card-actions">
                                    <a href="${pageContext.request.contextPath}/projects?action=edit&id=${p.id}">编辑</a>
                                    <c:choose>
                                        <c:when test="${p.taskCount > 0}">
                                            <form action="${pageContext.request.contextPath}/projects" method="post"
                                                style="display:inline"
                                                onsubmit="return confirm('该项目下有 ${p.taskCount} 个任务，删除将一并删除！确认？');">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="id" value="${p.id}">
                                                <button type="submit">删除</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="${pageContext.request.contextPath}/projects" method="post"
                                                style="display:inline" onsubmit="return confirm('确认删除该项目？');">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="id" value="${p.id}">
                                                <button type="submit">删除</button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <script>
                        var ctx = '${pageContext.request.contextPath}';
                        // 无刷新新建项目：拦截表单 → fetch → 新卡片插入列表末尾
                        document.getElementById('addProjectForm').addEventListener('submit', function (e) {
                            e.preventDefault();
                            var form = e.target;
                            var data = new FormData(form);
                            data.append('action', 'addAjax');
                            fetch(ctx + '/projects', { method: 'POST', body: data })
                                .then(function (resp) { return resp.text(); })
                                .then(function (html) {
                                    document.getElementById('projectList')
                                        .insertAdjacentHTML('beforeend', html);
                                    form.reset();
                                });
                        });
                        (function () {
                            var list = document.getElementById('projectList');
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
                                fetch(ctx + '/projects', {
                                    method: 'POST',
                                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                                    body: 'action=reorder&ids=' + encodeURIComponent(ids)
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
                    </script>
                </div>
            </body>

            </html>