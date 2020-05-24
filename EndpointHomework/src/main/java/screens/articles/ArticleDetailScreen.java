package screens.articles;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
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
    private MobileElement bookmark;

    @AndroidFindBy (className = "android.view.View")
    private List<MobileElement> articleDetailViews;


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
        return getScreenAttribute(articleTitle, "text");
    }
}




    // page object model works inner loop. If we are at some page, and if the action taking us to other page
    // we need to return the object of that particular page, so we don't need to inti the particular page
    // If we are not going to any other screen, then we return the object of the same class.