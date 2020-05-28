package screens.articles;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import screens.WikiScreen;
import wikiwiki.WikiCore;

import java.util.List;

/**
 * Article Screen class - Extends WikiCore so this class can have everything that WikiCore class has
 */
public class ArticleScreen extends WikiCore {
    /*
        MobileElement Objects
    */
    @AndroidFindBy (id = "view_list_card_item_title")
    private List<MobileElement> articles;

    /*
       Screen Actions
    */
    public String getArticleTitle(int whichOne) {
        MobileElement article = articles.get(whichOne);
        return getElementText(article);
    }

    public ArticleDetailScreen touchArticle(int whichOne) {
        MobileElement article = articles.get(whichOne);
        touch(article);
        return new ArticleDetailScreen();
    }

    public WikiScreen goBackToWikiHomeScreen() {
        goBack();
        return new WikiScreen();
    }
}