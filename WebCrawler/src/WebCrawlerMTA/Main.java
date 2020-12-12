package WebCrawlerMTA;

import WebCrawlerMTA.Exceptions.Exception;
import WebCrawlerMTA.Exceptions.ExceptionClass;
import WebCrawlerMTA.FileManager.FileManager;
import WebCrawlerMTA.FileManager.FileManagerFactory;
import WebCrawlerMTA.FileManager.Filter;
import WebCrawlerMTA.InputData.InputData;
import WebCrawlerMTA.Logger.Info;
import WebCrawlerMTA.Logger.Logger;
import WebCrawlerMTA.Logger.Severe;
import WebCrawlerMTA.Logger.Warn;
import WebCrawlerMTA.Permission.Permission;
import WebCrawlerMTA.Permission.PermissionManager;
import WebCrawlerMTA.Permission.PermissionManagerInterface;
import WebCrawlerMTA.Recursion.Recursion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static WebCrawlerMTA.Exceptions.ExceptionType.*;

public class Main {
    public static void main(String[] args) {

        InputData inputData = new InputData();
        Logger loggerInfo = new Info();
        Logger loggerSevere = new Severe();
        Logger loggerWarn = new Warn();
        Exception exception = new ExceptionClass();
        FileManagerFactory fileManagerFactory = new FileManagerFactory();

        loggerInfo.LoggerInfo("Application has started successfully.");
        inputData.PrintMenu();
        int wasCrawledCnf = 0;
        int wasCrawledTxt = 0;
        while(true)
        {
            String[] arguments = inputData.TakeCommand();
            if(arguments[0].equals(""))
            {
                System.out.println("Please enter your command.");
            }
            else {

                String command = inputData.GetCommand(arguments);
                if(command != null)
                {
                    String[] result = command.split(" ");
                    loggerInfo.LoggerInfo("Application has started executing command:" + command);

                    if (arguments[0].toLowerCase().equals("exit")) {
                        loggerInfo.LoggerInfo("Application has ended successfully.");
                        break;
                    }

                    if (command.equals("crawl cnf ok")) {
                        System.out.println("Starting crawling config file..");
                        loggerInfo.LoggerInfo("Config file has crawled successfully.");
                        System.out.println("Config file crawled.");
                        wasCrawledCnf = 1;
                    }

                    if (wasCrawledCnf == 1 && command.equals("crawl txt ok")) {
                        System.out.println("Starting crawling input file..");
                        Recursion recursion = Recursion.GetInstance(inputData);
                        Recursion.Run();
                        loggerInfo.LoggerInfo("File was crawled successfully.");
                        wasCrawledTxt = 1;
                    }
                    if (wasCrawledCnf == 0 && command.equals("crawl txt ok")){
                        loggerWarn.LoggerInfo("Config file was not crawled.");
                        exception.ExceptionMethod(CnfNotCrawled);
                    }

                    if (arguments[0].equals("filter") && wasCrawledTxt == 1) {
                        if (arguments[1].equals("type")) {
                            System.out.println("Starting filtering by type..");
                            FileManager fileManager = fileManagerFactory.GetAction(arguments[0]);
                            fileManager.DoSpecificWork(inputData.getDirectoryPath(), result[1]);
                        }
                        if (arguments[1].equals("size")) {
                            System.out.println("Starting filtering by size..");
                            FileManager fileManager = fileManagerFactory.GetAction(arguments[0]);
                            fileManager.DoSpecificWork(inputData.getDirectoryPath(), result[1] + " " + result[2]);
                        }
                        if (arguments[1].equals("date")) {
                            System.out.println("Starting filtering by date..");
                            FileManager fileManager = fileManagerFactory.GetAction(arguments[0]);
                            fileManager.DoSpecificWork(inputData.getDirectoryPath(), result[1]);
                        }
                    }
                    if (arguments[0].equals("filter") && wasCrawledTxt == 0) {
                        loggerWarn.LoggerInfo("Input file was not crawled.");
                        exception.ExceptionMethod(TxtNotCrawled);
                    }
                    if (arguments[0].equals("search") && wasCrawledTxt == 1) {
                        System.out.println("Starting searching word..");
                        FileManager fileManager = fileManagerFactory.GetAction(arguments[0]);
                        fileManager.DoSpecificWork(inputData.getDirectoryPath(), result[1]);
                    }
                    if (arguments[0].equals("search") && wasCrawledTxt == 0){
                        loggerWarn.LoggerInfo("Input file was not crawled.");
                        exception.ExceptionMethod(TxtNotCrawled);
                    }
                    if (arguments[0].equals("sitemap") && wasCrawledTxt == 1) {
                        System.out.println("Starting creating sitemap..");
                        FileManager fileManager = fileManagerFactory.GetAction(arguments[0]);
                        fileManager.DoSpecificWork(inputData.getDirectoryPath(), result[1]);
                        System.out.println("Sitemap created.");
                        loggerInfo.LoggerInfo("Sitemap created at "+result[1]);
                    }
                    if (arguments[0].equals("sitemap") && wasCrawledTxt == 0){
                        loggerWarn.LoggerInfo("Input file was not crawled.");
                        exception.ExceptionMethod(TxtNotCrawled);
                    }
                }
            }
            System.out.println("Enter new command");
        }
    }

}
