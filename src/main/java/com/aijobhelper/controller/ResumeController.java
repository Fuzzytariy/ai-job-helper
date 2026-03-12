package com.aijobhelper.controller;

import com.aijobhelper.entity.Resume;
import com.aijobhelper.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 简历接口
 */
@RestController
@RequestMapping("/api/resume")
public class ResumeController {
    
    @Autowired
    private ResumeService resumeService;
    
    /**
     * 上传简历
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long userId) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取文件信息
            String originalFilename = file.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            
            // 生成唯一文件名
            String fileName = UUID.randomUUID().toString() + "." + fileType;
            
            // TODO: 保存文件到存储服务
            String fileUrl = "/uploads/resumes/" + fileName;
            
            // 创建简历记录
            Resume resume = new Resume();
            resume.setUserId(userId);
            resume.setFileName(originalFilename);
            resume.setFileUrl(fileUrl);
            resume.setFileType(fileType);
            resume.setParseStatus("pending");
            
            resume = resumeService.uploadResume(resume);
            
            result.put("success", true);
            result.put("data", resume);
            result.put("message", "简历上传成功");
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "简历上传失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 获取用户简历列表
     */
    @GetMapping("/list")
    public Map<String, Object> getUserResumes(@RequestParam Long userId) {
        List<Resume> resumes = resumeService.getUserResumes(userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", resumes);
        
        return result;
    }
    
    /**
     * 获取简历详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getResumeById(@PathVariable Long id) {
        Resume resume = resumeService.getResumeById(id);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", resume != null);
        result.put("data", resume);
        
        return result;
    }
    
    /**
     * 解析简历
     */
    @PostMapping("/parse/{id}")
    public Map<String, Object> parseResume(@PathVariable Long id) {
        Resume resume = resumeService.parseResume(id);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", resume != null);
        result.put("data", resume);
        
        return result;
    }
    
    /**
     * 计算匹配度
     */
    @GetMapping("/match")
    public Map<String, Object> calculateMatchScore(
            @RequestParam Long resumeId,
            @RequestParam Long jobId) {
        
        double score = resumeService.calculateMatchScore(resumeId, jobId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("score", score);
        
        return result;
    }
    
    /**
     * 删除简历
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteResume(@PathVariable Long id) {
        boolean success = resumeService.deleteResume(id);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        
        return result;
    }
}
