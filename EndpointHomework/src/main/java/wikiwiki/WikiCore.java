package wikiwiki;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import testhelper.TestHelper;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

/**
 * WikiCore is the core of this automation framework.
 * This class included all driver commands, methods, etc
 * We are going to use this class in our page object model
 * -- We can managed all our driver commands in one place --
 * Most of our POM classes will extend this class because this class is SUPER
 */
public class WikiCore {

    protected static AppiumDriver driver;
    private static Properties configuration;
    private static String platform;

    // Initial page factory here, and we only do this in here.
    public WikiCore() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Here is where the framework is setting up Appium Driver. Due to the iOS limitation, we focus on Android here.
     * To run Android and iOS together, we can simply add the switch case to let the driver setup both Android and iOS.
     * In this homework, I tested it with real device. However, we can modify endpoint_testing.xml file to run both
     * emulator or real device at the same time if we want.
     * // Use parameters with xml file to make this automation framework scalable //
     * @param platformName  - iOS or Android
     * @param platformVersion - OS Version
     * @param deviceName - Device Name for metrics
     * @throws Exception - Catch errors
     */
    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeTest
    public void driverSetup(String platformName, String platformVersion, String deviceName) throws Exception{
        platform = platformName;
        try {
            configuration = new Properties();
            String configFileName = "configuration.properties";
            configuration.load(getClass().getClassLoader().getResourceAsStream(configFileName));
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            // Getting platformName, platformVersion and deviceName from TestNG file.
            desiredCapabilities.setCapability("platformName", platformName);
            desiredCapabilities.setCapability("platformVersion", platformVersion);
            desiredCapabilities.setCapability("deviceName", deviceName);

            if (platformName.equals("Android")) {
                desiredCapabilities.setCapability("automationName", configuration.getProperty("androidUiAutomator2"));
                desiredCapabilities.setCapability("appPackage", configuration.getProperty("androidWikipediaPackage"));
                desiredCapabilities.setCapability("appActivity", configuration.getProperty("androidAppActivity"));
                desiredCapabilities.setCapability("noReset", "true");
                URL url = new URL(configuration.getProperty("appiumServerUrl"));

                driver = new AndroidDriver(url, desiredCapabilities);
            } else if (platformName.equals("iOS")) {
                desiredCapabilities.setCapability("automationName", configuration.getProperty("iOSTestFramework"));
                desiredCapabilities.setCapability("app", getClass().getResource(configuration.getProperty("iOSWikiAppPath")).getFile());
                //desiredCapabilities.setCapability("bundleId", configuration.getProperty("iOSWikiBuildingId"));
                desiredCapabilities.setCapability("noReset", "true");
                URL iosUrl = new URL(configuration.getProperty("appiumServerUrl"));

                driver = new IOSDriver(iosUrl, desiredCapabilities);

            } else {
                throw new Exception("Platform is not correct in the TestNG file: endpoint_testing.xml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Special wait is use for special case when we are not really waiting for special element.
     * Mainly due to environment being slow.
     * @param seconds
     */
    protected void specialWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     *  Wait method to make sure the thing we are waiting for is showing up on the screen
     *  Default to 10 seconds but the test will execute if the thing shows up 3 seconds.
     *  This way we don't wait 10 seconds each time this method gets called.
     * @param element
     */

    protected void waitForThingsToShowUp(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, TestHelper.WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Set text into a text edit field
     * @param element
     * @param text
     */
    protected void setText(MobileElement element, String text) {
        waitForThingsToShowUp(element);
        element.sendKeys(text);
    }

    /**
     * Clean up text field
     * @param element
     */
    protected void cleanUpText(MobileElement element) {
        waitForThingsToShowUp(element);
        element.clear();
    }

    /**
     * Touch mobile element on the screen
     * @param element
     */
    protected void touch(MobileElement element) {
        waitForThingsToShowUp(element);
        element.click();
    }

    /**
     * Get mobile screen attribute such as text, title, etc
     * @param element
     * @param attribute
     * @return
     */
    private String getScreenAttribute(MobileElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    /**
     * Get mobile screen text based on platform
     * @param element
     * @return
     */
    protected String getElementText(MobileElement element) {
        waitForThingsToShowUp(element);
        switch(platform) {
            case "iOS":
                return getScreenAttribute(element, "name");
            case "Android":
                return getScreenAttribute(element, "text");
        }
        return null;
    }

    /**
     * Standard Android back button
     */
    protected void goBack() {
        driver.navigate().back();
    }

    /**
     * This method perform swipe left action on the target item
     * Here I used platform to identify the driver type, and use the different
     * value since iOS and Android calculate the value differently
     * @param item
     */
    protected void swipeElementToLeft(MobileElement item) {
        waitForThingsToShowUp(item);
        switch(platform) {
            case "iOS":
                new TouchAction(driver)
                        .press(element(item))
                        .waitAction(waitOptions(ofMillis(1000)))
                        .moveTo(element(item,-1000,0))
                        .release()
                        .perform();
                return;
            case "Android":
                new TouchAction(driver)
                        .press(element(item))
                        .waitAction(waitOptions(ofMillis(300)))
                        .moveTo(element(item,500,0))
                        .release()
                        .perform();
        }
    }

    /**
     * iOS and Android function differently, hence sometimes we need to have special method
     * for each one. In this case, this method is focus on item swipe/delete feature in iOS.
     * @param item
     */
    protected void iOsTouchSwipeRightItem(MobileElement item) {
        waitForThingsToShowUp(item);
        specialWait(1);
        // Calculate the center of the box
        Rectangle itemRect = item.getRect();
        int itemCenterX = itemRect.getX() + itemRect.getWidth() / 2;
        int itemCenterY = itemRect.getY() + itemRect.getHeight() / 2;
        int targetX = itemCenterX + (itemRect.getWidth() / 2);
        int targetY = itemCenterY;

        // Touch the very right item, in this case, it is Trash bin,
        // which the source code doesn't give mobile element ID
        // By targeting the item's center, and the very right corner.
        // This will make sure we are always targeting that spot even
        // if the device screen size changes.
        new TouchAction(driver)
                .press(point(targetX, targetY))
                .waitAction(waitOptions(ofMillis(1000)))
                .release()
                .perform();
    }


    /**
     * Rotate the screen to target orientation
     * Orientation mode can be ScreenOrientation.LANDSCAPE or ScreenOrientation.PORTRAIT
     * @param mode
     */
    protected void rotateTheScreen(ScreenOrientation mode) {
        driver.rotate(mode);
    }

    /**
     * Verify the screen orientation
     * @param mode
     * @return
     */
    protected boolean verifyOrientation(ScreenOrientation mode) {
        return mode == driver.getOrientation();
    }

    /**
     * Destroy the driver after its done.
     */
    @AfterTest
    public void destroy() {
        driver.quit();
    }
}
