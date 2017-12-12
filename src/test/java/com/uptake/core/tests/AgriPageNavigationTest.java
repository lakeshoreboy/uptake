package com.uptake.core.tests;

import com.uptake.core.base.UptakeDriver;
import com.uptake.core.base.UptakePage;
import com.uptake.core.base.UptakeTest;
import com.uptake.core.pages.AgriNavigationPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by venuarigela on 12/7/17.
 */
public class AgriPageNavigationTest extends UptakeTest {

@Test(dataProvider = "urls", groups = { "pagenavigation" }, description = "Verify if page is navigating to Agricutlute and back to Home Page properly")
    public void verifyPageNavigationTest(String url) throws InterruptedException {

    AgriNavigationPage navigationPage = PageFactory.initElements(UptakeDriver.getInstance().getDriver(), AgriNavigationPage.class);
    navigationPage.agriculturePage(url);

    }

    @DataProvider(name="urls")
    public static Object[][] BlogPostData() {
        return new Object[][]{
                {UptakePage.homePageUrl}

        };
    }

}
