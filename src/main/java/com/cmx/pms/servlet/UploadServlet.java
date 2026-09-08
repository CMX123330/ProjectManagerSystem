package com.cmx.pms.servlet;

import java.io.IOException;

import javax.servlet.http.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmx.pms.dao.AttachmentDao;
import com.cmx.pms.model.Attachment;
@WebServlet("/upload") @MultipartConfig
public class UploadServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            req.setCharacterEncoding("UTF-8");
            String preprojectId = req.getParameter("projectId");
            int projectId = Integer.parseInt(preprojectId);
            Part part = req.getPart("file");
            String originalName = part.getSubmittedFileName();
            String filename = System.currentTimeMillis()+"_"+originalName;
            new File("D:/pms_uploads/").mkdirs();
            part.write("D:/pms_uploads/"+filename);
            Attachment a= new Attachment(filename,originalName);
            a.setProjectId(projectId);
            AttachmentDao aDao = new AttachmentDao();  
            aDao.add(a);  
            resp.sendRedirect(req.getContextPath()+"/projects?id="+projectId);
        } catch (Exception e) {
            throw new ServletException("存入文件失败"+e);
        }
        }
    
}