package com.aijobhelper.service;

import com.aijobhelper.entity.Job;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aijobhelper.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 职位服务
 */
@Service
public class JobService {
    
    @Autowired
    private JobMapper jobMapper;
    
    /**
     * 搜索职位
     */
    public Page<Job> searchJobs(String keyword, String location, String jobType, 
                                  Integer salaryMin, Integer salaryMax, int pageNum, int pageSize) {
        Page<Job> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(w -> w.like("company_name", keyword)
                    .or().like("position", keyword)
                    .or().like("description", keyword));
        }
        
        if (location != null && !location.isEmpty()) {
            queryWrapper.like("location", location);
        }
        
        if (jobType != null && !jobType.isEmpty()) {
            queryWrapper.eq("job_type", jobType);
        }
        
        if (salaryMin != null) {
            queryWrapper.ge("salary_max", salaryMin);
        }
        
        if (salaryMax != null) {
            queryWrapper.le("salary_min", salaryMax);
        }
        
        queryWrapper.orderByDesc("collected_at");
        
        return jobMapper.selectPage(page, queryWrapper);
    }
    
    /**
     * 获取推荐职位
     */
    public List<Job> getRecommendJobs(Long userId, int limit) {
        // TODO: 根据用户简历推荐职位
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("collected_at");
        queryWrapper.last("LIMIT " + limit);
        return jobMapper.selectList(queryWrapper);
    }
    
    /**
     * 添加职位
     */
    public boolean addJob(Job job) {
        return jobMapper.insert(job) > 0;
    }
    
    /**
     * 批量添加职位
     */
    public boolean batchAddJobs(List<Job> jobs) {
        for (Job job : jobs) {
            jobMapper.insert(job);
        }
        return true;
    }
}
