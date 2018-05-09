package org.modules;

import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tag_identification_ids.TagIds;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.utility.Constants;
import org.utility.Utility;

public class LoginTest
{
    protected HashMap<String, String> parameters = new HashMap<>();
    protected WebDriver               driver;

    @Test
    public void execute()
    {
        try {
            WebDriverWait waitForReload = new WebDriverWait(driver, Constants.waitTimeForRefresh);
            waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.loginLanguageSelectionBox));
            Select language = new Select(driver.findElement(TagIds.loginLanguageSelectionBox));
            language.selectByVisibleText(parameters.get("language"));
            WebElement loginButton = driver.findElement(TagIds.loginButton);
            WebElement username = driver.findElement(TagIds.portalUsernameField);
            WebElement password = driver.findElement(TagIds.portalPasswordField);
            username.sendKeys(parameters.get("username"));
            password.sendKeys(parameters.get("password"));
            loginButton.submit();
            try {
                ExpectedCondition<Alert> alertPresence = ExpectedConditions.alertIsPresent();
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Login Error : " + alertText);
                alert.accept();
                driver.close();
                Assert.fail();
            }
            catch (NoAlertPresentException e) {
                waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.loginButton));
                loginButton.submit();
            }

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail("Error Occured !!");
        }

    }

    @BeforeMethod
    @Parameters({ "credentails", "portal", "execute" })
    public void beforeMethod(String credentails, String portal, String execute, ITestContext context)
    {
        Utility.checkExecutionStatus(execute, context);
        driver = (WebDriver) context.getSuite().getAttribute("driver");
        Utility.fillMapWithTestConfigurations(parameters, credentails);
        Utility.fillMapWithTestConfigurations(parameters, portal);
    }



}
