# AI求职助手

> 让求职更简单

## 项目简介

AI求职助手是一个帮助应届生/求职者实现一键求职的智能工具，提供职位聚合、简历解析、AI模拟面试等功能。

## 核心功能

- 🔍 **职位聚合** - 聚合Boss直聘、拉勾、猎聘、官网等职位信息
- 🎴 **翻牌子交互** - 卡片式职位展示，一键收藏/投递
- 📄 **简历解析** - PDF/Word/图片自动解析为结构化数据
- 📝 **官网一键填表** - 预先配置的表单字段，一键填充
- 🤖 **AI模拟面试** - 基于简历生成问题，SOP标准化流程

## 技术栈

- **后端**: Java (Spring Boot 3.2 + Spring Cloud)
- **前端**: Vue3 + Element Plus
- **数据库**: MySQL 8.0 + Redis
- **搜索引擎**: Elasticsearch
- **文件存储**: MinIO
- **AI**: OpenAI / Claude API

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0
- Redis 7.x
- Node.js 18+

### 本地启动

```bash
# 1. 克隆项目
git clone https://github.com/your-username/ai-job-helper.git
cd ai-job-helper

# 2. 配置数据库
# 修改 application.yml 中的数据库连接信息

# 3. 启动后端
cd backend
mvn spring-boot:run

# 4. 启动前端
cd frontend
npm install
npm run dev
```

## 项目结构

```
ai-job-helper/
├── docs/           # 项目文档
├── backend/        # 后端代码
├── frontend/       # 前端代码
├── docker/         # Docker配置
└── .github/        # GitHub Actions
```

## License

MIT
