# AI求职助手 - 技术架构文档

## 一、项目概述

### 1.1 项目简介
**项目名称**：AI求职助手 (AI-Job-Helper)
**项目类型**：Web应用 + 后端服务
**技术栈**：Java (Spring Boot) + Vue3 + MySQL + Redis

### 1.2 核心功能
1. **职位聚合搜索** - 聚合Boss直聘、拉勾、猎聘、官网等职位信息
2. **翻牌子交互** - 卡片式职位展示，用户可收藏、跳转、筛选
3. **简历解析** - PDF/Word/图片简历自动解析为结构化数据
4. **官网一键填表** - 预先配置的表单字段，一键填充
5. **AI模拟面试** - 基于简历生成问题，SOP标准化流程

### 1.3 目标用户
- 应届生求职者
- 跳槽求职者
- 留学申请者

---

## 二、技术架构

### 2.1 系统架构图

```
┌─────────────────────────────────────────────────────────────┐
│                        前端层                                │
│                  Vue3 + Element Plus                        │
│         (Web端 / 小程序 / Chrome插件预留接口)                  │
└────────────────────────────┬────────────────────────────────┘
                             │ HTTP / WebSocket
┌────────────────────────────▼────────────────────────────────┐
│                       网关层                                 │
│                    Spring Cloud Gateway                      │
│              (鉴权 / 限流 / 路由 / 日志)                       │
└────────────────────────────┬────────────────────────────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
┌───────────────┐   ┌───────────────┐   ┌───────────────┐
│  用户服务      │   │  职位服务      │   │  简历服务      │
│  (user)       │   │  (job)        │   │  (resume)     │
│               │   │               │   │               │
│ - 用户管理    │   │ - 职位搜索    │   │ - 简历解析    │
│ - 认证鉴权    │   │ - 职位爬取    │   │ - 简历存储    │
│ - 收藏管理    │   │ - 职位推荐    │   │ - 简历匹配    │
└───────────────┘   └───────────────┘   └───────────────┘
        │                    │                    │
        └────────────────────┼────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────┐
│                      数据层                                  │
│   MySQL  +  Redis  +  Elasticsearch  +  MinIO(文件存储)     │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 技术选型

| 层级 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 后端框架 | Spring Boot | 3.2.x | Java主流框架 |
| 微服务 | Spring Cloud | 2023.x | 服务治理 |
| 数据库 | MySQL | 8.0 | 主数据库 |
| 缓存 | Redis | 7.x | 热点数据缓存 |
| 搜索引擎 | Elasticsearch | 8.x | 职位搜索 |
| 文件存储 | MinIO | latest | 简历文件存储 |
| 消息队列 | RabbitMQ | latest | 异步任务 |
| 爬虫框架 | Jsoup + Selenium | latest | 职位爬取 |
| AI模型 | OpenAI/Claude API | - | 简历解析/面试 |
| 容器化 | Docker | 24.x | 服务部署 |
| CI/CD | GitHub Actions | - | 持续集成 |

---

## 三、模块设计

### 3.1 用户模块 (user-service)

**主要功能**：
- 用户注册/登录 (JWT)
- 个人信息管理
- 收藏职位管理
- 投递记录追踪
- 会员权限管理

**数据库表**：
```sql
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'USER',
    vip_expire_time DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_collect (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_delivery (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    company_name VARCHAR(100),
    position VARCHAR(100),
    status VARCHAR(20), -- pending, delivered, viewed, interview, reject
    deliver_time DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 3.2 职位模块 (job-service)

**主要功能**：
- 职位搜索 (ES)
- 职位爬取 (Boss/拉勾/猎聘)
- 职位推荐 (基于简历匹配)
- 官网职位收录

**数据库表**：
```sql
CREATE TABLE job (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    source VARCHAR(20) NOT NULL, -- boss, lagou, liepin, official
    source_url VARCHAR(500),
    company_name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    location VARCHAR(50),
    salary_min INT,
    salary_max INT,
    experience VARCHAR(50),
    education VARCHAR(50),
    job_type VARCHAR(20), -- fulltime, internship
    description TEXT,
    requirements TEXT,
    benefits TEXT,
    hr_name VARCHAR(50),
    hr_title VARCHAR(50),
    published_at DATETIME,
    collected_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_company (company_name),
    INDEX idx_position (position),
    INDEX idx_location (location)
);

CREATE TABLE company_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(100) NOT NULL UNIQUE,
    industry VARCHAR(50),
    scale VARCHAR(20), -- small, medium, large, unicorn
    website VARCHAR(200),
    description TEXT,
    logo_url VARCHAR(500),
    official_career_url VARCHAR(500), -- 官网招聘页
    form_fields TEXT -- 官网表单字段配置(JSON)
);

CREATE TABLE job_recommend (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    match_score DECIMAL(5,2),
    reason VARCHAR(200),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 3.3 简历模块 (resume-service)

**主要功能**：
- 简历上传 (PDF/Word/图片)
- 简历解析 (OCR + LLM)
- 简历存储
- 简历匹配度分析
- 一键填表配置

**数据库表**：
```sql
CREATE TABLE resume (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    file_name VARCHAR(200),
    file_url VARCHAR(500),
    file_type VARCHAR(20), -- pdf, word, image
    parse_status VARCHAR(20), -- pending, processing, success, failed
    
    -- 解析后的结构化数据
    name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    location VARCHAR(50),
    
    education JSON, -- [{"school": "", "major": "", "degree": "", "duration": ""}]
    experience JSON, -- [{"company": "", "position": "", "duration": "", "description": ""}]
    project JSON, -- [{"name": "", "role": "", "description": ""}]
    skill JSON, -- ["Java", "Python", "MySQL"]
    certificate JSON,
    award JSON,
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 3.4 面试模块 (interview-service)

**主要功能**：
- AI模拟面试
- 面试问题生成 (基于简历 + 岗位)
- SOP流程控制
- 面试评估报告

**数据库表**：
```sql
CREATE TABLE interview_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    resume_id BIGINT,
    target_position VARCHAR(100),
    target_company VARCHAR(100),
    status VARCHAR(20), -- preparing, in_progress, completed
    current_step INT, -- 1-6 对应SOP步骤
    started_at DATETIME,
    completed_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE interview_question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    session_id BIGINT NOT NULL,
    step INT, -- 1-6
    question_type VARCHAR(20), -- self_intro, resume_deep, behavior, reverse
    question_text TEXT,
    user_answer TEXT,
    ai_feedback TEXT,
    score INT,
    answered_at DATETIME
);

CREATE TABLE question_bank (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(50), -- self_intro, team_work, problem_solve, pressure
    difficulty VARCHAR(20), -- easy, medium, hard
    question_text TEXT NOT NULL,
    ideal_answer TEXT,
    source VARCHAR(50), -- 牛客, 知乎, 原创
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

---

## 四、API设计

### 4.1 用户模块

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/user/register | POST | 用户注册 |
| /api/user/login | POST | 用户登录 |
| /api/user/info | GET | 获取用户信息 |
| /api/user/collect | GET | 获取收藏列表 |
| /api/user/collect/{jobId} | POST | 收藏职位 |
| /api/user/collect/{jobId} | DELETE | 取消收藏 |
| /api/user/delivery | GET | 获取投递记录 |

### 4.2 职位模块

| 接口 | 方法 |说明 |
|------|------|------|
| /api/job/search | GET | 搜索职位 |
| /api/job/recommend | GET | 推荐职位 |
| /api/job/collect | POST | 批量获取职位详情 |
| /api/job/company/{name} | GET | 获取公司信息 |

### 4.3 简历模块

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/resume/upload | POST | 上传简历 |
| /api/resume/parse/{id} | POST | 解析简历 |
| /api/resume/{id} | GET | 获取简历详情 |
| /api/resume/match/{jobId} | GET | 简历匹配度分析 |

### 4.4 面试模块

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/interview/start | POST | 开始面试 |
| /api/interview/{sessionId}/question | GET | 获取下一个问题 |
| /api/interview/{sessionId}/answer | POST | 提交回答 |
| /api/interview/{sessionId}/report | GET | 获取评估报告 |

---

## 五、核心流程

### 5.1 职位翻牌子流程

```
用户输入筛选条件
    ↓
调用 /api/job/search 获取职位列表
    ↓
前端渲染职位卡片 (翻牌子UI)
    ↓
用户操作:
    - 收藏 → 调用 /api/user/collect
    - 不感兴趣 → 滑过
    - 查看详情 → 跳转BOSS详情页
    - 一键填表 → 跳转官网填表页
```

### 5.2 简历解析流程

```
用户上传简历文件
    ↓
文件存储到MinIO
    ↓
异步任务: OCR提取文本 + LLM结构化
    ↓
存储结构化数据到数据库
    ↓
返回解析结果给前端
```

### 5.3 AI面试流程

```
用户选择目标岗位 + 简历
    ↓
生成面试SOP计划
    ↓
环节1: 自我介绍 (2分钟)
    ↓
环节2: 深挖简历 (15分钟)
    ↓
环节3: 岗位匹配 (15分钟)
    ↓
环节4: 行为面试 (15分钟)
    ↓
环节5: 反问环节 (3分钟)
    ↓
生成评估报告
```

---

## 六、安全设计

### 6.1 认证授权
- JWT token认证
- Token有效期配置
- 敏感接口需要登录验证

### 6.2 数据安全
- 简历文件加密存储
- 用户隐私数据脱敏
- 定期备份机制

### 6.3 爬虫合规
- 遵循Robots协议
- 请求频率限制
- 不做自动化投递，只做信息聚合

---

## 七、部署架构

### 7.1 开发环境
```
本机: Java 17 + IDEA + Maven
数据库: Docker MySQL 8.0
缓存: Docker Redis 7.x
搜索: Docker ES 8.x
```

### 7.2 生产环境 (Docker Compose)
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    ports:
      - "3306:3306"
  
  redis:
    image: redis:7
    ports:
      - "6379:6379"
  
  elasticsearch:
    image: elasticsearch:8.x
    ports:
      - "9200:9200"
  
  minio:
    image: minio/minio
    ports:
      - "9000:9000"
  
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysql
      - redis
      - elasticsearch
      - minio
```

---

## 八、开发计划

### Phase 1: 基础框架 (1周)
- [ ] 项目初始化 (Spring Boot多模块)
- [ ] 用户模块CRUD
- [ ] 基础配置 (MySQL/Redis/ES)

### Phase 2: 职位核心 (2周)
- [ ] 职位搜索功能
- [ ] 职位爬取 (Boss/拉勾)
- [ ] 翻牌子前端UI

### Phase 3: 简历模块 (2周)
- [ ] 文件上传下载
- [ ] 简历解析 (OCR+LLM)
- [ ] 简历匹配

### Phase 4: 面试模块 (2周)
- [ ] 面试SOP流程
- [ ] 题库管理
- [ ] 评估报告

### Phase 5: 优化上线 (1周)
- [ ] 性能优化
- [ ] 单元测试
- [ ] 部署文档

---

## 九、目录结构

```
ai-job-helper/
├── docs/                    # 项目文档
│   ├── architecture.md      # 本架构文档
│   ├── api.md               # API接口文档
│   ├── db.md                # 数据库设计文档
│   └── deploy.md            # 部署文档
│
├── backend/                 # 后端代码
│   ├── job-helper-bom/      # 依赖版本管理
│   ├── job-helper-common/   # 公共模块
│   ├── job-helper-user/     # 用户服务
│   ├── job-helper-job/      # 职位服务
│   ├── job-helper-resume/   # 简历服务
│   └── job-helper-interview/# 面试服务
│
├── frontend/                # 前端代码
│   ├── src/
│   │   ├── api/            # API请求
│   │   ├── components/     # 组件
│   │   ├── views/         # 页面
│   │   ├── router/        # 路由
│   │   └── store/         # 状态管理
│   └── package.json
│
├── docker/                 # Docker配置
│   ├── docker-compose.yml
│   └── Dockerfile
│
├── .github/                # GitHub Actions
│   └── workflows/
│
├── pom.xml                 # Maven配置
├── README.md               # 项目说明
└── .gitignore
```

---

## 十、注意事项

1. **爬虫风险**: 仅做信息聚合展示，不做自动化投递
2. **API成本**: AI调用需要控制并发和缓存
3. **数据隐私**: 简历数据加密存储，定期清理
4. **用户体验**: 翻牌子交互要流畅，加载要快

---

*文档版本: v1.0*
*创建时间: 2026-03-12*
