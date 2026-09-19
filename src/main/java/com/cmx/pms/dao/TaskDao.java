package com.cmx.pms.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cmx.pms.model.Task;
import com.cmx.pms.util.DBUtil;

public class TaskDao{
    private final QueryRunner runner = new QueryRunner(DBUtil.getDataSource());
    public List<Task> findByProjectId(int projectId) throws SQLException{
        // 先添加的放上面：sort_order 小的在前，相同按 id 升序
        String sql = "SELECT id,project_id AS projectId,title,status,"+
        "due_date AS dueDate, created_at AS createdAt, sort_order AS sortOrder "+
        "FROM task WHERE project_id = ? ORDER BY sort_order ASC, id ASC";
        return runner.query(sql, new BeanListHandler<>(Task.class),projectId);

    }
    public Task findById(int id) throws SQLException{
        String sql = "SELECT id,project_id AS projectId,title,status,"+
        "due_date AS dueDate,created_at AS createdAt "+
        "FROM task WHERE id= ?";
        return runner.query(sql, new BeanHandler<>(Task.class),id);
    }
    public int add(Task t) throws SQLException{
        // 新任务在该项目内排到最后
        Number max = runner.query("SELECT COALESCE(MAX(sort_order),0)+1 FROM task WHERE project_id = ?", new ScalarHandler<>(), t.getProjectId());
        String sql ="INSERT INTO task(project_id,title,status,due_date,sort_order) VALUES(?,?,?,?,?)";
        return runner.update(sql,t.getProjectId(),t.getTitle(),t.getStatus(),t.getDueDate(),max.intValue());
    }
    public int update(Task t) throws SQLException{
        String sql = "UPDATE task SET title = ?, status = ?, due_date = ? WHERE id = ?";
        return runner.update(sql,t.getTitle(),t.getStatus(),t.getDueDate(),t.getId());
    }
    public int delete(int id) throws SQLException{
        String sql="DELETE FROM task WHERE id =?";
        return runner.update(sql,id);

    }
    public int deleteByprojectId(int projectId) throws SQLException
    {
        String sql = "delete from task where project_id = ?";
        return runner.update(sql,projectId);
    }
    // 拖动排序后写回 sort_order
    public int updateSortOrder(int id, int sortOrder) throws SQLException
    {
        String sql = "UPDATE task SET sort_order = ? WHERE id = ?";
        return runner.update(sql, sortOrder, id);
    }
}