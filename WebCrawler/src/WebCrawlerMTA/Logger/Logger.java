package WebCrawlerMTA.Logger;
/**
 * @author Seba
 * Logger interface with methods
 */
public interface Logger {
    /**
     * interface methods
     * @param fileName represents the file name where you want to write
     * @param Message  represents the message that you want to write in file.
     */
    public  void LoggerInfo(String fileName, String Message);

}
