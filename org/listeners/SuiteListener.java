package org.listeners;

import org.listeners.adaptersclasses.SuiteAdapter;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;

public class SuiteListener extends SuiteAdapter
{
    WebDriver driver;
    @Override
    public void onFinish(ISuite suite)
    {
        driver = (WebDriver) suite.getAttribute("driver");
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.quit();
    }
}
