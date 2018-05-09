package org.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HandleAskBox extends Thread
{
    WebDriver driver;
    String elementName;

    public String getElementName()
    {
        return elementName;
    }

    public void setElementName(String elementName)
    {
        this.elementName = elementName;
    }

    public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }

    public WebDriver getDriver()
    {
        return driver;
    }


    @Override
    public void run()
    {
        WebElement folderOption = driver.findElement(By.name(elementName));
        folderOption.click();
    }
}
