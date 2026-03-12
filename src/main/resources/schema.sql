-- 职位表
CREATE TABLE IF NOT EXISTS job (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(20),
    source_url VARCHAR(500),
    company_name VARCHAR(100),
    position VARCHAR(100),
    location VARCHAR(50),
    salary VARCHAR(50),
    salary_min INT,
    salary_max INT,
    experience VARCHAR(50),
    education VARCHAR(50),
    job_type VARCHAR(20),
    description TEXT,
    requirements TEXT,
    benefits TEXT,
    hr_name VARCHAR(50),
    hr_title VARCHAR(50),
    published_at TIMESTAMP,
    collected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户表
CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(128),
    email VARCHAR(100),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'USER',
    vip_expire_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 简历表
CREATE TABLE IF NOT EXISTS resume (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    file_name VARCHAR(200),
    file_url VARCHAR(500),
    file_type VARCHAR(20),
    parse_status VARCHAR(20) DEFAULT 'pending',
    name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    location VARCHAR(50),
    education TEXT,
    experience TEXT,
    project TEXT,
    skills TEXT,
    certificate TEXT,
    award TEXT,
    summary TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 插入一些测试数据
INSERT INTO job (source, company_name, position, location, salary, salary_min, salary_max, job_type, description, collected_at) VALUES
('boss', '阿里巴巴', 'Java开发工程师', '杭州', '20K-40K', 20000, 40000, 'fulltime', '负责后端开发', CURRENT_TIMESTAMP),
('boss', '腾讯', '后端开发工程师', '深圳', '25K-50K', 25000, 50000, 'fulltime', '负责服务器端开发', CURRENT_TIMESTAMP),
('boss', '字节跳动', 'Java工程师', '北京', '30K-60K', 30000, 60000, 'fulltime', '负责业务后端开发', CURRENT_TIMESTAMP),
('lagou', '美团', '高级Java开发', '北京', '25K-45K', 25000, 45000, 'fulltime', '负责平台开发', CURRENT_TIMESTAMP),
('liepin', '京东', 'Java技术专家', '北京', '35K-70K', 35000, 70000, 'fulltime', '负责技术架构', CURRENT_TIMESTAMP);
