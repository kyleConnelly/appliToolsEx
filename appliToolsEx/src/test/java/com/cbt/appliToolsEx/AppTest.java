package com.cbt.appliToolsEx;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.RectangleSize;

public class AppTest extends TestCase {
    private String username = System.getenv("CBTUSRNAME"), authkey = System.getenv("CBTAUTH"), score = "fail",
            hubUrl = "http://" + username + ":" + authkey + "@hub.crossbrowsertesting.com:80/wd/hub";
    private CBTHelper cbt;
    private RemoteWebDriver driver;
    private Eyes eyes;

    public void setUp() throws MalformedURLException {
        System.out.println();
        DesiredCapabilities caps = new DesiredCapabilities();
        cbt = new CBTHelper(username, authkey, false);
        caps.setCapability("name", "Atypon Testing IntelliJ");
        caps.setCapability("browserName", "Safari");
        caps.setCapability("deviceName", "iPhone 7 Simulator");
        caps.setCapability("platformVersion", "10.2");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("record_video", "true");

        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS"));//test git

        // create a new RemoteWebDriver object
        driver = new RemoteWebDriver(new URL(hubUrl), caps);
    }

    //  myViewportSize = eyes.getViewportSize();
    //  System.out.println("viewport="+myViewportSize.width+"x"+myViewportSize.height);

    public void testApp() {
        // open a new instance of eyes
        //for desktops
//        eyes.open(driver, "CrossBrowserTesting", "My first Selenium Java test!",
//                new RectangleSize(800, 600));

        //for mobiles. you dont need the RectangleSize()
        eyes.open(driver, "Atypon", "Testing for Atypon IntelliJ");

        // navigate to the webpage we'd like to capture
        driver.get("https://dlnext.acm.org/toc/csur/current");

        // Visual checkpoint
        eyes.checkWindow("Search within CSUR");

        eyes.close();
        score = "pass";
    }

    public void tearDown() {
        cbt.setSessionId(driver.getSessionId().toString());
        cbt.setScore(score);
        if (driver != null)
            driver.quit();
        eyes.abortIfNotClosed();
    }
}