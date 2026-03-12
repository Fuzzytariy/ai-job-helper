package com.aijobhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AI求职助手 - 主启动类
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("========================================");
        System.out.println("  AI求职助手 启动成功!");
        System.out.println("  访问地址: http://localhost:8080");
        System.out.println("========================================");
    }
}
