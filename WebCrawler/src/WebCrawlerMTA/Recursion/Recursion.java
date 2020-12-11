package WebCrawlerMTA.Recursion;


import WebCrawlerMTA.FileManager.Utils;
import WebCrawlerMTA.InputData.InputData;
import WebCrawlerMTA.Permission.PermissionManager;
import WebCrawlerMTA.Permission.PermissionManagerInterface;

import java.awt.desktop.ScreenSleepEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Class implementing the parsing and downloading of all sites
 *
 *
 * @author Gutu Bogdan
 */

public class Recursion {

    /**
     * Class members
     */

    private static Integer threadsNumber;
    private static String delay;
    private static String directoryPath;
    private static Integer depth;
    public static List<String> urlsList;
    private static Recursion recursion_instance = null;
    private static String typeLimit;

    /**
     * Recursion class constructor
     * @param inputData - data that is extracted from the configuration file
     */
    private Recursion(InputData inputData) {
        threadsNumber = inputData.getThreadsNumber();
        delay = inputData.getDelay();
        directoryPath = inputData.getDirectoryPath();
        depth = inputData.getDepth();
        urlsList = inputData.getUrlsList();
        typeLimit= inputData.getTypeToCrawl();
    }


    /**
     * This method reads all the page text
     * @param urlString is the url of the page that will be read
     * @return is the page text
     */

    private static String GetPage(String urlString)
    {
        String result = "";
        try {
            URL url = new URL( urlString);
            URLConnection connection = url.openConnection();
            connection.connect();
            //System.out.println("Internet is connected");

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    result += str;
                }
                in.close();
            }
            catch (MalformedURLException e) {
            }
            catch (IOException e) {
            }
            // System.out.println(result);

        } catch (MalformedURLException e) {
            //System.out.println("Internet is not connected");
           // System.out.println(e.getCause());
            //logger + eroare e.getMessage()
        } catch (IOException e) {
            //System.out.println("Internet is not connected");
            //logger + eroare e.getMessage()

            //System.out.println(e.getCause());
        }
        return result;
    }

    /**
     * This method returns the instance of this Singletone class
     * @param inputData -datas that are extracted from the configuration file
     * @return the instance of this Singleton class
     */

    public static Recursion GetInstance(InputData inputData)
    {
        if (recursion_instance == null)
            recursion_instance = new Recursion(inputData);
        return recursion_instance;
    }

    /**
     * This method starts the parseing of sites on more threads
     */

    public static void Run()
    {
        List<Thread> threads= new ArrayList<>();
        for(int i=0;i<threadsNumber;i++)
        {
            Thread thread= new Thread(new MyThread(i,urlsList.size(),threadsNumber));
            threads.add(thread);
        }
        for(int i=0;i<threadsNumber;i++)
        {
            threads.get(i).start();
            //System.out.println("Am inceput, semnat threadul: "+ i);
        }
    }

    /**
     * This method is used to extract the directory where a object will be
     * @param url is the path to an object
     * @return the path of the directory where the object is found
     */

    private static String GetDirectory(String url)
    {
        String directory = "";
        int nrTokens = 0;
        StringTokenizer tokenizer = new StringTokenizer(url, "//");
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            nrTokens++;
        }
        int pos = 0;
        StringTokenizer tokenizer2 = new StringTokenizer(url, "//");
        while (tokenizer2.hasMoreElements()) {
            String token = tokenizer2.nextToken();

            pos++;
            if(pos != 1) {
                directory = directory + "/" + token;
            }
            if(pos == nrTokens - 1)
            {
                break;
            }
        }
        return directory;
    }

    /**
     * This method is used to verify the type of a file
     * @param url is the path of an object
     * @return the extension of an object
     */
    private static String GetExtension(String url)
    {
        String result = "";
        String finalResult = "";
        for(int i=url.length()-1;i>0;i--)
        {
            if(url.charAt(i)=='.')
            {
                break;
            }
            result += url.charAt(i);
        }
        for(int i=result.length()-1;i>=0;i--)
        {
            finalResult+=result.charAt(i);
        }
        return finalResult;
    }

    /**
     * This method is used to verify the limit of an object
     * @param url is a site
     * @return the size of an object that will be downloaded
     */

    private static int GetFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }


    /**
     * This method is used to download some elements of a site
     * @param url is the url of the site
     */

    private static void DownloadPage(String url)
    {
        Utils utils = new Utils();

        if(!typeLimit.isEmpty())
        {
            if(GetExtension(url).matches(typeLimit)){
                try {
                    BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                    int pos = url.indexOf("/");
                    FileOutputStream fileOutputStream =
                            new FileOutputStream(directoryPath+url.substring(pos+1));
                    System.out.println(directoryPath+url.substring(pos+1));
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    //System.out.println(e.getCause());
                }
            }
        }
        else{
            try {
                BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                int pos = url.indexOf("/");
                FileOutputStream fileOutputStream =
                        new FileOutputStream(directoryPath+url.substring(pos+1));
                System.out.println(directoryPath+url.substring(pos+1));
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                //System.out.println(e.getCause());
            }
        }
    }

    /**
     * This method is used to access all sites of a page
     * @param currentDepth is the currentDepth of the page, it can not be greater the the limit
     * @param positionURL is the position of the element in the urlList
     * @param queueURLs is a List that is storing all the urls of a page
     */
    public static void GoRecursion(Integer currentDepth, Integer positionURL, List<String> queueURLs){
        if(currentDepth > depth)
            return;
        if(!queueURLs.isEmpty()){
            PermissionManagerInterface permissionManagerInterface;
            permissionManagerInterface= new PermissionManager();
            String element = queueURLs.get(0);
            permissionManagerInterface.GetPermissions(element + "/robots.txt");
            String page = GetPage(element);
            int a=0;
            while(a!= -1)
            {
                a=page.indexOf("href=\"",a);
                if(a==-1)
                {
                    break;
                }
                //System.out.print(a);
                //System.out.print("\n");
                String newURL = new String();
                char b=' ';
                a+=6;
                while(true) {
                    b = page.charAt(a);
                    if (b == '\"')
                    {
                        break;
                    }
                    newURL += b;
                    a++;
                }
                if(newURL.indexOf("http")!=-1)
                {
                    if(permissionManagerInterface.AllowedToCrawl(newURL))
                    {
                        int delayToSleep = Integer.parseInt(delay.substring(0,delay.length()-2));
                        if(permissionManagerInterface.GetDelay(newURL) != null)
                        {
                            delayToSleep = permissionManagerInterface.GetDelay(newURL);
                        }
                        try {
                            TimeUnit.SECONDS.sleep(delayToSleep/1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            Path path = Paths.get(directoryPath + GetDirectory(newURL));
                            Files.createDirectories(path);
                            DownloadPage(newURL);

                            //System.out.println("Directory is created!");

                        } catch (IOException e) {

                            //System.err.println("Failed to create directory!" + e.getMessage());

                        }
                    }
                    queueURLs.remove(0);
                    queueURLs.add(newURL);
                    GoRecursion(currentDepth+1,positionURL,queueURLs);
                }
            }

        }
        else{
            if(positionURL!=-1) {
                queueURLs.add(urlsList.get(positionURL));
                //System.out.println(urlsList.get(positionURL));
                GoRecursion(currentDepth + 1, -1, queueURLs);
            }
        }

    }


}

