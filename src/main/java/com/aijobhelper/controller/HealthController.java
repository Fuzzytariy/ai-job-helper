package com.aijobhelper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查接口
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("message", "AI求职助手服务运行中");
        result.put("version", "1.0.0");
        return result;
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "AI求职助手");
        result.put("description", "让求职更简单");
        result.put("endpoints", new String[]{
            "/api/health - 健康检查",
            "/api/job/search - 职位搜索",
            "/api/resume/upload - 简历上传",
            "/api/interview/start - 开始面试"
        });
        return result;
    }
}
