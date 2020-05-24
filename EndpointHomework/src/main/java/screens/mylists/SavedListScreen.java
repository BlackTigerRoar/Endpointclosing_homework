package screens.mylists;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
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
    private List<MobileElement> listTitles;

    @AndroidFindBy (id = "item_reading_list_statistical_description")
    private MobileElement availableArticleCounts;

    /*
        Screen Actions
     */
    public void deleteTheList(int whichOne) {
        swipeLeft(listTitles.get(whichOne));
    }

    public String getSaveArticleTitle(int whichOne) {
        return getScreenAttribute(listTitles.get(whichOne), "text");
    }

    /*
        In Saved List screen, the app is showing "n of n articles available offline"
        To verify the count is correct, we can grab the string. Split it, and then get the
        index of 0 and 2, which is lower bound and upper bound. Compare both for the correctness.
     */
    public List<Integer> getAvailableArticlesCount() {
        List<Integer> availableArticlesCount = new ArrayList<Integer>();
        String[] available = getScreenAttribute(availableArticleCounts, "text").split(" ");
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