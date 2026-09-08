建表
-- 项目表
CREATE TABLE project (
    id INT PRIMARY KEY AUTO_INCREMENT,//项目id
    name VARCHAR(100) NOT NULL,//项目名称
    description TEXT,//描述信息
    status VARCHAR(20) DEFAULT '进行中',//进度
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
-- 任务表
CREATE TABLE task (
    id INT PRIMARY KEY AUTO_INCREMENT,
    project_id INT,
    title VARCHAR(200) NOT NULL,
    status VARCHAR(20) DEFAULT '待办',
    due_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES project(id)
);