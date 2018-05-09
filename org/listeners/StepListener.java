package org.listeners;

import org.listeners.adaptersclasses.HookableAdapter;
import org.testng.IHookCallBack;
import org.testng.ITestResult;

public class StepListener extends HookableAdapter 
{ 
@Override
public void run(IHookCallBack callBack, ITestResult testResult)
{
    super.run(callBack, testResult);
    callBack.runTestMethod(testResult);
}
}
