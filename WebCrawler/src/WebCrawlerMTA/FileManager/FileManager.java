package WebCrawlerMTA.FileManager;

/**
 * Interface defining formal rules of the FileManager's interaction.
 * Defines the types of inputs and outputs of methods, without implementing them
 *
 *
 * @author Andrei Claudia
 */

public interface FileManager {
    /**
     * Method DoSpecificWork interface method (does not have a body)
     * @param path indicates the path of folder whose files we are filtering
     * @param property indicates the property according to which the filtering is performed
     */
    public void DoSpecificWork(final String path, final String property);
}
