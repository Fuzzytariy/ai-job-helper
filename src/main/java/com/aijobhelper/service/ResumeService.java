package com.aijobhelper.service;

import com.aijobhelper.entity.Resume;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aijobhelper.mapper.ResumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 简历服务
 */
@Service
public class ResumeService {
    
    @Autowired
    private ResumeMapper resumeMapper;
    
    /**
     * 上传简历
     */
    public Resume uploadResume(Resume resume) {
        resumeMapper.insert(resume);
        return resume;
    }
    
    /**
     * 获取用户简历列表
     */
    public List<Resume> getUserResumes(Long userId) {
        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("created_at");
        return resumeMapper.selectList(queryWrapper);
    }
    
    /**
     * 获取简历详情
     */
    public Resume getResumeById(Long id) {
        return resumeMapper.selectById(id);
    }
    
    /**
     * 解析简历
     * TODO: 集成OCR和LLM进行解析
     */
    public Resume parseResume(Long id) {
        Resume resume = resumeMapper.selectById(id);
        if (resume != null) {
            // TODO: 实现简历解析逻辑
            resume.setParseStatus("success");
            resumeMapper.updateById(resume);
        }
        return resume;
    }
    
    /**
     * 计算简历与职位的匹配度
     */
    public double calculateMatchScore(Long resumeId, Long jobId) {
        // TODO: 实现匹配度计算逻辑
        return 0.85;
    }
    
    /**
     * 删除简历
     */
    public boolean deleteResume(Long id) {
        return resumeMapper.deleteById(id) > 0;
    }
}
