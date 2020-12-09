package fileManager;

/**
 * Class generating objects of concrete class based on given information
 *
 *
 * @author Andrei Claudia
 */

public class FileManagerFactory {

    //use GetAction method to get object of type FileManager
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