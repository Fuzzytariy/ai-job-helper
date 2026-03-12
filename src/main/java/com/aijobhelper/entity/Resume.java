package com.aijobhelper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 简历实体
 */
@Data
@TableName("resume")
public class Resume {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 文件名
     */
    private String fileName;
    
    /**
     * 文件URL
     */
    private String fileUrl;
    
    /**
     * 文件类型: pdf, word, image
     */
    private String fileType;
    
    /**
     * 解析状态: pending, processing, success, failed
     */
    private String parseStatus;
    
    // ========== 解析后的结构化数据 ==========
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 所在地
     */
    private String location;
    
    /**
     * 教育经历 (JSON)
     */
    private String education;
    
    /**
     * 工作经历 (JSON)
     */
    private String experience;
    
    /**
     * 项目经历 (JSON)
     */
    private String project;
    
    /**
     * 技能标签 (JSON)
     */
    private String skills;
    
    /**
     * 证书 (JSON)
     */
    private String certificate;
    
    /**
     * 奖项荣誉 (JSON)
     */
    private String award;
    
    /**
     * 简历摘要
     */
    private String summary;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
