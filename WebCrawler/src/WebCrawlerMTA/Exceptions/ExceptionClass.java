package WebCrawlerMTA.Exceptions;

/**
 * @author Seba
 * ExceptionClass defines errors some that can occur
 */

public class ExceptionClass implements Exception {
    /**
     * @param Message - the detail message
     */

    String Message;

    public void ExceptionMethod(ExceptionType type) {
/**
 * @param type - from ExceptionType - error type that appears
 * print error message
 */
        switch(type){

            case FileNotFound:   {

                System.out.println("File not found");
                break;
            }

            case FileNotOpened:    {

                System.out.println("File can not be opened ");
                break;
            }
        }

    }



}

