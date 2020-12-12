package WebCrawlerMTA.FileManager;
import jdk.jshell.execution.Util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.*;

/**
 * Class implementing the FilterManager interface.
 *
 *
 * @author Andrei Claudia
 */

public class Filter implements FileManager {
    /**
     * Member description
     */
    private String filterProperty;
    private String filterValue;
    List<File> allFoldersFiles;
    /**
     * Filter class constructor
     * Initializes the newly created object
     */
    public Filter() {
        this.filterProperty = "";
        this.filterValue = "";
        allFoldersFiles = new ArrayList<File>();
    }

    /**
     * Method SetFilterProperties is used to set the properties of the filter
     * @param filterProperty represents the value who has to be assigned to the object
     * @param filterValue represents the value who has to be assigned to the object
     */
    private void SetFilterProperties(String filterProperty, String filterValue) {
        this.filterProperty = filterProperty;
        this.filterValue = filterValue;
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
     * Method FilterByType is used to list all files that satisfy the specified filter
     * @param extension represents filter who has to be satisfied
     */
    private ArrayList<String> FilterByType(final String path,final String extension) {
        this.allFoldersFiles.clear();
        this.GetAllFiles(path, this.allFoldersFiles);
        ArrayList<String> filesToList = new ArrayList<String>();
        //List of all the text files
        for (int i = 0; i < this.allFoldersFiles.size(); i++) {
            String fileWithPath = allFoldersFiles.get(i).getAbsolutePath().toLowerCase();
            if (fileWithPath.endsWith("." + this.filterValue)) {
                filesToList.add(allFoldersFiles.get(i).getAbsolutePath());
            }
        }
        return filesToList;
    }

    /**
     * Method FilterBySize returns an ArrayList of Strings filtered by size
     * @param path indicates the path of folder whose files we are filtering
     */
    private ArrayList<String> FilterBySize(final String path) {

        ArrayList<String> fileToPrint = new ArrayList<String>();
        int size;
        String SI;
        Utils utilFunction = new Utils();
        File dirPath = new File(path);

        size = Integer.parseInt(utilFunction.SplitString(" ", filterValue)[0]);
        SI = utilFunction.SplitString(" ", filterValue)[1].toUpperCase();
        int sizeInKB = utilFunction.ConvertToKB(SI, size);

        this.allFoldersFiles.clear();
        this.GetAllFiles(path, this.allFoldersFiles);
        //List of all the text files
        for (File file : this.allFoldersFiles) {
            int fileSize = Integer.parseInt(utilFunction.SplitString(" ", utilFunction.ReadableFileSize(file.length()))[0]);
            if (fileSize < sizeInKB)
                fileToPrint.add(file.getAbsolutePath() + " " + utilFunction.ReadableFileSize(file.length()));
        }
        return fileToPrint;
    }


    /**
     * Method FilterByDate returns an ArrayList of Strings, containing names of files,
     * filtered by date
     * @param path indicates the path of folder whose files we are filtering
     */
    private ArrayList<String> FilterByDate(final String path) {
        Utils utilFunction = new Utils();
        ArrayList<String> fileToPrint = new ArrayList<String>();
        try {
            this.allFoldersFiles.clear();
            this.GetAllFiles(path, this.allFoldersFiles);
            //List of all the text files
            for (File file : this.allFoldersFiles){
                Path filePath = Paths.get(file.getAbsolutePath());

                BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
                FileTime fileTime = attr.creationTime();

                String fileDate = utilFunction.FormatDateTime(fileTime);
                Date dateFile = utilFunction.ConvertToDate(fileDate);
                Date dateFilter = utilFunction.ConvertToDate(this.filterValue);
                if (dateFile.compareTo(dateFilter) < 0) {
                    fileToPrint.add(file.getName() + " " + fileDate);
                }
            }
        } catch (IOException ex) {
            System.out.println("Fisierul nu exista");
        }
        return fileToPrint;
    }


    /**
     * Method DoSpecificWork provide specific implementation of method provided by super-class
     * @param path indicates the path of folder whose files we are filtering
     * @param property indicates the property according to which the filtering is performed
     */
    @Override
    public void DoSpecificWork(final String path, final String property) {
        Utils utilFunction = new Utils();
        int contor = 0;
        ArrayList<String> filesToPrint = new ArrayList<String>();
        SetFilterProperties(utilFunction.SplitString("#", property)[0], utilFunction.SplitString("#", property)[1]);
        switch (this.filterProperty) {
            case "type":
                ArrayList<String> filesToList = this.FilterByType(path,this.filterValue);
                if (filesToList.size() == 0)
                    System.out.println("There are no files with the specified extension " + this.filterValue + "!");
                else {
                    System.out.println("List of the " + this.filterValue + " files in the specified directory:");
                    for (String fileName : filesToList) {
                        System.out.println(fileName);
                    }
                }
                break;

            case "size":
                filesToPrint = FilterBySize(path);
                contor  = filesToPrint.size();
                int size = Integer.parseInt(utilFunction.SplitString(" ", filterValue)[0]);
                String SI = utilFunction.SplitString(" ", filterValue)[1].toUpperCase();
                if (contor == 0)
                    System.out.println("Nu exista fisiere descarcate cu lungimea mai mica de " + size + " " + SI + "!");
                else {
                    System.out.println("Fisierele cu lungime mai mica de " + size + " " + SI + " sunt: ");
                    for (String fileToPrint : filesToPrint)
                        System.out.println(fileToPrint);
                }
                break;

            case "date":
                filesToPrint = FilterByDate(path);
                contor = filesToPrint.size();
                if (contor == 0)
                    System.out.println("Nu exista fisiere descarcate inainte de data " + this.filterValue + "!");
                else
                    for (String fileToPrint : filesToPrint)
                        System.out.println("Fisierele descarcate inainte de " + this.filterValue + " sunt: "+fileToPrint);
                break;
        }
    }
}