package com.cmx.pms.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmx.pms.dao.TaskDao;
import com.cmx.pms.model.Task;
@WebServlet("/tasks")
public class TaskServlet extends HttpServlet{
    

    @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String preid = req.getParameter("id");
            int id = Integer.parseInt(preid);
            String preprojectid = req.getParameter("projectId");
            int projectId = Integer.parseInt(preprojectid);
            String preaction = req.getParameter("action");
            TaskDao tDao = new TaskDao();
            if ("done".equals(preaction)) {
                Task task = tDao.findById(id);
                task.setStatus("完成");
                tDao.update(task);
            } else if("delete".equals(preaction)){
                tDao.delete(id);
            }
            resp.sendRedirect(req.getContextPath()+"/projects?id="+projectId);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String preaction = req.getParameter("action");
            String preprojectid = req.getParameter("projectId");
            int projectid = Integer.parseInt(preprojectid);
            if ("reorder".equals(preaction)) {
                // 任务拖动排序：按下标写回 sort_order
                String ids = req.getParameter("ids");
                if (ids != null && !ids.isEmpty()) {
                    TaskDao tDao = new TaskDao();
                    String[] arr = ids.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        tDao.updateSortOrder(Integer.parseInt(arr[i]), i);
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/projects?id=" + projectid);
                return;
            }
            if ("addAjax".equals(preaction)) {
                String title = req.getParameter("title");
                String predueDate = req.getParameter("dueDate");
                Task task = new Task(title);
                if (predueDate != null && !predueDate.isEmpty()) {
                    task.setDueDate(LocalDate.parse(predueDate).atStartOfDay());
                }
                task.setProjectId(projectid);
                TaskDao tDao = new TaskDao();
                tDao.add(task);
                // 新任务 sort_order 最大，永远排列表最后 → 最后一条就是刚插入的它
                List<Task> tasks = tDao.findByProjectId(projectid);
                req.setAttribute("task", tasks.get(tasks.size() - 1));
                // 不 redirect，改成 forward 到片段：响应体 = 一张卡片的 HTML
                req.getRequestDispatcher("/WEB-INF/jsp/task-card.jsp").forward(req, resp);
                return;
            }
            if ("doneAjax".equals(preaction)) {
                // 无刷新"完成"：改状态后只回一个 ok，页面由 JS 局部更新
                int id = Integer.parseInt(req.getParameter("id"));
                TaskDao tDao = new TaskDao();
                Task task = tDao.findById(id);
                task.setStatus("完成");
                tDao.update(task);
                resp.setContentType("text/plain;charset=UTF-8");
                resp.getWriter().write("ok");
                return;
            }

            String title = req.getParameter("title");
            String predueDate = req.getParameter("dueDate");
            Task task = new Task(title);
            if (predueDate != null && !predueDate.isEmpty()) {
                task.setDueDate(LocalDate.parse(predueDate).atStartOfDay());
            }
            TaskDao tDao = new TaskDao();
            task.setProjectId(projectid);
            tDao.add(task);
            resp.sendRedirect(req.getContextPath()+"/projects?id="+projectid);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
}