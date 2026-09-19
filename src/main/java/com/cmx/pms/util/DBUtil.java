package com.cmx.pms.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

public class DBUtil
{
    private  static  final DruidDataSource dataSource = new DruidDataSource();  //建立连接池对象
    static  {
        Properties props = new Properties();
try {
            props.load(DBUtil.class.getClassLoader().getResourceAsStream("config.properties"));//从class path读配置
    
} catch (Exception e) {
    throw new RuntimeException("读取 config.properties 失败",e);
}       dataSource.setUrl(props.getProperty("jdbc.url"));
        dataSource.setUsername(props.getProperty("jdbc.username"));
        dataSource.setPassword(props.getProperty("jdbc.password"));
    }
    public static Connection getConnection() throws SQLException
    {
        return  dataSource.getConnection();
    }
    public static DataSource getDataSource()
    {
        return dataSource;
    }
}
