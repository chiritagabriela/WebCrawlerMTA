package fileManager;

/**
 * Class implementing the FilterManager interface.
 *
 *
 * @author Andrei Claudia
 */

public class Sitemap implements FileManager {

    //use doSpecificWork method to provide specific implementation of method provided by super-class
    @Override
    public void DoSpecificWork(final String path, final String property){
        System.out.println("Creeaza sitemap");
    }
}