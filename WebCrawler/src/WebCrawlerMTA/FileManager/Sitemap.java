package WebCrawlerMTA.FileManager;

import WebCrawlerMTA.Logger.Logger;
import WebCrawlerMTA.Logger.Warn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class implementing SiteMap component
 *
 *
 * @author Diana Merches
 */

public class Sitemap implements FileManager {
    /**
     * Class members
     */
    FileWriter fileWriter;
    PrintWriter printWriter;

    /**
     * Method ListFoldersFiles
     * @param path specify the path to a folder
     * @param level indicates the current level to search for files and folders
     */
    private void ListFoldersFiles(String path, int level) {
        File dirPath = new File(path);
        File[] firstLevelFiles = dirPath.listFiles();

        if (firstLevelFiles != null && firstLevelFiles.length != 0) {
            for (File file : firstLevelFiles) {
                for (int i = 0; i < level; i++) {
                    printWriter.write("\t");
                }
                if (file.isDirectory()) {
                    printWriter.write(file.getName()+"/");
                    printWriter.write("\n");
                    ListFoldersFiles(file.getAbsolutePath(), level + 1);
                } else {
                    printWriter.write(file.getName());
                    printWriter.write("\n");
                }
            }
        }
    }


    /**
     * Method DoSpecificWork provide specific implementation of method provided by super-class
     * @param path indicates the path of parent folder whose files and subfolders create the sitemap
     * @param property represents name of the output file
     */
    @Override
    public void DoSpecificWork(final String path, final String property){
        try {
            File file = new File(property);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile());
            printWriter = new PrintWriter(fileWriter);
            ListFoldersFiles(path,0);
            printWriter.close();
        } catch (IOException ex) {
            System.out.println("File not found for sitemap.");
            Logger logger = new Warn();
            logger.LoggerInfo("File not found for sitemap.");
        }
    }
}