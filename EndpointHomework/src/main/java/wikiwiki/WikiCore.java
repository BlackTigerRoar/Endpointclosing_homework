package wikiwiki;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
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
import java.util.Properties;

/**
 * WikiCore is the core of this automation framework.
 * This class included all driver commands, methods, etc
 * We are going to use this class in our page object model
 * -- We can managed all our driver commands in one place --
 * Most of our POM classes will extend this class because this class is SUPER
 */
public class WikiCore {

    private static AppiumDriver driver;
    private static Properties configuration;

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
        try {
            configuration = new Properties();
            String configFileName = "configuration.properties";
            configuration.load(getClass().getClassLoader().getResourceAsStream(configFileName));
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", platformName);
            desiredCapabilities.setCapability("platformVersion", platformVersion);
            desiredCapabilities.setCapability("deviceName", deviceName);
            desiredCapabilities.setCapability("automationName", configuration.getProperty("androidUiAutomator2"));
            desiredCapabilities.setCapability("appPackage", configuration.getProperty("androidWikipediaPackage"));
            desiredCapabilities.setCapability("appActivity", configuration.getProperty("androidAppActivity"));
            desiredCapabilities.setCapability("noReset", "true");
            URL url = new URL(configuration.getProperty("appiumServerUrl"));

            driver = new AndroidDriver(url, desiredCapabilities);
            // sessiondId is for parallel testing purpose //
            String sessionId = driver.getSessionId().toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
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
    protected String getScreenAttribute(MobileElement element, String attribute) {
        waitForThingsToShowUp(element);
        return element.getAttribute(attribute);
    }

    /**
     * Standard Android back button
     */
    protected void goBack() {
        driver.navigate().back();
    }

    /**
     * This method perform swipe left action on the target item
     * @param item
     */
    protected void swipeLeft(MobileElement item) {
        waitForThingsToShowUp(item);
        new TouchAction(driver)
                .press(item)
                .waitAction(300)
                .moveTo(500, 0)
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
