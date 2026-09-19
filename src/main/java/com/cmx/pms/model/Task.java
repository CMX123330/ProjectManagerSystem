package com.cmx.pms.model;

import java.time.LocalDateTime;
public class Task{
    private int id;
    private int projectId;
    private String title;
    private String status;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private int sortOrder;
    public Task()
    {
    }

    public Task(String title)
    {
        this.title = title;
        this.status = "待办";
    }
    public void setId(int Id)
    {
        this.id = Id;
    }
    public void setProjectId(int projectId)
    {
        this.projectId = projectId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setDueDate(LocalDateTime dueDate)
    {
        this.dueDate = dueDate;
    }
    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }
    public int getId()
    {
        return id;
    }
    public int getProjectId()
    {
        return projectId;
    }
    public String getTitle()
    {
        return title;
    }
    public String getStatus()
    {
        return status;
    }
    public LocalDateTime getDueDate()
    {
        return dueDate;
    }   
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
    public int getSortOrder()
    {
        return sortOrder;
    }
    public void setSortOrder(int sortOrder)
    {
        this.sortOrder = sortOrder;
    }
    @Override
    public String toString()
    {
        return "Task{"+
                "Id="+id+
                ", projectId="+projectId+
                ", title='"+title+'\''+
                ", status='"+status+'\''+
                ", dueDate="+dueDate+
                ", createdAt="+createdAt+
                '}';
    }   
}