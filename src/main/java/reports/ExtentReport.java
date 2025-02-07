package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ExtentReport {
	private ExtentReport() {
	}

	static ExtentReports extentReports;
	static ExtentSparkReporter extentSparkReporter;
	static ExtentTest extentTest;

	public static void initReport() {
		extentReports = new ExtentReports();
		extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/index.html");
		extentSparkReporter.config().setTheme(Theme.DARK);
		extentSparkReporter.config().setDocumentTitle("Extent Report By MBM");
		extentSparkReporter.config().setReportName("MyReportname");
		extentReports.attachReporter(extentSparkReporter);

	}

	public static void createExtentTest(String testName) {
		extentTest = extentReports.createTest(testName);
	}
	
	public static void extentTestLog(String logMessage) {
		extentTest.info(logMessage);
	}
	
	public static void extentTestPass(String passMessage) {
		extentTest.pass(passMessage);
	}
	
	public static void extentTestFail(String failMessage) {
		extentTest.fail(failMessage);
	}

	public static void flushReport() {
		extentReports.flush();
	}

}
