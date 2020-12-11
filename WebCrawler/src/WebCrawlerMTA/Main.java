package WebCrawlerMTA;

import WebCrawlerMTA.Exceptions.Exception;
import WebCrawlerMTA.Exceptions.ExceptionClass;
import WebCrawlerMTA.FileManager.FileManager;
import WebCrawlerMTA.FileManager.FileManagerFactory;
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

import static WebCrawlerMTA.Exceptions.ExceptionType.ArgumentsNotCorrect;
import static WebCrawlerMTA.Exceptions.ExceptionType.NoArgumentsGiven;

public class Main {
    public static void main(String[] args) {

        InputData inputData = new InputData();
        Logger loggerInfo = new Info();
        Logger loggerSevere = new Severe();
        Exception exception = new ExceptionClass();
        FileManagerFactory fileManagerFactory = new FileManagerFactory();

        loggerInfo.LoggerInfo("Application has started successfully.");

        if(args.length == 0)
        {
            loggerSevere.LoggerInfo("Application has stopped because you did not give a command.");
            exception.ExceptionMethod(NoArgumentsGiven);
        }

        inputData.setDelay("100ms");
        inputData.setDepth(3);
        inputData.setDirectoryPath("C:/Users/gabri/Desktop/CrawlerDownload");
        inputData.setThreadsNumber(10);


        //String command = inputData.GetCommand(args);

        //Recursion recursion = Recursion.GetInstance(inputData);
        //Recursion.Run();



        loggerInfo.LoggerInfo("Application has ended successfully.");




    }

}
