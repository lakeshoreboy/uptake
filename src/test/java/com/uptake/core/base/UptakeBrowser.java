package com.uptake.core.base;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;


/**
 * Created by venuarigela on 11/28/17.
 */
public class UptakeBrowser {

    private WebDriver driver;
    private ITestContext context;

    public final static String PARAM_RUN = "selenium.run";
    public final static String PARAM_BROWSER = "selenium.browser";
    public final static String PARAM_VERSION = "selenium.version";
    public final static String PARAM_PLATFORM = "selenium.platform";


    //SELENIUM BROWSER CONFIGURATION
    public final static String BROWSER_FIREFOX = "FIREFOX";
    public final static String BROWSER_CHROME = "CHROME";
    public final static String BROWSER_SAFARI = "SAFARI";
    public final static String BROWSER_INTERNETEXPLORER = "IE";

    /**
     * Constructor
     *
     * @param context
     * @throws InterruptedException
     * @throws IOException
     */
    public UptakeBrowser(ITestContext context) throws IOException, InterruptedException {
        this.context = context;

        //Three intents to create the driver before fail.
        int count=0;
        do{
            setDriver();
            count++;
        }while(this.driver == null && count<3);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public ITestContext getContext() {
        return context;
    }

    public String getBrowser() {
        return context.getCurrentXmlTest().getParameter(PARAM_BROWSER).toUpperCase();
    }

    private void setDriver() throws IOException, InterruptedException
    {
        String browser = context.getCurrentXmlTest().getParameter(PARAM_BROWSER);
        if (browser == null) {
            browser = BROWSER_CHROME; //chrome by default
            context.getCurrentXmlTest().addParameter(PARAM_BROWSER, browser);
        }

        String version = context.getCurrentXmlTest().getParameter(PARAM_VERSION);
        if (version == null) {
            version = "UNDEFINED";
            context.getCurrentXmlTest().addParameter(PARAM_VERSION, version);
        }

        String platform = context.getCurrentXmlTest().getParameter(PARAM_PLATFORM);
        if (platform == null) {
            platform = "UNDEFINED";
            context.getCurrentXmlTest().addParameter(PARAM_PLATFORM, platform);
        }

        DesiredCapabilities capabilities;

        if (browser.equalsIgnoreCase(BROWSER_FIREFOX)) {
            capabilities = setFirefoxCapabilities(version, getPlatform(platform));
        } else if (browser.equalsIgnoreCase(BROWSER_CHROME)) {
            capabilities = setChromeCapabilities(version, getPlatform(platform));
        } else if (browser.equalsIgnoreCase(BROWSER_SAFARI)) {
            capabilities = setSafariCapabilities(version, getPlatform(platform));
        } else if (browser.equalsIgnoreCase(BROWSER_INTERNETEXPLORER)) {
            capabilities = setIECapabilities(version, getPlatform(platform));
        } else {
            //Chrome  by default
            capabilities = setChromeCapabilities(version, getPlatform(platform));
        }

        String run = context.getCurrentXmlTest().getParameter(PARAM_RUN);
        if (run == null) {
            run = "UNDEFINED";
            context.getCurrentXmlTest().addParameter(PARAM_RUN, run);
        }

        System.out.println("configuration[" + run + "|" + browser + "|" + version + "|" + platform + "]");

        WebDriver driver = null;

        try {

                //create a local driver
                this.driver = setLocalDriver(browser);

        } catch (WebDriverException e) {
            Utils.writeToLog("ERROR CONNECTION: Can't create driver.");
            System.out.println(e.getStackTrace());
        }
    }

    private static DesiredCapabilities setFirefoxCapabilities(String version, Platform platform) throws MalformedURLException, InterruptedException {
        System.out.println("running firefox");

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, platform);
        capabilities.setCapability("name", "running with Firefox");
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capabilities.setCapability("--disable-extensions",true);
        return capabilities;
    }

    private static DesiredCapabilities setChromeCapabilities(String version, Platform platform) throws IOException, InterruptedException {
        System.out.println("running chrome");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, platform);
        capabilities.setCapability("name", "running with Chrome");
        capabilities.setCapability("--disable-extensions",true);

        return capabilities;
    }

    private static DesiredCapabilities setSafariCapabilities(String version, Platform platform) throws MalformedURLException, InterruptedException {
        System.out.println("running Safari");

        DesiredCapabilities capabilities = DesiredCapabilities.safari();
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, platform);
        capabilities.setCapability("name", "running with Safari");

        return capabilities;
    }

    private static DesiredCapabilities setIECapabilities(String version, Platform platform) throws MalformedURLException, InterruptedException {
        System.out.println("running IE");

        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, "Windows10");
        capabilities.setCapability("name", "running with internet explorer");
        capabilities.setCapability("--disable-extensions",true);

        return capabilities;
    }

    /**
     * @description to set local driver
     * @param browser
     * @return
     * @throws IOException
     */

    private WebDriver setLocalDriver(String browser) throws IOException {
        WebDriver driver;
        if (browser.equalsIgnoreCase(BROWSER_FIREFOX)) {
            driver = setFirefoxDriver();
        } else if (browser.equalsIgnoreCase(BROWSER_CHROME)) {
            driver = setChromeDriver();
        } else if (browser.equalsIgnoreCase(BROWSER_SAFARI)) {
            driver = setSafariDriver();
        } else {
            //Chrome  by default
            driver = setChromeDriver();
        }

        return driver;
    }

    private static WebDriver setFirefoxDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        WebDriver driver = new FirefoxDriver((Capabilities) profile);

        return driver;
    }

    private static ChromeDriverService service;

    /**
     *
     * @return
     * @throws IOException
     */
    private static WebDriver setChromeDriver() throws IOException {

        System.out.println(System.getProperty("user.dir"));
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-extensions");
        chromeOptions.addArguments("--start-maximized");
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver = new Augmenter().augment(driver);
        return driver;
    }

    /**
     *
     * @return safari driver
     */
    private static WebDriver setSafariDriver() {
        WebDriver driver = new SafariDriver();
        driver.manage().deleteAllCookies();

        return driver;
    }


    /**
     *
     * @param platformString
     * @return
     */
    private static Platform getPlatform(String platformString) {
        if (platformString.contains("MAC"))
            return Platform.MAC;
         else if (platformString.contains("WINDOWS")) {
            return Platform.WINDOWS;
        }else{
             return Platform.MAC; //default platform
        }

        }

    /**
     *
     * @param result
     * @throws IOException
     */
    public void finish(ITestResult result) throws IOException {
        String run = context.getCurrentXmlTest().getParameter(PARAM_RUN);
        Utils.writeToLog("finish.run= " + run);

            try {
                driver.close();
            } catch (WebDriverException e) {
                Utils.writeToLog("ERROR CONNECTION: can't find the driver...");
            }

    }
}
