package screens.search;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import screens.WikiScreen;
import screens.articles.ArticleDetailScreen;

import java.util.List;

/**
 * SearchWikipedia Screen class - Extends WikiScreen so this class can have everything that WikiScreen class has
 */
public class SearchWikipediaScreen extends WikiScreen {
    /*
        MobileElement Objects
    */
    @iOSXCUITFindBy(id = "Search Wikipedia")
    private MobileElement searchBar;

    @iOSXCUITFindBy(iOSNsPredicate = "type == \"XCUIElementTypeCell\" AND visible == 1")
    private List<MobileElement> searchResults;
    /*
        Screen Actions
     */

    public void touchSearchBar() {
        touch(searchBar);
    }

    public void searchFor(String searchText) {
        setText(searchBar, searchText);
        specialWait(3);
    }

    public ArticleDetailScreen touchSearchResult(int whichOne) {
        System.out.println(searchResults);
        MobileElement searchResult = searchResults.get(whichOne);
        touch(searchResult);
        return new ArticleDetailScreen();
    }

    public void cleanSearchBox() {
        cleanUpText(searchBar);
    }
}
