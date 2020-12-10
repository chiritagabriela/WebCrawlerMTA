package WebCrawlerMTA.Logger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Class that writes in file specific logger informations
 * @author Seba
 */

public class Error implements Logger {

    /**
     * Implementing Logger interface methods
     * @param fileName represents the file name where you want to write
     * @param message  represents the message that you want to write in file.
     */

    public  void LoggerInfo(String fileName, String message)
    {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime timeNow = LocalDateTime.now();
        /**
         * timeNow - represents the Time and Date
         */


        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));

            //add date and time in file
            out.write("\n");
            out.write(String.valueOf(timeNow));
            //add Message in file
            out.write("\n");
            out.write(message);

            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }
}
