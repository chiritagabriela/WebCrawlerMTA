package fileManager;

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

    //returns 1 if a word is found in a file, else returns 0
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

    //returns an ArrayList of Files if wordToSearch is found in file's name or in file's content
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

    //use doSpecificWork method to provide specific implementation of method provided by super-class
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