package com.cmx.pms.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cmx.pms.model.User;
import com.cmx.pms.dao.UserDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     try {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserDao uDao = new UserDao();
        User user = uDao.findByUsername(username);
        if(user==null)
        {
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
        else if (!password.equals(user.getPassword())) {
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
        else{
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath()+"/projects");
        }
     } catch (Exception e) {
        throw new ServletException("登录失败"+e);
     }   
    }
    

}