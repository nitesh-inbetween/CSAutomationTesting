package org.facade;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory
{
    private static WebDriver    driver = null;
    private DesiredCapabilities browserSpec;

    public static WebDriver getDriver(HashMap<String, String> parameters)
    {
        if (driver == null) {
            DriverFactory driverFactoryInsatnce = new DriverFactory();
            switch (parameters.get("browsername"))
            {
                case "ff":
                    driverFactoryInsatnce.setFirefoxDriver(parameters);
                    break;

                case "gc":
                    driverFactoryInsatnce.setGoogleChromeDriver(parameters);
                    break;

                case "ie":
                    driverFactoryInsatnce.setInternetExplorerDriver(parameters);
                    break;

                default:
                    driverFactoryInsatnce.setInternetExplorerDriver(parameters);
                    break;
            }
        }

        return driver;
    }

    private void setInternetExplorerDriver(HashMap<String, String> parameters)
    {
        if (parameters.get("remote").equals("no")) {
            System.setProperty("webdriver.ie.driver",
                    System.getProperty("user.dir") + "//Dependencies//IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        } else {
            browserSpec = new DesiredCapabilities("internetexplorer", parameters.get("browserversion"),
                    org.openqa.selenium.Platform.WINDOWS);
            driver = (InternetExplorerDriver) new RemoteWebDriver(browserSpec);
        }
    }

    private void setGoogleChromeDriver(HashMap<String, String> parameters)
    {
        if (parameters.get("remote").equals("no")) {
            System.setProperty("webdriver.chrome.driver",
                    System.getProperty("user.dir") + "//Dependencies//chromedriver_2.28.455520_55-57v.exe");
            driver = new ChromeDriver();
        } else {
            browserSpec = new DesiredCapabilities("chrome", parameters.get("browserversion"),
                    org.openqa.selenium.Platform.WINDOWS);
            driver = (ChromeDriver) new RemoteWebDriver(browserSpec);
        }
    }

    private void setFirefoxDriver(HashMap<String, String> parameters)
    {
        if (parameters.get("remote").equals("no")) {
            System.setProperty("webdriver.firefox.marionette",
                    System.getProperty("user.dir") + "//Dependencies//geckodriver.exe");
            driver = new FirefoxDriver();
        } else {
            browserSpec = new DesiredCapabilities("firefox", parameters.get("browserversion"),
                    org.openqa.selenium.Platform.WINDOWS);
            driver = (FirefoxDriver) new RemoteWebDriver(browserSpec);
        }
    }

}
