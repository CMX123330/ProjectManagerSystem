package com.cmx.pms.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cmx.pms.model.Attachment;
import com.cmx.pms.util.DBUtil;
public class AttachmentDao {

    private final QueryRunner runner = new QueryRunner(DBUtil.getDataSource());

    public List<Attachment> findByProjectId(int projectId) throws SQLException {
        String sql = "SELECT id, project_id AS projectId, filename, "
                + "original_name AS originalName, upload_time AS uploadTime "
                + "FROM attachment WHERE project_id = ? ORDER BY id DESC";
        return runner.query(sql, new BeanListHandler<>(Attachment.class), projectId);
    }
    public int add(Attachment a) throws SQLException{
        String sql = "INSERT INTO attachment(project_id, filename, original_name) VALUES(?, ?, ?)";
        return runner.update(sql,a.getProjectId(),a.getFileName(),a.getOriginalName());
    }
    public Attachment findById(int id) throws SQLException{
        String sql = "SELECT id,project_id AS projectId,filename,"+"original_name AS originalName,upload_time AS uploadTime "+"FROM attachment WHERE id=?";
        return runner.query(sql, new BeanHandler<>(Attachment.class),id);
    }
    public int deleteByprojectId(int projectId) throws SQLException
    {
        String sql = "delete from attachment where project_id=?";
        return runner.update(sql,projectId);
    }
}
