package com.uptake.core.base;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


/**
 * Created by venuarigela on 11/29/17.
 */
public class Utils {

    /**
     * Write the given text on the console log and the report log.
     *
     * @param log Text to log.
     */
    public static void writeToLog(String log) {
        System.out.println(log);
        Reporter.log(log);
    }

    /**
     * wait 30 sec till the element is displayed or we can use timeout parameter
     */
    public static void waitForElement(WebElement element,String desc){

        WebDriverWait wait = new WebDriverWait(UptakeDriver.getInstance().getDriver(), 50);
        wait.until(ExpectedConditions.visibilityOf(element));
        verifyElementExist(element,desc);
    }

    /**
     *
     * @param moveElement
     * @param tobeClickeEle
     * @param descr
     */
    public static void moveandClickElement(WebElement moveElement, WebElement tobeClickeEle, String descr){

        Actions actions = new Actions(UptakeDriver.getInstance().getDriver());
        actions.moveToElement(moveElement).click(tobeClickeEle).perform();

        writeToLog(descr);
    }

    /**
     *
     * @param str1
     * @param str2
     * @param desc
     */
    public static void compareStrings(String str1, String str2 , String desc){
        writeToLog("verifying "+desc);
        Assert.assertEquals(str1,str2,str1+ " did not match with "+str2  );

    }

    /**
     *
     * @param element
     * @param desc
     */
    public static void verifyElementExist(WebElement element, String desc){
       writeToLog("verifying if "+desc+ " is existed");
       Assert.assertTrue(element.isDisplayed(),desc+" not displayed");
    }

    public static void clickElement(WebElement element, String desc){
        writeToLog("clicking on "+desc);
        verifyElementExist(element,desc);
        element.click();

    }
}
