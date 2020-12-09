package fileManager;

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

    //returns value in KB/MB/GB of
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

    //returns an array of Strings after data deserialization made by delimitator
    public String[] SplitString(String delim, String property) {
        String[] propertyArray = new String[2];
        int i = 0;
        for (String val : property.split(delim, 2)) {
            propertyArray[i] = val;
            i++;
        }
        return propertyArray;
    }

    //returns value of Kb/Mb/Gb as integer
    int ConvertToKB(final String unit, int size) {
        if (unit.equals("MB")) {
            size = size * 1024;
        }
        if (unit.equals("GB")) {
            size = size * 1024 * 1024;
        }
        return size;
    }

    //returns a String of the file time formatted as dd/MM/yyyy
    public String FormatDateTime(FileTime fileTime) {

        LocalDateTime localDateTime = fileTime
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    //converts dateTime into Date type
    public Date ConvertToDate(String dateTime) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateTime);
            return date;
        } catch (ParseException ex) {
            System.out.println("Data nu a putut fi parsata");
        }
        return null;
    }

    //returns true if a file has extension or false if an extension doesn't exist
    public boolean GetFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return true;
        return false;
    }

    //returns true if a str contains subString, ignoring case
    public boolean ContainsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }
}
