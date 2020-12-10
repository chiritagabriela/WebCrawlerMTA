package WebCrawlerMTA.FileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class implementing the FilterManager interface.
 *
 *
 * @author Andrei Claudia
 */

public class Search implements FileManager {
    /**
     * Method FileCrawler returns either if a specified word is found in a file or not
     * @param file specifies specifies the file in which the word is searched
     * @param searchWord indicates the word we are searching for
     **/
    private final int FileCrawler(final File file,final String searchWord) {
        Scanner scanFile;
        try {
            scanFile = new Scanner(file);
            if (null != scanFile.findWithinHorizon(searchWord, 0))
            {
                scanFile.close();
                return 1;
            }
            scanFile.close();
            return 0;
        } catch (FileNotFoundException e) {
            System.err.println("Fisierul nu exista!!");
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * Method SearchString returns an ArrayList of files who contains a specified word
     * @param path indicates the path of folder whose files we are filtering
     * @param wordToSearch indicates the word we are searching for
     **/
    private final ArrayList<File> SearchString(final String path, final String wordToSearch) {
        Utils utilComponent = new Utils();
        File directoryPath = new File(path);
        File fileList[] = directoryPath.listFiles();
        ArrayList<File> filesToPrint = new ArrayList<File>();
        for (File file : fileList)
            if ((utilComponent.ContainsIgnoreCase(file.getName(),wordToSearch)||FileCrawler(file,wordToSearch)==1)&&!file.getName().endsWith(".download"))
                filesToPrint.add(file);

        return filesToPrint;
    }

    /**
     * Method DoSpecificWork provide specific implementation of method provided by super-class
     * @param path indicates the path of folder whose files we are filtering
     * @param property indicates the property according to which the filtering is performed
     */
    @Override
    public void DoSpecificWork(final String path, final String property) {

        ArrayList<File> filesToPrint = SearchString(path, property);
       if (filesToPrint.size() == 0)
            System.out.println("Nu exista fisiere care sa contina cuvantul " + property);
       else
           {
            System.out.println("Exista " + filesToPrint.size() + " fisiere care contin cuvantul " + property + ":");
            for (File file : filesToPrint)
                System.out.println(file.getName());
            }
    }
}