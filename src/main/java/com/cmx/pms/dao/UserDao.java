package com.cmx.pms.dao;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.cmx.pms.model.User;
import com.cmx.pms.util.DBUtil;
public class UserDao{
    
    private final QueryRunner runner = new QueryRunner(DBUtil.getDataSource());
    public User findByUsername(String username) throws SQLException
    {
        String sql = "SELECT id, username, password, created_at AS createdAt FROM user WHERE username = ?";
        return runner.query(sql, new BeanHandler<>(User.class),username);
    }
}