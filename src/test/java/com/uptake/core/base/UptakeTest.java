package com.uptake.core.base;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by venuarigela on 12/4/17.
 */
public class UptakeTest {


        @BeforeMethod
        public void setUp(ITestContext context, Method method) throws IOException, InterruptedException {
            UptakeDriver.getInstance().setUp(context);
            System.setProperty(UptakeDriver.ESCAPE_PROPERTY, "false");

            Utils.writeToLog("Running test case- " + method.getName() + " on " + context.getName());
        }

        @AfterMethod
        public void tearDown(ITestContext context, ITestResult result) throws IOException, InterruptedException {
            UptakeDriver.getInstance().tearDown(context, result);
        }



}
