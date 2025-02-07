package listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import reports.ExtentReport;

public class MyListenerClass implements ITestListener {
	@Override
	public void onTestStart(ITestResult result) {
		ExtentReport.createExtentTest(result.getMethod().getMethodName());
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReport.extentTestPass(result.getMethod().getMethodName()+" Test is Passed Successfully");
	}
	@Override
	public void onTestFailure(ITestResult result) {
		ExtentReport.extentTestFail(result.getMethod().getMethodName()+ " Test is Fialed");
	}
	@Override
	public void onStart(ITestContext context) {
		ExtentReport.initReport();
	}
	@Override
	public void onFinish(ITestContext context) {
		ExtentReport.flushReport();
	}

}
