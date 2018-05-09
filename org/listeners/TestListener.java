package org.listeners;

import org.listeners.adaptersclasses.TestAdapter;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TestListener extends TestAdapter
{
    String mtcid, description, name;

    @Override
    public void onTestSuccess(ITestResult result)
    {
        mtcid = result.getTestContext().getCurrentXmlTest().getParameter("mtcid");
        name = result.getTestContext().getName();
        System.out.println("Passed test name : " + name + ", id :" + mtcid);
    }

    @Override
    public void onTestFailure(ITestResult result)
    {
        mtcid = result.getTestContext().getCurrentXmlTest().getParameter("mtcid");
        name = result.getTestContext().getName();
        System.out.println("Failed test name : " + name + ", id :" + mtcid);
    }

    @Override
    public void onTestSkipped(ITestResult result)
    {
        mtcid = result.getTestContext().getCurrentXmlTest().getParameter("mtcid");
        name = result.getTestContext().getName();
        System.out.println("Skipped test name : " + name + ", id :" + mtcid);
    }

    @Override
    public void onFinish(ITestContext context)
    {
        mtcid = context.getCurrentXmlTest().getParameter("mtcid");
        description = context.getCurrentXmlTest().getParameter("description");
        System.out.println("Test Completed, description : " + description + ", id :" + mtcid);
        System.out.println("*******************************************************************");
    }
}
