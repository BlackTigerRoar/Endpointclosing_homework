package screens.mylists;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import wikiwiki.WikiCore;

/**
 * Article Screen class - Extends WikiCore so this class can have everything that WikiCore class has
 */
public class MyListsScreen extends WikiCore {
    /*
        MobileElement Objects
    */
    @AndroidFindBy (id = "item_title") private MobileElement savedList;

    /*
        Screen Actions
    */
    public SavedListScreen touchSavedList() {
        touch(savedList);
        return new SavedListScreen();
    }
}