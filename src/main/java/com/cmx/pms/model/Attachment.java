package com.cmx.pms.model;
import java.time.LocalDateTime;
public class Attachment{
    private int id;
    private int projectId;
    private String fileName;
    private String originalName;
    private LocalDateTime uploadTime;

    public Attachment() {
    }

    public Attachment(String fileName, String originalName) {
        this.fileName = fileName;
        this.originalName = originalName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Attachment{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", fileName=").append(fileName);
        sb.append(", originalName=").append(originalName);
        sb.append(", uploadTime=").append(uploadTime);
        sb.append('}');
        return sb.toString();
    }


}