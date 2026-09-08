package com.cmx.pms.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cmx.pms.model.Task;
import com.cmx.pms.util.DBUtil;

public class TaskDao{
    private final QueryRunner runner = new QueryRunner(DBUtil.getDataSource());
    public List<Task> findByProjectId(int projectId) throws SQLException{
        String sql = "SELECT id,project_id AS projectId,title,status,"+
        "due_date AS dueDate, created_at AS createdAt "+
        "FROM task WHERE project_id = ? ORDER BY id DESC";
        return runner.query(sql, new BeanListHandler<>(Task.class),projectId);
        
    }
    public Task findById(int id) throws SQLException{
        String sql = "SELECT id,project_id AS projectId,title,status,"+
        "due_date AS dueDate,created_at AS createdAt "+
        "FROM task WHERE id= ?";
        return runner.query(sql, new BeanHandler<>(Task.class),id);
    }
    public int add(Task t) throws SQLException{
        String sql ="INSERT INTO task(project_id,title,status,due_date) VALUES(?,?,?,?)";
        return runner.update(sql,t.getProjectId(),t.getTitle(),t.getStatus(),t.getDueDate());
    }
    public int update(Task t) throws SQLException{
        String sql = "UPDATE task SET title = ?, status = ?, due_date = ? WHERE id = ?";
        return runner.update(sql,t.getTitle(),t.getStatus(),t.getDueDate(),t.getId());
    }
    public int delete(int id) throws SQLException{
        String sql="DELETE FROM task WHERE id =?";
        return runner.update(sql,id);

    }
}