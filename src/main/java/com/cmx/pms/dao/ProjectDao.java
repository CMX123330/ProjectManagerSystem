package com.cmx.pms.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cmx.pms.model.Project;
import com.cmx.pms.util.DBUtil;

//提供各种工具
public class ProjectDao{
    //把连接池交给queryrunner,之后每次查询它自动借连接，用完自动还
    private final QueryRunner runner = new QueryRunner(DBUtil.getDataSource());
    public List<Project> findAll() throws SQLException{
        String sql="SELECT id,name,description,status,created_at as createdAt FROM project ORDER BY id DESC";
        return runner.query(sql, new BeanListHandler<>(Project.class));
    }
    // 通过id找project
    public Project findById(int id) throws SQLException{
        String sql="SELECT id,name,description,status,created_at AS createdAt FROM project WHERE id = ?";
        return runner.query(sql, new BeanHandler<>(Project.class),id);
    }
    
    public int add(Project p) throws SQLException{
        String sql="INSERT INTO project(name,description,status) VALUES(?,?,?)";
        return runner.update(sql,p.getName(),p.getDescription(),p.getStatus());
    }
    public int update(Project p) throws SQLException{
        String sql= "UPDATE project SET name = ?,description=?,status=? WHERE id=?";
        return runner.update(sql,p.getName(),p.getDescription(),p.getStatus(),p.getId());
    }
    public int delete(int id) throws SQLException{
        String sql = "DELETE FROM project WHERE id=?";
        return runner.update(sql,id);
    }
}
