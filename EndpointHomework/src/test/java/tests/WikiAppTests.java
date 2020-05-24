package tests;

import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.articles.ArticleDetailScreen;
import screens.articles.ArticleScreen;
import screens.WikiScreen;
import screens.mylists.MyListsScreen;
import screens.mylists.SavedListScreen;
import screens.readinglists.ReadingListScreen;
import wikiwiki.WikiCore;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Test cases based on the homework requirements
 */
public class WikiAppTests extends WikiCore {
    /*
        List the screen objects that we need in Wiki Home Screen Tests
     */
    WikiScreen wikiScreen;
    ArticleScreen articleScreen;
    ArticleDetailScreen articleDetailScreen;
    ReadingListScreen readingListScreen;
    MyListsScreen myListScreen;
    SavedListScreen savedListScreen;

    /*
        Create wiki screen object before each method
     */
    @BeforeMethod
    public void beforeMethod(Method method) {
        wikiScreen = new WikiScreen();
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
        articleScreen = wikiScreen.touchNews(0);
        articleDetailScreen = articleScreen.touchArticle(0);
        readingListScreen = articleDetailScreen.touchBookmarkIcon();
        readingListScreen.touchSavedText();
        articleDetailScreen.goBackToArticleScreen();
        articleScreen.touchArticle(1);
        articleDetailScreen.touchBookmarkIcon();
        readingListScreen.touchSavedText();
        articleDetailScreen.goBackToArticleScreen();
        articleScreen.goBackToWikiHomeScreen();
        myListScreen = wikiScreen.touchMyLists();
        savedListScreen = myListScreen.touchSavedList();
        savedListScreen.deleteTheList(0);

        // Verify the lower bound and upper bound articles.
        // We deleted 1 article, and total article is 1
        // lower/upper bound value should be 1 of 1
        List<Integer> availalbeArticles = savedListScreen.getAvailableArticlesCount();
        int lowerBoundArticleNumber = availalbeArticles.get(0);
        int higherBoundArticleNumber = availalbeArticles.get(1);

        Assert.assertEquals(lowerBoundArticleNumber, 1);
        Assert.assertEquals(higherBoundArticleNumber, 1);
    }

    /**
     * As a user, I want to see the article title match in my list also when I click it, so I don't get confused by title
     */
    @Test
    public void t2_articleInTheListMatchTheActualArticle() {
        // Save the current article title in MyList. Get the actual article title, and then compare both.
        String savedArticleTitle = savedListScreen.getSaveArticleTitle(0);
        savedListScreen.touchTargetArticle(0);
        String articleTitle = articleDetailScreen.getArticleTitle();

        Assert.assertEquals(articleTitle, savedArticleTitle);
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
