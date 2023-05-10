package SiteMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    public static void setExtent() {
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\reports\\"+"TestReport.html");
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("OrangeHRM Test Automation Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("HostName", "Endava");
        extent.setSystemInfo("ProjectName", "Endava");
        extent.setSystemInfo("Tester", "Ovidiu");
        extent.setSystemInfo("OS", "Win10");
        extent.setSystemInfo("Browser", System.getProperty("browser"));
    }
    public static void endReport() {
        extent.flush();
    }
}