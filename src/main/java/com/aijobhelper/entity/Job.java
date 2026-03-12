package com.aijobhelper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 职位实体
 */
@Data
@TableName("job")
public class Job {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 来源: boss, lagou, liepin, official
     */
    private String source;
    
    /**
     * 来源URL
     */
    private String sourceUrl;
    
    /**
     * 公司名称
     */
    private String companyName;
    
    /**
     * 职位名称
     */
    private String position;
    
    /**
     * 工作地点
     */
    private String location;
    
    /**
     * 薪资范围
     */
    private String salary;
    
    /**
     * 最低薪资
     */
    private Integer salaryMin;
    
    /**
     * 最高薪资
     */
    private Integer salaryMax;
    
    /**
     * 经验要求
     */
    private String experience;
    
    /**
     * 学历要求
     */
    private String education;
    
    /**
     * 工作类型: fulltime, internship
     */
    private String jobType;
    
    /**
     * 职位描述
     */
    private String description;
    
    /**
     * 职位要求
     */
    private String requirements;
    
    /**
     * 福利待遇
     */
    private String benefits;
    
    /**
     * HR姓名
     */
    private String hrName;
    
    /**
     * HR职位
     */
    private String hrTitle;
    
    /**
     * 发布时间
     */
    private LocalDateTime publishedAt;
    
    /**
     * 采集时间
     */
    private LocalDateTime collectedAt;
}
