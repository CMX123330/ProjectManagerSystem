package com.cmx.pms.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cmx.pms.model.Project;
import com.cmx.pms.util.DBUtil;

//提供各种工具
public class ProjectDao{
    //把连接池交给queryrunner,之后每次查询它自动借连接，用完自动还
    private final QueryRunner runner = new QueryRunner(DBUtil.getDataSource());
    public List<Project> findAll() throws SQLException{
        // 先添加的放上面（sort_order 小的在前，相同按 id 升序）
        String sql="SELECT id,name,description,status,created_at as createdAt,sort_order AS sortOrder,"
                + "(select count(*) from task t where t.project_id = p.id) AS taskCount "
                + "FROM project p ORDER BY sort_order ASC, id ASC";
        return runner.query(sql, new BeanListHandler<>(Project.class));
    }
    // 通过id找project
    public Project findById(int id) throws SQLException{
        String sql="SELECT id,name,description,status,created_at AS createdAt FROM project WHERE id = ?";
        return runner.query(sql, new BeanHandler<>(Project.class),id);
    }
    
    public int add(Project p) throws SQLException{
        // 新项目排到最后：取当前最大 sort_order + 1
        Number max = runner.query("SELECT COALESCE(MAX(sort_order),0)+1 FROM project", new ScalarHandler<>());
        String sql="INSERT INTO project(name,description,status,sort_order) VALUES(?,?,?,?)";
        return runner.update(sql,p.getName(),p.getDescription(),p.getStatus(),max.intValue());
    }
    public int update(Project p) throws SQLException{
        String sql= "UPDATE project SET name = ?,description=?,status=? WHERE id=?";
        return runner.update(sql,p.getName(),p.getDescription(),p.getStatus(),p.getId());
    }
    public int delete(int id) throws SQLException{
        String sql = "DELETE FROM project WHERE id=?";
        return runner.update(sql,id);
    }
    // 拖动排序后按新顺序写回 sort_order（i = 位置下标）
    public int updateSortOrder(int id, int sortOrder) throws SQLException{
        String sql = "UPDATE project SET sort_order = ? WHERE id = ?";
        return runner.update(sql, sortOrder, id);
    }
}
