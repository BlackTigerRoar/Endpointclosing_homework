package screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import screens.articles.ArticleScreen;
import screens.mylists.MyListsScreen;
import wikiwiki.WikiCore;

import java.util.List;

/**
 * Wiki Screen class - Extends WikiCore so this class can have everything that WikiCore class has
 */
public class WikiScreen extends WikiCore {
    /*
        MobileElement Objects
     */
    @AndroidFindBy (id = "horizontal_scroll_list_item_text")
    private List<MobileElement> news;

    @AndroidFindBy (accessibility = "My lists")

    private MobileElement myListsIcon;

    /*
        Screen Actions
     */
    public ArticleScreen touchNews(int whichOne) {
        touch(news.get(whichOne));
        return new ArticleScreen();
    }

    public MyListsScreen touchMyLists() {
        touch(myListsIcon);
        return new MyListsScreen();
    }
}