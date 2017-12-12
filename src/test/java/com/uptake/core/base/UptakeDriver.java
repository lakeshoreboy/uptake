package com.uptake.core.base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by venuarigela on 11/28/17.
 */
public class UptakeDriver {

    private static volatile UptakeDriver instance = null;

    private int timeout =30;

    public static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";


    public static ThreadLocal<UptakeBrowser> threadLocalDriver = new ThreadLocal<UptakeBrowser>();

    protected UptakeDriver(){}

    /**
     *
     * @return
     */
   public static UptakeDriver getInstance() {

        if (instance == null) {

            synchronized (UptakeDriver.class) {
                if (instance == null) {
                    instance = new UptakeDriver();
                }
            }

        }

        return instance;

    }

    /**
     * Get webDriver from UptakeDriver singleton.
     *
     * @return driver
     */
public WebDriver getDriver(){
        return UptakeDriver.threadLocalDriver.get().getDriver();
    }

    public void waitFor(int seconds) throws InterruptedException {
        UptakeDriver.threadLocalDriver.get().wait(seconds);
    }

    public void setDriverImplicityTimeout(int seconds)
    {
        getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Force driver to wait until page finishes load.
     */
    public void waitForLoad() {
        setDriverImplicityTimeout(timeout);

    }

    /**
     * Global setup
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    public void setUp(ITestContext context) throws IOException, InterruptedException {


        UptakeBrowser thread = new UptakeBrowser(context);
        UptakeDriver.threadLocalDriver.set(thread);

        setDriverImplicityTimeout(timeout);

        waitForLoad();
        getDriver().manage().window().maximize();
    }


    /**
     * Global tear Down
     *
     * @param context
     * @param result
     * @throws IOException
     * @throws InterruptedException
     */
    public void tearDown(ITestContext context, ITestResult result) throws IOException, InterruptedException {
        Reporter.setCurrentTestResult(result);

        String run = context.getCurrentXmlTest().getParameter(UptakeBrowser.PARAM_RUN);
        System.out.println("\nteardown::run= " +run);


            if (!result.isSuccess()) {
                captureScreen(result.getMethod().getRealClass().getName() +"-"+ result.getMethod().getMethodName());
            }

            Utils.writeToLog("closing driver");
            UptakeDriver.threadLocalDriver.get().finish(result);

            Utils.writeToLog("removing driver from threadlocal");
            UptakeDriver.threadLocalDriver.set(null);

    }

    public void captureScreen(String filename) {
        Utils.writeToLog("Skipped Capture Screen... (" + filename + ")");


    }
}
