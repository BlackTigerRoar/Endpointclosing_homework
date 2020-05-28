package screens.readinglists;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import wikiwiki.WikiCore;

/**
 * Article Screen class - Extends WikiCore so this class can have everything that WikiCore class has
 */
public class ReadingListScreen extends WikiCore {
    /*
        MobileElement Objects
    */
    @AndroidFindBy (id = "item_title")
    private MobileElement savedText;

    /*
        Screen Actions
     */
    public ReadingListScreen touchSavedText() {
        touch(savedText);
        return this;
    }
}