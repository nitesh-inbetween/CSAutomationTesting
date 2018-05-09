package org.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tag_identification_ids.TagIds;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.utility.Constants;
import org.utility.HandleAskBox;
import org.utility.Utility;

public class CreatePimAttribute
{
    private WebDriver                 driver;
    protected HashMap<String, String> parameters                 = new HashMap<>();
    protected HashMap<String, String> verificationValuesText     = new HashMap<>();
    protected HashMap<String, String> verificationValuesDropdown = new HashMap<>();
    private String                    mainWindow;

    @Test
    public void execute()
    {
        try {
            WebDriverWait waitForReload = new WebDriverWait(driver, Constants.waitTimeForRefresh, 1000);
            driver.switchTo().window(mainWindow);
            performUsecase(waitForReload, false);
            performUsecase(waitForReload, true);
            waitForReload
                    .until(ExpectedConditions.visibilityOfElementLocated(By.linkText(parameters.get("attributeName"))));
            WebElement createdAttribute = driver.findElement(By.linkText(parameters.get("attributeName")));
            createdAttribute.click();
            verifyAttributeConfiguration(waitForReload);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail("Error Occured !!");
        }

    }

    private void performUsecase(WebDriverWait waitForReload, boolean clickOkayButton) throws InterruptedException
    {
        Utility.traverseToPimSetting(waitForReload, driver);
        waitForReload.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(parameters.get("folderName"))));
        WebElement createdFolder = driver.findElement(By.linkText(parameters.get("folderName")));
        Utility.performRightClick(createdFolder, driver);
        Utility.selectRightClickOption(waitForReload, "Create new", driver);
        WebElement folderOption = driver.findElement(By.name("Create new"));
        folderOption.click();
        Thread.sleep(3000);
        mainWindow = Utility.askBoxWindowOperation(parameters.get("attributeName"), clickOkayButton, driver);
        driver.switchTo().window(mainWindow);
        Utility.traverseToPimSetting(waitForReload, driver);
        waitForReload.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(parameters.get("folderName"))));
        createdFolder = driver.findElement(By.linkText(parameters.get("folderName")));
        createdFolder.click();
        Thread.sleep(3000);
        List<WebElement> Attribute = driver.findElements(By.linkText(parameters.get("attributeName")));

        Assert.assertEquals(Attribute.isEmpty(), !clickOkayButton);
    }

    private void verifyAttributeConfiguration(WebDriverWait waitForReload)
    {
        Utility.traverseToPimStudio(waitForReload, driver);
        driver.switchTo().frame("main");
        waitForReload.until(ExpectedConditions.visibilityOfElementLocated(TagIds.pimAttributeType));
        verifyTextConfigurations();
        verifyDropdownConfiguration();
    }

    private void verifyDropdownConfiguration()
    {
        String elementval;
        Select dropdown;
        for (Map.Entry<String, String> verifyValue : verificationValuesDropdown.entrySet()) {
            dropdown = new Select(driver.findElement(By.id(verifyValue.getKey())));
            elementval = dropdown.getFirstSelectedOption().getText();
            if (!elementval.equals(verifyValue.getValue())) {
                Assert.fail("Created Attibute's " + verifyValue.getKey() + " verfication values didn't matched");
            }
        }
    }

    private void verifyTextConfigurations()
    {
        String elementval;
        WebElement element;
        for (Map.Entry<String, String> verifyValue : verificationValuesText.entrySet()) {
            element = driver.findElement(By.id(verifyValue.getKey()));
            elementval = element.getAttribute("value");
            if (!elementval.equals(verifyValue.getValue())) {
                Assert.fail("Created Attibute's " + verifyValue.getKey() + " verfication values didn't matched");
            }
        }
    }

    @BeforeMethod
    @Parameters({ "testData", "execute", "verificationTextData", "verificationDropdownData" })
    public void beforeMethod(String testData, String execute, String verificationTextData,
            String verificationDropdownData, ITestContext context)
    {
        Utility.checkExecutionStatus(execute, context);
        driver = (WebDriver) context.getSuite().getAttribute("driver");
        mainWindow = (String) context.getSuite().getAttribute("mainWindow");
        Utility.fillMapWithTestConfigurations(parameters, testData);
        Utility.fillMapWithTestConfigurations(verificationValuesText, verificationTextData);
        Utility.fillMapWithTestConfigurations(verificationValuesDropdown, verificationDropdownData);
    }
}
