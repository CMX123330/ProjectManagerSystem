package  com.cmx.pms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cmx.pms.model.Attachment;
import com.cmx.pms.dao.AttachmentDao;
import com.cmx.pms.dao.ProjectDao;
import com.cmx.pms.dao.TaskDao;
import com.cmx.pms.model.Project;
import com.cmx.pms.model.Task;
@WebServlet("/projects")
public class ProjectServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String preid = req.getParameter("id");//获取项目id
            String preaction = req.getParameter("action");//获取项目行为
            if (preid==null) {
            ProjectDao projectDaoGet = new ProjectDao();
            List<Project> projects = projectDaoGet.findAll();
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("/WEB-INF/jsp/project-list.jsp").forward(req, resp);
            } else {
                if ("delete".equals(preaction)) {
                    ProjectDao pDao = new ProjectDao();
                    pDao.delete(Integer.parseInt(preid));//功能实现步骤
                    resp.sendRedirect(req.getContextPath()+"/projects");
                    return;
                } else if("edit".equals(preaction)){
                    Project p = new ProjectDao().findById(Integer.parseInt(preid));
                    req.setAttribute("project", p);
                    req.getRequestDispatcher("/WEB-INF/jsp/project-edit.jsp").forward(req, resp);
                    return;
                }
                else if("upload".equals(preaction))
                {
                    Project p = new ProjectDao().findById(Integer.parseInt(preid));
                    req.setAttribute("project", p);
                    req.getRequestDispatcher("/WEB-INF/jsp/upload.jsp").forward(req, resp);
                    return;

                }
                else{
                    TaskDao tDao = new TaskDao();
                    ProjectDao pDao = new ProjectDao();
                    List<Task> tasks = tDao.findByProjectId(Integer.parseInt(preid));
                    List<Attachment> attachments = new AttachmentDao().findByProjectId(Integer.parseInt(preid));
                    req.setAttribute("project", pDao.findById(Integer.parseInt(preid)));       // 打包①
                    req.setAttribute("tasks", tasks);         // 打包②
                    req.setAttribute("attachments", attachments);  // 打包③
                    req.getRequestDispatcher("/WEB-INF/jsp/project-detail.jsp").forward(req, resp);  // 交货
                    return;
                }
            }
        } catch (Exception e) {
            throw new ServletException("查询项目失败",e);   
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String preid = req.getParameter("id");
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            if(preid==null||preid.isEmpty()){
                ProjectDao pDao = new ProjectDao();
                Project p = new Project(name,description);
                pDao.add(p);
                resp.sendRedirect(req.getContextPath()+"/projects");
                return;
            }
            else{
                ProjectDao pDao = new ProjectDao();
                Project p = pDao.findById(Integer.parseInt(preid));
                p.setName(name);
                p.setDescription(description);
                pDao.update(p);
                resp.sendRedirect(req.getContextPath()+"/projects?id="+p.getId());
                return;
            }
        } catch (Exception e) {
            throw new ServletException("写入项目失败",e);
        }

        
    }
    
}