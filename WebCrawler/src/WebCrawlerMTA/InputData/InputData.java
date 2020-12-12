package WebCrawlerMTA.InputData;

import WebCrawlerMTA.Exceptions.Exception;
import WebCrawlerMTA.Exceptions.ExceptionClass;
import WebCrawlerMTA.Exceptions.ExceptionType;
import WebCrawlerMTA.Logger.Config;
import WebCrawlerMTA.Logger.Logger;
import WebCrawlerMTA.Logger.Severe;
import WebCrawlerMTA.Logger.Warn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Class implementing InputData component
 *
 * @author Diana Merches
 */

public class InputData {
    /**
     * Class members
     */
    private Integer threadsNumber;
    private String delay;
    private String directoryPath;
    private Integer logLevel;
    private List<String> urlsList;
    private String typeToCrawl;
    private Integer depth;

    /**
     * Method InputData
     * InputData class constructor
     */
    public InputData() {
        this.urlsList = new ArrayList<>();
    }

    /**
     * Method GetExtension
     * @param filename indicates the name of the verified file
     */
    private String GetExtension(String filename)
    {
        if (filename.endsWith("."+"cnf")) {
            return "cnf";
        }
        if (filename.endsWith("."+"txt")) {
            return "txt";
        }
        return null;
    }

    /**
     *Method SetPropertyValue is used to set the properties of members class
     * @param result represents the string wich will be verified
     */
    private void SetPropertyValue(String[] result){
        for (int x=0; x<result.length; x++) {
            if(result[0].equals("delay"))
            {
                this.delay = result[1];
                break;
            }
            if(result[0].equals("n_threads"))
            {
                this.threadsNumber = Integer.parseInt(result[1]);
                break;
            }
            if(result[0].equals("root_dir"))
            {
                this.directoryPath = result[1];
                break;
            }
            if(result[0].equals("log_level"))
            {
                this.logLevel = Integer.parseInt(result[1]);
                break;
            }
            if(result[0].equals("type_to_crawl"))
            {
                this.typeToCrawl = result[1];
                break;
            }
            if(result[0].equals("depth"))
            {
                this.depth = Integer.parseInt(result[1]);
                break;
            }
        }
    }

    /**
     *Method GetCommand is used to get commands line and compose the string wich will be serialized
     */
    public String GetCommand(String[] args) {
        String command = "";
        Exception exception = new ExceptionClass();
        Logger loggerWarn = new Warn();
        Logger loggerSevere = new Severe();
        Logger loggerConfig = new Config();

        if (args[0].toLowerCase().equals("filter")) {
            int isOkForFilter = 0;

            if (args.length < 3 || args.length > 4) {
                exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
                loggerWarn.LoggerInfo("Arguments not correct.");
                return null;
            }
            command = command + args[0].toLowerCase() + " ";
            if (args[1].toLowerCase().equals("size")) {
                command = command + args[1].toLowerCase() + "#" + args[2].toLowerCase() + " " + args[3].toLowerCase();
                isOkForFilter = 1;
            }
            if (args[1].toLowerCase().equals("type")) {
                command = command + args[1].toLowerCase() + "#" + args[2].toLowerCase();
                isOkForFilter = 1;
            }
            if (args[1].toLowerCase().equals("date")) {
                command = command + args[1].toLowerCase() + "#" + args[2].toLowerCase();
                isOkForFilter = 1;
            }
            if (isOkForFilter == 0) {
                exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
                loggerWarn.LoggerInfo("Arguments not correct.");
                return null;
            } else {
                return command;
            }
        }
        if (args[0].toLowerCase().equals("search")) {
            if (args.length == 1) {
                exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
                loggerWarn.LoggerInfo("Arguments not correct.");
                return null;
            }
            command = args[0].toLowerCase() + " " + args[1].toLowerCase();
            return command;
        }
        if (args[0].toLowerCase().equals("crawl")) {

            if (args.length < 2) {
                exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
                loggerWarn.LoggerInfo("Arguments not correct.");
                return null;
            }
            if (GetExtension(args[1].toLowerCase()).equals("cnf")) {
                try {
                    File filename = new File(args[1].toLowerCase());
                    Scanner myReader = new Scanner(filename);
                    int step = 0;
                    while (myReader.hasNextLine()) {
                        if (step == 0) {
                            String[] result = myReader.nextLine().split("=");
                            this.SetPropertyValue(result);
                        }
                        if (step == 1) {
                            String[] result = myReader.nextLine().split("=");
                            this.SetPropertyValue(result);
                        }
                        if (step == 2) {
                            String[] result = myReader.nextLine().split("=");
                            this.SetPropertyValue(result);

                        }
                        if (step == 3) {
                            String[] result = myReader.nextLine().split("=");
                            this.SetPropertyValue(result);

                        }
                        if (step == 4) {
                            String[] result = myReader.nextLine().split("=");
                            this.SetPropertyValue(result);
                        }
                        if (step == 5) {
                            String[] result = myReader.nextLine().split("=");
                            this.SetPropertyValue(result);
                        }
                        step++;
                    }
                    myReader.close();
                    if (step < 4) {
                        exception.ExceptionMethod(ExceptionType.CnfFileIncorrect);
                        loggerConfig.LoggerInfo("Config file is incorrect.");
                        return null;
                    } else {
                        return "crawl cnf ok";
                    }
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    loggerSevere.LoggerInfo(e.getMessage());
                }

            }
            if (GetExtension(args[1].toLowerCase()).equals("txt")) {
                int isOkForTxt = 0;
                try {
                    File filename = new File(args[1].toLowerCase());
                    Scanner myReader = new Scanner(filename);
                    while (myReader.hasNextLine()) {
                        String url = myReader.nextLine();
                        this.urlsList.add(url);
                        isOkForTxt = 1;
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    loggerSevere.LoggerInfo(e.getMessage());
                }
                if (isOkForTxt == 1) {
                    return "crawl txt ok";
                }
                else{
                    exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
                    loggerWarn.LoggerInfo("Arguments not correct.");
                    return null;
                }
            }
        }
        if (args[0].toLowerCase().matches("search")) {
            if (args[1].isEmpty()) {
                exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
                loggerWarn.LoggerInfo("Arguments not correct.");
                return null;
            } else {
                return args[0].toLowerCase() + " " + args[1].toLowerCase();
            }
        }
        if (args[0].toLowerCase().matches("sitemap")) {
            if (args[1].isEmpty()) {
                exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
                loggerWarn.LoggerInfo("Arguments not correct.");
                return null;
            } else {
                return args[0].toLowerCase() + " " + args[1].toLowerCase();
            }
        }
        exception.ExceptionMethod(ExceptionType.ArgumentsNotCorrect);
        loggerWarn.LoggerInfo("Arguments not correct.");
        return null;
    }

    /**
     *Method PrintMenu is used to print the menu
     */
    public void PrintMenu(){
        System.out.println("WELCOME TO WEBCRAWLER-MTA");
        System.out.println("In order to use the application you have the following possibilities:");
        System.out.println("[1]-->Crawling configuration file:crawl config.cnf");
        System.out.println("[2]-->Crawling sites from a given input file:crawl input.txt");
        System.out.println("[3]-->Crawling sites from a given input file with a specified type:crawl input.txt type png");
        System.out.println("[4]-->Filter downloaded sites by type:filter type png");
        System.out.println("[5]-->Filter downloaded sites by date:filter date zz/mm/yyyy");
        System.out.println("[6]-->Filter downloaded sites by dimension:filter size 150 kb");
        System.out.println("[7]-->Create sitemap in an output file:sitemap sitemap.txt");
        System.out.println("[8]-->Search word in all the downloaded files:search word");
        System.out.println("[9]-->Exit:exit");
    }

    public String[] TakeCommand()
    {
        Scanner console = new Scanner(System.in);
        String command = console.nextLine();
        String[] result = command.split(" ");
        return result;
    }
    /**
     *Method getThreadsNumber is used to return number of threads
     */
    public Integer getThreadsNumber() {
        return threadsNumber;
    }

    /**
     *Method setThreadsNumber
     * @param threadsNumber indicates number of threads
     */
    public void setThreadsNumber(Integer threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    /**
     *Method getDelay is used to return delay
     */
    public String getDelay() {
        return delay;
    }

    /**
     *Method setDelay
     * @param delay indicates delay
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     *Method getDirectoryPath is used to return the path of a directory
     */
    public String getDirectoryPath() {
        return directoryPath;
    }

    /**
     *Method setDirectoryPath
     * @param directoryPath indicates the path of directory
     */
    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     *Method getLogLevel is used to return the log level parameter
     */
    public Integer getLogLevel() {
        return logLevel;
    }

    /**
     *Method setLogLevel
     * @param logLevel indicates the log level
     */
    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }

    /**
     *Method getUrlsList is used to return list of URLs
     */
    public List<String> getUrlsList() {
        return urlsList;
    }


    /**
     *Method setUrlsList
     * @param urlsList indicates the list of URLs
     */
    public void setUrlsList(List<String> urlsList) {
        this.urlsList = urlsList;
    }

    /**
     *Method getTypeToCrawl is used to return type to crawl
     */
    public String getTypeToCrawl() {
        return typeToCrawl;
    }

    /**
     *Method setTypeToCrawl
     * @param typeToCrawl indicates type for crawl
     */
    public void setTypeToCrawl(String typeToCrawl) {
        this.typeToCrawl = typeToCrawl;
    }

    /**
     *Method getDepth is used to return depth of crawler
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     *Method setDepth
     * @param depth sets depth for crawler
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
