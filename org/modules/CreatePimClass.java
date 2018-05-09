package org.modules;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tag_identification_ids.TagIds;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.utility.Constants;
import org.utility.Utility;

public class CreatePimClass
{
    private WebDriver               driver;
    private HashMap<String, String> parameters = new HashMap<>();
    private String                  mainWindow;

    @Test
    public void execute()
    {
        WebDriverWait waitForReload = new WebDriverWait(driver, Constants.waitTimeForRefresh, 1000);
        try {
            driver.switchTo().window(mainWindow);
            performUseCase(waitForReload, false);
            performUseCase(waitForReload, true);
            waitForReload.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(parameters.get("className"))));
            WebElement createdClass = driver.findElement(By.linkText(parameters.get("className")));
            createdClass.click();
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error Occured !!");
        }
    }

    private void performUseCase(WebDriverWait waitForReload, boolean clickOkayButton) throws InterruptedException
    {
        Utility.traverseToPimSetting(waitForReload, driver);
        waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.pimSettingClassNode));
        WebElement classNode = driver.findElement(TagIds.pimSettingClassNode);
        Utility.performRightClick(classNode, driver);
        Utility.selectRightClickOption(waitForReload, "Create new", driver);
        WebElement classOption = driver.findElement(By.name("Create new"));
        classOption.click();
        Thread.sleep(3000);
        mainWindow = Utility.askBoxWindowOperation(parameters.get("className"), clickOkayButton, driver);
        driver.switchTo().window(mainWindow);
        Utility.traverseToPimSetting(waitForReload, driver);
        waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.pimSettingClassNode));
        classNode = driver.findElement(TagIds.pimSettingClassNode);
        classNode.click();
        Thread.sleep(3000);
        List<WebElement> classes = driver.findElements(By.linkText(parameters.get("className")));

        Assert.assertEquals(classes.isEmpty(), !clickOkayButton);
    }

    @BeforeMethod
    @Parameters({"testData", "execute"})
    public void beforeMethod(String testData, String execute, ITestContext context)
    {
        Utility.checkExecutionStatus(execute, context);
        driver = (WebDriver) context.getSuite().getAttribute("driver");
        Utility.fillMapWithTestConfigurations(parameters, testData);
        mainWindow = (String) context.getSuite().getAttribute("mainWindow");
    }

    @AfterMethod
    public void afterMethod()
    {
    }

}
