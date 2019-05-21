package com.api.core.reporting;

import com.api.core.utils.Request;
import com.api.core.utils.RequestConstants;
import com.api.core.utils.ResponseConstants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class ExtentTestNGITestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.createInstance("extent.html");
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal();
    ExtentTest parent, subChild;

    ITestContext context;

    @Override
    public synchronized void onStart(ITestContext context) {
        this.context = context;
        parent = extent.createTest(context.getName());
        parentTest.set(parent);

    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        ExtentTest child = parentTest.get().createNode(getTestName(result), result.getMethod().getDescription());
        test.set(child);
        appendTestInfoInReport(Status.PASS, result);
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        ExtentTest child = parentTest.get().createNode(getTestName(result), result.getMethod().getDescription());
        test.set(child);
        appendTestInfoInReport(Status.FAIL, result);
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    private synchronized String getTestName(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        return testName;

    }

    private synchronized void appendTestInfoInReport(Status testStatus, ITestResult iTestResult) {
        String requestUrl = null;
        Request request = (Request) iTestResult.getTestContext().getAttribute(RequestConstants.REQUEST);
        Response response = (Response)  iTestResult.getTestContext().getAttribute(ResponseConstants.RESPONSE);
        Long timer = (Long) iTestResult.getTestContext().getAttribute(ResponseConstants.RESPONSE_TIME);
        requestUrl = request.getBaseUrl() +request.getApiPath()+"/?";
        if (request.getQueryParameters() != null)
            requestUrl += request.getQueryParameters().toString();
        test.get().log(testStatus, requestUrl);
        test.get().log(Status.INFO,"Request Type : " + request.getRequestType().name());
        test.get().log(Status.INFO, "Response Time : " + String.valueOf(timer)+"ms");
        if(request.getRequestBody()!=null)
            test.get().log(Status.INFO,"Request Body: " + request.getRequestBody());
        if (testStatus.equals(Status.FAIL))
            test.get().log(testStatus, "Failure Reason : " + iTestResult.getThrowable().fillInStackTrace());
        test.get().log(testStatus, response.prettyPrint());
    }

}