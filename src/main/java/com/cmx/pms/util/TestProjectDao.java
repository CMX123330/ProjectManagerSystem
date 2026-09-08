package com.cmx.pms.util;

import java.util.List;

import com.cmx.pms.dao.ProjectDao;
import com.cmx.pms.model.Project;

public class TestProjectDao{
    public static void main(String[] args) {
        ProjectDao dao = new  ProjectDao();
        try {
            Project p = new Project("测试项目","这是第一个项目");
            int rows = dao.add(p);
            System.out.println("插入影响行数："+rows);

            List<Project> list = dao.findAll();
            System.out.println("共"+list.size()+"个项目");
            for(Project project:list)
            {
                System.out.println(" "+project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}