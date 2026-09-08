package com.cmx.pms.model;

import java.time.LocalDateTime;

public class User{
    private int id;
    private String username;
    private String password;
    private LocalDateTime createdAt;

    public User()
    {

    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setCreatedAt(LocalDateTime CreatedAt)
    {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public String toString()
    {
        return "User{"+
        "Id="+id+
        ",username="+username+
        ",password="+password+
        ",createdAt="+createdAt+
        "}";
    }
}