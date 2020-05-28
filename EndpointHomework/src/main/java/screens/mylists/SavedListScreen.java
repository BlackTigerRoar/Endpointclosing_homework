package screens.mylists;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import screens.articles.ArticleDetailScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Article Screen class - Extends MyListsScreen because this screen is belong MyListsScreen
 * Note: MyListsScreen extends WikiCore, so everything in the WikiCore is inherited to this class.
 */
public class SavedListScreen extends MyListsScreen {
    /*
        MobileElement Objects
     */
    @AndroidFindBy (id = "page_list_item_title")
    @iOSXCUITFindBy(iOSNsPredicate = "type == \"XCUIElementTypeCell\" AND visible == 1")
    private List<MobileElement> listTitles;

    @iOSXCUITFindBy(iOSNsPredicate = "type == \"XCUIElementTypeLink\" AND visible == 1")
    private List<MobileElement> iOSlistTitles;

    @AndroidFindBy (id = "item_reading_list_statistical_description")
    private MobileElement availableArticleCounts;

    /*
        Screen Actions
     */
    public void deleteTheList(int whichOne) {
        swipeElementToLeft(listTitles.get(whichOne));
    }

    public void iOsDeleteTheList(int whichOne) {
        MobileElement targetArticle = listTitles.get(whichOne);
        swipeElementToLeft(targetArticle);
        iOsTouchSwipeRightItem(targetArticle);
    }

    public int getArticlesSize() {
        return listTitles.size();
    }

    public String getSaveArticleTitle(int whichOne) {
        return getElementText(listTitles.get(whichOne));
    }

    public String getIosSaveArticleTitle(int whichOne) {
        return getElementText(iOSlistTitles.get(whichOne));
    }

    public ArticleDetailScreen touchIosTargetArticle(int whichOne) {
        MobileElement article = iOSlistTitles.get(whichOne);
        touch(article);
        return new ArticleDetailScreen();
    }

    /*
        In Saved List screen, the app is showing "n of n articles available offline"
        To verify the count is correct, we can grab the string. Split it, and then get the
        index of 0 and 2, which is lower bound and upper bound. Compare both for the correctness.
     */
    public List<Integer> getAvailableArticlesCount() {
        List<Integer> availableArticlesCount = new ArrayList<Integer>();
        String[] available = getElementText(availableArticleCounts).split(" ");
        availableArticlesCount.add(Integer.parseInt(available[0]));
        availableArticlesCount.add(Integer.parseInt(available[2]));
        return availableArticlesCount;
    }

    /*
        The screen can have multiple articles. We can access the articles by index
     */
    public ArticleDetailScreen touchTargetArticle(int whichOne) {
        MobileElement article = listTitles.get(whichOne);
        touch(article);
        return new ArticleDetailScreen();
    }
}