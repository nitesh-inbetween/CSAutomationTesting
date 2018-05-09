package org.modules;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tag_identification_ids.TagIds;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.utility.Constants;
import org.utility.HandleAskBox;
import org.utility.Utility;

public class CreatePimAttributeFolder
{
    private WebDriver               driver;
    private HashMap<String, String> parameters = new HashMap<>();
    private String                  mainWindow;

    @Test
    public void execute()
    {
        try {
            WebDriverWait waitForReload = new WebDriverWait(driver, Constants.waitTimeForRefresh, 1000);
            waitForReload.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
            WebElement tabPIM = null;
            try {
                driver.switchTo().frame(0);
                waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.pimTab));
                tabPIM = driver.findElement(TagIds.pimTab);
            }
            catch (NoSuchFrameException e) {
                waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.pimTab));
                tabPIM = driver.findElement(TagIds.pimTab);
            }
            tabPIM.click();
            driver.navigate().refresh();
            performUsecase(waitForReload, false);
            performUsecase(waitForReload, true);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error Occured !!");
        }

    }

    private void performUsecase(WebDriverWait waitForReload, boolean clickOkayButton) throws InterruptedException
    {
        WebElement atributeContextTreeNode = traverseToPimSettingAttributeTree(waitForReload);
        Utility.performRightClick(atributeContextTreeNode, driver);
        Utility.selectRightClickOption(waitForReload, "New Folder", driver);
        WebElement folderOption = driver.findElement(By.name("New Folder"));
        folderOption.click();
        Thread.sleep(3000);
        mainWindow = Utility.askBoxWindowOperation(parameters.get("folderName"), clickOkayButton, driver);
        driver.switchTo().window(mainWindow);
        atributeContextTreeNode = traverseToPimSettingAttributeTree(waitForReload);
        atributeContextTreeNode.click();
        Thread.sleep(3000);
        List<WebElement> folders = driver.findElements(By.linkText(parameters.get("folderName")));
        Assert.assertEquals(folders.isEmpty(), !clickOkayButton);
    }

    private WebElement traverseToPimSettingAttributeTree(WebDriverWait waitForReload)
    {
        Utility.traverseToPimSetting(waitForReload, driver);
        waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.pimSettingNode));

        return driver.findElement(TagIds.pimSettingNode);
    }

    @BeforeMethod
    @Parameters({ "testData", "execute" })
    public void beforeMethod(String testData, String execute, ITestContext context)
    {
        Utility.checkExecutionStatus(execute, context);
        driver = (WebDriver) context.getSuite().getAttribute("driver");
        Utility.fillMapWithTestConfigurations(parameters, testData);
    }

    @AfterMethod
    public void afterMethod(ITestContext context)
    {
        ISuite suiteInstance = context.getSuite();
        suiteInstance.setAttribute("mainWindow", mainWindow);
    }

}
