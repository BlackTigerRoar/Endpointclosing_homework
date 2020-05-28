package screens.articles;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import screens.readinglists.ReadingListScreen;
import wikiwiki.WikiCore;

import java.util.List;

/**
 * Article Screen class - Extends WikiCore so this class can have everything that WikiCore class has
 */
public class ArticleDetailScreen extends WikiCore {
    /*
        MobileElement Objects
    */
    @AndroidFindBy (id = "article_menu_bookmark")
    @iOSXCUITFindBy (xpath = "//XCUIElementTypeButton[@name=\"Save for later\"]")
    private MobileElement bookmark;

    @AndroidFindBy (className = "android.view.View")
    @iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"Appium\"])")
    private List<MobileElement> articleDetailViews;

    // This is due to wiki iOS app layout issue. It didn't have any identifier, and xpath is too slow for this
    // assignment purpose. In the prod environment, we can work with developer to make this better.
    @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"Appium\"]/child::XCUIElementTypeStaticText")
    private MobileElement wikiArticle;

    /*
        Screen Actions
    */
    public ReadingListScreen touchBookmarkIcon() {
        touch(bookmark);
        return new ReadingListScreen();
    }

    public ArticleScreen goBackToArticleScreen() {
        goBack();
        return new ArticleScreen();
    }

    public String getArticleTitle() {
        MobileElement articleTitle = articleDetailViews.get(0);
        System.out.println(articleTitle);
        return getElementText(articleTitle);
    }

    // Demo for another way to dynamically grab element
    public String getIosArticleTitle() {
        return getElementText(wikiArticle);
    }
}