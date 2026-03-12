/**
 * AI Job Helper - Test Class
 * Verify Java environment is configured correctly
 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  AI Job Helper - Java Environment Test");
        System.out.println("========================================");
        System.out.println();
        
        // Java version info
        System.out.println("[Java Version Info]");
        System.out.println("  Version: " + System.getProperty("java.version"));
        System.out.println("  Vendor: " + System.getProperty("java.vendor"));
        System.out.println("  Working Dir: " + System.getProperty("user.dir"));
        System.out.println();
        
        // OS info
        System.out.println("[OS Info]");
        System.out.println("  OS Name: " + System.getProperty("os.name"));
        System.out.println("  OS Version: " + System.getProperty("os.version"));
        System.out.println("  OS Arch: " + System.getProperty("os.arch"));
        System.out.println();
        
        System.out.println("========================================");
        System.out.println("  Environment OK! Ready to develop!");
        System.out.println("========================================");
    }
}
