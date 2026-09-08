package com.cmx.pms.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConn{
    public static void main(String[] args) {
        try(Connection conn = DBUtil.getConnection()){
            System.out.println("数据库连接成功："+conn);
        }catch(SQLException e){
            System.out.println("连接失败");
            e.printStackTrace();
        }
    }
}