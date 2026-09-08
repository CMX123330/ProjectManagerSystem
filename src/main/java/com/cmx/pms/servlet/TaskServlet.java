package com.cmx.pms.servlet;

import java.io.IOException;
import java.time.LocalDate;

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
            String title = req.getParameter("title");
            String preprojectid = req.getParameter("projectId");
            int projectid = Integer.parseInt(preprojectid);
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