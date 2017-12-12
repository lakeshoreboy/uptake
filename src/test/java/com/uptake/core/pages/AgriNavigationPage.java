package com.uptake.core.pages;

import com.uptake.core.base.UptakePage;
import com.uptake.core.base.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import javax.rmi.CORBA.Util;

/**
 * Created by venuarigela on 12/7/17.
 */
public class AgriNavigationPage extends UptakePage {


    @FindBy(xpath = "//span[contains(text(),'Industries')]")
    private WebElement IndustriesTab;

    @FindBy(xpath = "//a[contains(text(),'Agriculture')]")
    private WebElement AgricultureLinkEle;

    @FindBy(xpath = "//h1[contains(text(),'Agriculture')]")
    private WebElement AgricultureHeadEle;

    @FindBy(how=How.CLASS_NAME ,using = "l-site-header__brand")
    private WebElement uptakeHeaderTitle;

    @FindBy(xpath = "//h1[contains(text(),'UPTAKE PRODUCTS')]")
    private WebElement UptakeHeadPicEle;

    /**
     * @Desc verifying if page is correctly navigating to Agriculture page and back to Uptage Home page
     * @param url
     * @throws InterruptedException
     */
    public void agriculturePage(String url) throws InterruptedException {

        goToUrl(url);
        String baseUrl =  getCurrentUrl();
        Utils.compareStrings(baseUrl,UptakePage.homePageUrl,"home page url");
        Utils.waitForElement(uptakeHeaderTitle,"uptake title");
        Utils.moveandClickElement(IndustriesTab,AgricultureLinkEle,"Agriculture link ");
        Utils.waitForElement(AgricultureHeadEle, "Agriculture picture");
        Utils.writeToLog("verifying agriculture page url :"+getCurrentUrl());
        Utils.compareStrings(getCurrentUrl(),agricultureUrl,"agriculture page url");
        Utils.clickElement(uptakeHeaderTitle,"Uptake Header to go back to home page");
        Utils.waitForElement(UptakeHeadPicEle,"Uptake Home page viewport");
        Utils.compareStrings(getCurrentUrl(),UptakePage.homePageUrl,"home page url");
    }

}
