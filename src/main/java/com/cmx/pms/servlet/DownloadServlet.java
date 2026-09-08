package com.cmx.pms.servlet;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmx.pms.model.Attachment;
import com.cmx.pms.dao.AttachmentDao;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String perid = req.getParameter("id");
            int id = Integer.parseInt(perid);
            Attachment a = new AttachmentDao().findById(id);
            if(a==null)
            {
                resp.sendRedirect(req.getContextPath()+"/projects");
                return;
            }
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(a.getOriginalName(),"UTF-8"));
            Files.copy(Paths.get("D:/pms_uploads/" + a.getFileName()), resp.getOutputStream());
        } catch (Exception e) {
            throw new ServletException("下载失败"+e);
        }
    }
    
}