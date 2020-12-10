package WebCrawlerMTA.FileManager;

/**
 * Class implementing the FilterManager interface.
 *
 *
 * @author Andrei Claudia
 */

public class Sitemap implements FileManager {
    /**
     * Method DoSpecificWork provide specific implementation of method provided by super-class
     * @param path indicates the path of folder whose files we are filtering
     * @param property indicates the property according to which the filtering is performed
     */
    @Override
    public void DoSpecificWork(final String path, final String property){
        System.out.println("Creeaza sitemap");
    }
}