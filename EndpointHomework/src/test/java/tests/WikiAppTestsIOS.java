package tests;

import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.WikiScreen;
import screens.articles.ArticleDetailScreen;
import screens.articles.ArticleScreen;
import screens.mylists.MyListsScreen;
import screens.mylists.SavedListScreen;
import screens.readinglists.ReadingListScreen;
import screens.search.SearchWikipediaScreen;
import wikiwiki.WikiCore;

import java.lang.reflect.Method;


/**
 * Test cases based on the homework requirements
 */
public class WikiAppTestsIOS extends WikiCore {
    /*
        List the screen objects that we need in Wiki Home Screen Tests
     */
    WikiScreen wikiScreen;
    ArticleScreen articleScreen;
    ArticleDetailScreen articleDetailScreen;
    ReadingListScreen readingListScreen;
    MyListsScreen myListScreen;
    SavedListScreen savedListScreen;
    SearchWikipediaScreen searchWikiScreen;

    /*
        Create wiki screen object before each method
     */
    @BeforeMethod
    public void beforeMethod(Method method) {
        wikiScreen = new WikiScreen();
        savedListScreen = new SavedListScreen();
        // This is just for testing purpose. In the real environment, we usually don't put anything in the console log.
        // We usually look at the test result report for information that we need.
        System.out.println("\n" + "::::::: Starting test: " + method.getName() + " :::::::" + "\n");
    }

    /**
     * As a user, I want to be able to save two articles into My Lists, so I can read them later
     * -----------------
     * This test should break down to several small test cases, but for the purpose of homework,
     * let's focus more on the framework architecture instead of test cases.
     */
    @Test
    public void t1_saveTwoArticlesIntoOneList() {
        searchWikiScreen = wikiScreen.touchSearchWikipediaEditBox();
        // Basic version: Hard code the Search word. We should have a XML file to managed all our Strings.
        searchWikiScreen.searchFor("Appium");
        articleDetailScreen = searchWikiScreen.touchSearchResult(0);
        articleDetailScreen.touchBookmarkIcon();
        goBack();
        wikiScreen.touchSearchWikipediaEditBox();
        articleDetailScreen = searchWikiScreen.touchSearchResult(1);
        articleDetailScreen.touchBookmarkIcon();
        goBack();
        wikiScreen.touchMyLists();
        int previousSaveListSize = savedListScreen.getArticlesSize();
        savedListScreen.iOsDeleteTheList(0);
        int currentSaveListSize = savedListScreen.getArticlesSize();

        // Verify that the previous article size should be 2
        Assert.assertEquals(previousSaveListSize, 2);
        // Verify that the current article size should be 1
        Assert.assertEquals(currentSaveListSize, 1);
    }

    /**
     * As a user, I want to see the article title match in my list also when I click it, so I don't get confused by title
     */
    @Test
    public void t2_articleInTheListMatchTheActualArticle() {
        // Save the current article title in MyList. Get the actual article title, and then compare both.
        wikiScreen.touchMyLists();
        String savedArticleTitle = savedListScreen.getIosSaveArticleTitle(0);
        // Due to iOS app layout format, I had to parse the string in order to get article title. Downside about open source app.
        String trimTheTitleBasedOnWikiFormat = savedArticleTitle.split(" ")[0].replaceAll("\\s+", "-").split("-")[0];
        articleDetailScreen = savedListScreen.touchIosTargetArticle(0);
        String articleTitle = articleDetailScreen.getIosArticleTitle();

        // Verify that the article title is match
        Assert.assertEquals(articleTitle, trimTheTitleBasedOnWikiFormat);
    }

    /**
     * As a user, I want to see the UI rotate when I rotate the device, so it's easier for me to read the article
     * ----------------
     * There are more test cases can be added for landscape version, but since this is not the endpoint app, won't do here.
     */
    @Test
    public void t3_verifyTheScreenCanBeRotated() {
        // Rotate the screen. Get the orientation from android OS since that is the source of truth.
        // Verify that we can switch between landscape mode and portrait mode.
        rotateTheScreen(ScreenOrientation.LANDSCAPE);
        Assert.assertEquals(verifyOrientation(ScreenOrientation.LANDSCAPE), true);
        rotateTheScreen(ScreenOrientation.PORTRAIT);
        Assert.assertEquals(verifyOrientation(ScreenOrientation.PORTRAIT), true);
    }
}
