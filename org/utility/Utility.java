package org.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tag_identification_ids.TagIds;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;

public class Utility
{
    public static HashMap<String, String> fillMapWithTestConfigurations(HashMap<String, String> map,
            String configurations)
    {
        String[] configPair, configValues = null;
        configValues = configurations.split(",");
        for (int index = 0; index < configValues.length; index++) {
            configPair = configValues[index].split("=");
            configPair[1] = configPair[1].trim();
            configPair[1] = configPair[1].equals("empty") ? "" : configPair[1];
            map.put(configPair[0].trim(), configPair[1]);
        }

        return map;
    }

    @SuppressWarnings("unused")
    public static void printTagIds(WebDriver driver, String tagName)
    {
        final List<WebElement> tags = driver.findElements(By.tagName(tagName));
        for (WebElement tag : tags) {
            System.out.println(tagName + " = id: " + tag.getAttribute("id") + ", name: " + tag.getAttribute("name")
                    + ", class: " + tag.getAttribute("class"));
            System.out.println((tagName.equals("iframe") ? tag.getAttribute("src") : ""));
        }
    }

    public static void checkExecutionStatus(String execute, ITestContext context)
    {
        String testcasename = context.getCurrentXmlTest().getName();
        String mtcid = context.getCurrentXmlTest().getParameter("mtcid");
        if (execute.equalsIgnoreCase("n")) {
            throw new SkipException(
                    "Test Case With Name:" + testcasename + ", having TCID:" + mtcid + " Skipped by the user.");
        }
    }

    public static String askBoxWindowOperation(String inputValue, boolean pressOkay, WebDriver driver) throws InterruptedException
    {
        WebDriverWait waitForReload = new WebDriverWait(driver, Constants.waitTimeForRefresh, 1000);
        String mainWindow = driver.getWindowHandle();
        driver.switchTo().defaultContent();
        waitForReload.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(2));
        WebElement newFolderButton = null;

        waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.inputBox));
        WebElement folderNameFeild = driver.findElement(TagIds.inputBox);
        folderNameFeild.sendKeys(inputValue);
        if (pressOkay) {
            newFolderButton = driver.findElement(TagIds.okayButton);
            Thread.sleep(1000);
            newFolderButton.click();
        } else {
            newFolderButton = driver.findElement(TagIds.cancleButton);
            Thread.sleep(1000);
            newFolderButton.click();
        }

        return mainWindow;
    }

    public static void selectRightClickOption(WebDriverWait waitForReload, String option, WebDriver driver)
    {
        driver.switchTo().defaultContent();
        waitForReload.until(ExpectedConditions.visibilityOfElementLocated(TagIds.rightClickDiv));
        waitForReload.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(TagIds.rightClickFrame_CS17));
        waitForReload.until(ExpectedConditions.elementToBeClickable(By.name(option)));
    }

    public static void performRightClick(WebElement element, WebDriver driver)
    {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.contextClick(element).build().perform();
    }

    public static void traverseToPimSetting(WebDriverWait waitForReload, WebDriver driver)
    {
        traverseToPimStudio(waitForReload, driver);
        waitForReload.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(TagIds.pimTree));
        waitForReload.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));//For CS17
        waitForReload.until(ExpectedConditions.elementToBeClickable(TagIds.pimSetting));
        WebElement pimTreeSettings = driver.findElement(TagIds.pimSetting);
        pimTreeSettings.click();
    }

    public static void traverseToPimStudio(WebDriverWait waitForReload, WebDriver driver)
    {
        driver.switchTo().defaultContent();
        waitForReload.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        waitForReload.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(TagIds.pimTreeFrame));
        driver.switchTo().frame(0);
    }
}
