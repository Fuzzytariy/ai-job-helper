package com.aijobhelper;

/**
 * AI Job Helper - Trae CN + JDK17 Test
 */
public class App {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  AI Job Helper - Trae CN + JDK17 Test");
        System.out.println("========================================");
        System.out.println();
        
        // JDK Info
        System.out.println("[JDK Config]");
        System.out.println("  Version: " + System.getProperty("java.version"));
        System.out.println("  Path: D:\\java-develop\\jdk");
        System.out.println("  Vendor: " + System.getProperty("java.vendor"));
        System.out.println();
        
        // System Info
        System.out.println("[System Info]");
        System.out.println("  OS: " + System.getProperty("os.name"));
        System.out.println("  OS Version: " + System.getProperty("os.version"));
        System.out.println("  Arch: " + System.getProperty("os.arch"));
        System.out.println();
        
        // Project Info
        System.out.println("[Project Info]");
        System.out.println("  Name: AI Job Helper");
        System.out.println("  Path: D:\\ai-job-helper");
        System.out.println("  IDE: Trae CN");
        System.out.println();
        
        System.out.println("========================================");
        System.out.println("  SUCCESS! Ready to develop!");
        System.out.println("========================================");
    }
}
