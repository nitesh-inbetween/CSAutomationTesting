package org.modules;

import java.util.HashMap;

import org.facade.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.utility.Utility;

public class LaunchApp
{
    protected HashMap<String, String> parameters = new HashMap<>();
    protected WebDriver               driver;
    protected DesiredCapabilities     browserSpec;

    @Test
    public void execute()
    {
        driver = DriverFactory.getDriver(parameters);
        driver.get(parameters.get("url"));
        Assert.assertEquals(driver.getTitle(), parameters.get("title"));
    }

    @BeforeMethod
    @Parameters({ "BrowserDetails", "execute" })
    public void beforeMethod(String configurations, String execute)
    {
        Utility.fillMapWithTestConfigurations(parameters, configurations);
    }

    @AfterMethod
    public void afterMethod(ITestContext context)
    {
        ISuite suiteInstance = context.getSuite();
        suiteInstance.setAttribute("driver", driver);
    }
}
