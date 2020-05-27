package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Test NG TestListener. This class listen to the test actions such as Start, Finish, Pass, Fail, etc
 * We can do certain things during each events. Powerful stuff!
 */
public class TestListener implements ITestListener {
    public void onTestSuccess(ITestResult result) {
        // Todo - Don't need in this assignment, but the idea maybe updating Test Result to CLOUD matrices, etc.
        // Todo - We can attach test result, screenshot, video, etc
    }

    public void onTestFailure(ITestResult result) {
        // Todo - Don't need in this assignment, but the idea maybe updating Test Result to CLOUD matrices, etc.
        // Todo - We can attach test result, screenshot, video, etc

        // Here we are writing call stacks to the test result, so we can check what the error is directly in the report.
        if (result.getThrowable() != null) {
            StringWriter stringWriter = new StringWriter();
            result.getThrowable().printStackTrace(new PrintWriter(stringWriter));
        }
    }

    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }
}
