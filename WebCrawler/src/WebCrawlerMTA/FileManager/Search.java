package WebCrawlerMTA.FileManager;
import WebCrawlerMTA.Logger.Info;
import WebCrawlerMTA.Logger.Logger;
import WebCrawlerMTA.Logger.Warn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class implementing the FilterManager interface.
 *
 *
 * @author Andrei Claudia
 */

public class Search implements FileManager {
    /**
     * Member description
     */
    List<File> allFoldersFiles;

    /**
     * Filter class constructor
     * Initializes the newly created object
     */
    public Search() {
        allFoldersFiles = new ArrayList<File>();
    }
    /**
     * Method FileCrawler returns either if a specified word is found in a file or not
     * @param file specifies specifies the file in which the word is searched
     * @param searchWord indicates the word we are searching for
     **/
    private final int FileCrawler(final File file,final String searchWord) {
        Scanner scanFile;
        Logger logger = new Warn();
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
            System.out.println("Searched file does not exist.");
            logger.LoggerInfo("Searched file not found.");
        }

        return 0;
    }
    /**
     * Method GetAllFiles sets list of all directories and subdirectories
     * files, using recursion
     * @param directoryName represents root directory
     * @param files represents list of all saved files
     */
    private void GetAllFiles(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    GetAllFiles(file.getAbsolutePath(), files);
                }
            }
    }
    /**
     * Method SearchString returns an ArrayList of files who contains a specified word
     * @param path indicates the path of folder whose files we are filtering
     * @param wordToSearch indicates the word we are searching for
     **/
    private final ArrayList<File> SearchString(final String path, final String wordToSearch) {
        Utils utilComponent = new Utils();
        this.allFoldersFiles.clear();
        this.GetAllFiles(path,allFoldersFiles);

        ArrayList<File> filesToPrint = new ArrayList<File>();
        for (File file : this.allFoldersFiles)
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
            System.out.println("There are no files that contain the word [" + property+ "]");
        else
        {
            System.out.println("There are " + filesToPrint.size() + " files that contain the word [" + property + "]:");
            for (File file : filesToPrint)
                System.out.println(file.getAbsolutePath());
        }
    }
}