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

public class Warn implements Logger {

    /**
     * Implementing Logger interface methods
     *
     * @param message  represents the message that you want to write in file.
     */

    public  void LoggerInfo(String message)
    {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime timeNow = LocalDateTime.now();
        /**
         * timeNow - represents the Time and Date
         */


        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(new FileWriter("resources/logger.txt", true));

            //add date and time in file
            out.write(String.valueOf(timeNow));
            out.write(" ");
            out.write("[WARN]:");
            //add Message in file
            out.write(message);
            out.write("\n");

            out.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
