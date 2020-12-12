package WebCrawlerMTA.Exceptions;

/**
 * @author Seba
 * ExceptionClass defines errors some that can occur
 */

public class ExceptionClass implements Exception {
    /**
     * @param message - the detail message
     */

    String message;

    public ExceptionClass() {
    }

    /**
     * @param type - from ExceptionType - error type that appears
     * print error message
     */
    public void ExceptionMethod(ExceptionType type) {

        switch(type){

            case ArgumentsNotCorrect:   {

                this.message = "Command is incorrect or it was not introduced enough parameters.";
                System.out.println(message);
                break;
            }
            case NoArgumentsGiven:   {

                this.message = "Application has stopped because you did not give a command.";
                System.out.println(message);
                break;
            }
            case CnfNotCrawled:   {

                this.message = "Config file was not set.";
                System.out.println(message);
                break;
            }

            case TxtNotCrawled:   {

                this.message = "Sites were not crawled.";
                System.out.println(message);
                break;
            }
            case CnfFileIncorrect:   {

                this.message = "Config file is incorrect.";
                System.out.println(message);
                break;
            }


        }

    }



}

