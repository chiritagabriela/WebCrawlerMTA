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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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

    /**
     * Filter class constructor
     * Initializes the newly created object
     */
    public Filter() {
        this.filterProperty = "";
        this.filterValue = "";
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
     * Method FilterByType is used to list all files that satisfy the specified filter
     * @param extension represents filter who has to be satisfied
     */
    private FilenameFilter FilterByType(String extension) {
        FilenameFilter textFilefilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith("."+extension)) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        return textFilefilter;
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

        File fileList[] = dirPath.listFiles();
        File generatedList[] = new File[fileList.length];
        for (File file : fileList) {
            int fileSize = Integer.parseInt(utilFunction.SplitString(" ", utilFunction.ReadableFileSize(file.length()))[0]);
            if (fileSize < sizeInKB)

                fileToPrint.add(file.getName() + " " + utilFunction.ReadableFileSize(file.length()));
        }

        return fileToPrint;

    }


    /**
     * Method FilterByDate returns an ArrayList of Strings, containing names of files,
     * filtered by date
     * @param files indicates the array of files we are filtering
     * @param pathDirectory indicates the path of folder whose files we are filtering
     */
    private ArrayList<String> FilterByDate(final File files[], final File pathDirectory) {
        Utils utilFunction = new Utils();
        ArrayList<String> fileToPrint = new ArrayList<String>();
        try {
            for (File file : files) {
                Path filePath = Paths.get(pathDirectory + "\\" + file.getName());

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
                File directoryPath = new File(path);
                FilenameFilter textFilefilter = FilterByType(this.filterValue);
                //List of all the text files
                String filesList[] = directoryPath.list(textFilefilter);
                if(filesList.length == 0 )
                    System.out.println("Nu exista fisiere de tip " + this.filterValue + "in directorul specificat!");
                else {
                    System.out.println("List of the " + this.filterValue + " in the specified directory:");
                    for (String fileName : filesList) {
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
                File pathDirectory = new File(path);
                File files[] = pathDirectory.listFiles();
                filesToPrint = FilterByDate(files, pathDirectory);
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