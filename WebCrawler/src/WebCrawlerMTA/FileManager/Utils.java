package WebCrawlerMTA.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    /**
     * Method ReadableFileSize returns value in KB/MB/GB of the file's size
     * @param sizeBytes indicates the size of file as a long number
     */
    public String ReadableFileSize(long sizeBytes) {
        long sizeKb = sizeBytes / 1024 + 1;
        long sizeMb = sizeKb / 1024;
        long sizeGb = sizeMb / 1024;
        String cntSize;
        if (sizeGb > 0) {
            cntSize = sizeGb + " GB";
        } else if (sizeMb > 0) {
            cntSize = sizeMb + " MB";
        } else {
            cntSize = sizeKb + " KB";
        }
        return cntSize;
    }


    /**
     * Method SplitString returns an array of Strings after data deserialization
     * @param delim indicates the delimitator according to which the data is deserialized
     * @param property indicates the string we are deserializing
     */
    public String[] SplitString(String delim, String property) {
        String[] propertyArray = new String[2];
        int i = 0;
        for (String val : property.split(delim, 2)) {
            propertyArray[i] = val;
            i++;
        }
        return propertyArray;
    }


    /**
     * Method ConvertToKB returns returns value of MB/GB as KB
     * @param unit indicates the unit of measurement(MB/GB)
     * @param size indicates the size of measurement
     */
    public int ConvertToKB(final String unit, int size) {
        if (unit.equals("MB")) {
            size = size * 1024;
        }
        if (unit.equals("GB")) {
            size = size * 1024 * 1024;
        }
        return size;
    }


    /**
     * Method FormatDateTime returns the file time formatted in a specific way
     * @param fileTime indicates the file time which we have to format
     */
    public String FormatDateTime(FileTime fileTime) {

        LocalDateTime localDateTime = fileTime
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    /**
     * Method ConvertToDate converts a date into Date type
     * @param dateTime indicates date we are going to convert in a specific format
     */
    public Date ConvertToDate(String dateTime) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateTime);
            return date;
        } catch (ParseException ex) {
            System.out.println("Data nu a putut fi parsata");
        }
        return null;
    }


    /**
     * Method GetFileExtension returns true if a file has extension or false if an extension
     * doesn't exist
     * @param file indicates the file we will check if it has an extension or not
     */
    public boolean GetFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return true;
        return false;
    }


    /**
     * Method ContainsIgnoreCase returns true if a string contains another substring
     * or false if it doesn't, ignoring case
     * doesn't exist
     * @param str indicates the string we are checking
     * @param subString indicates the string we are looking for
     */
    public boolean ContainsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }
}
