package WebCrawlerMTA.FileManager;

/**
 * Class generating objects of concrete class based on given information
 *
 *
 * @author Andrei Claudia
 */

public class FileManagerFactory {
    /**
     * Method GetAction is used to get object of type FileManager based on
     * user input and if the type doesn't exist, the method returns null.
     * @param actionType represents the action entered by user in CLI
     */
    public FileManager GetAction(String actionType) {
        if (actionType == null) {
            return null;
        }
        FileManagerType type;

        type = FileManagerType.valueOf(actionType.toUpperCase());

        switch (type) {
            case FILTER:
                return new Filter();

            case SEARCH:
                return new Search();

            case SITEMAP:
                return new Sitemap();

        }
        return null;
    }
}