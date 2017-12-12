package com.uptake.core.base;

import org.openqa.selenium.WebDriver;

/**
 * Created by venuarigela on 12/4/17.
 */
public class UptakePage {

  //  public static String homePageUrl = System.getProperty("baseurl");
    public static String homePageUrl = "https://www.uptake.com/";
    public static String agricultureUrl = homePageUrl+"industries/agriculture";

    /**
     * Load the URL.
     *
     * @param url
     * @throws InterruptedException
     */
    public void goToUrl(String url) throws InterruptedException {
        Utils.writeToLog("URL: " + url);
        UptakeDriver.getInstance().getDriver().get(url);
    }

    /**
     * Get URL of the current page.
     *
     * @return A String with the URL of the current page.
     */
    public static String getCurrentUrl() {
        return UptakeDriver.getInstance().getDriver().getCurrentUrl();
    }

    /**
     * Get the current driver.
     *
     * @return WebDriver
     */
    public WebDriver getCurrentDriver() {
        return UptakeDriver.getInstance().getDriver();
    }
}
