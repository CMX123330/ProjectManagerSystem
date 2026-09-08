package com.cmx.pms.dao;

import java.util.List;

import com.cmx.pms.model.Project;
import com.cmx.pms.model.Task;
public class TestTaskDao{
    public static void main(String[] args) {
        ProjectDao pDao = new ProjectDao();
        TaskDao tDao = new TaskDao();
        try {
            Project pro = new  Project("项目1","这是项目1");
            pDao.add(pro);
            List<Project> list= pDao.findAll();
            int id = list.get(0).getId();
             Task task1 = new Task("task1");
             Task task2 = new Task("task2");
             //一对多
             task1.setProjectId(id);
             task2.setProjectId(id);
             tDao.add(task1);
             tDao.add(task2);
             List<Task> Listtask= tDao.findByProjectId(id);
             for(Task task : Listtask)
             {
                System.out.println(" "+task);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}