package com.aijobhelper.controller;

import com.aijobhelper.entity.Job;
import com.aijobhelper.service.JobService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位接口
 */
@RestController
@RequestMapping("/api/job")
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    /**
     * 搜索职位
     */
    @GetMapping("/search")
    public Map<String, Object> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) Integer salaryMin,
            @RequestParam(required = false) Integer salaryMax,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        Page<Job> page = jobService.searchJobs(keyword, location, jobType, 
                salaryMin, salaryMax, pageNum, pageSize);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", page.getRecords());
        result.put("total", page.getTotal());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("pages", page.getPages());
        
        return result;
    }
    
    /**
     * 获取推荐职位
     */
    @GetMapping("/recommend")
    public Map<String, Object> recommend(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Job> jobs = jobService.getRecommendJobs(userId, limit);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", jobs);
        
        return result;
    }
    
    /**
     * 获取职位详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getJobById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", null); // TODO: 实现
        return result;
    }
}
