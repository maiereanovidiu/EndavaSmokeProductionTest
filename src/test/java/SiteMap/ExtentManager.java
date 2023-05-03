package SiteMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    public static void setExtent() {
        htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"\\reports\\"+"TestReport.html");
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("OrangeHRM Test Automation Report");
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

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