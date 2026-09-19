package com.cmx.pms.model;
import java.time.LocalDateTime;
public class Project{
    private int id;                  //项目编号
    private String name;            //项目名称
    private String description;     //项目描述
    private String status;          //项目状态
    private LocalDateTime createdAt; //创建日期
    private int taskCount;
    private int sortOrder;

    public Project()
    {
    }
    //在数据库中，id和createdAt是自动生成的，所以在创建项目时不需要传入这两个参数
    public Project(String name,String description)
    {
        this.name = name;
        this.description = description;
        this.status = "进行中";
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public String getStatus()
    {
        return status;
    }
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
    @Override
    public String toString()
    {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}