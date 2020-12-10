package WebCrawlerMTA.InputData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputData {
    String noThreads;
    String delay;
    String rootDir;
    String logLevel;
    String toSerialize;
    String applicationName;
    String action;
    String attributeCommand1;
    String attributeCommand2;
    ArrayList<String> listOfURLs = new ArrayList<String>();

    public ArrayList<String> GetListOfURLs() {
        return listOfURLs;
    }

    public void SetActionToSend(String action, String attribute1, String attribute2){
        if(attribute2==null)
            toSerialize=attribute1;
        else
            toSerialize=attribute1+" "+attribute2;
    }

    public String GetAction(){
        return toSerialize;
    }

    private String SplitToString(String toSplit){
        String[] splitString=toSplit.split("=");
        toSplit= splitString[1];
        return toSplit;
    }

    public void SetNoThreads(String noThreads){
        this.noThreads = SplitToString(noThreads);
    }

    public void SetLogLevel(String logLevel) {
        this.logLevel = SplitToString(logLevel);
    }

    public void SetDelay(String delay) {
        this.delay = SplitToString(delay);
    }

    public void SetRootDir(String rootDir){
        this.rootDir = SplitToString(rootDir);
    }

    public String GetNoThreads() {
        return noThreads;
    }

    public String GetDelay() {
        return delay;
    }

    public String GetRootDir() {
        return rootDir;
    }

    public String GetLogLevel() {
        return logLevel;
    }

    public String GetReadAruments(){
        String toReturn;
        toReturn=GetNoThreads()+"#"+GetDelay()+"#"+GetRootDir()+"#"+GetLogLevel();
        return toReturn;
    }

    public void SetApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void SetAction(String action) {
        this.action = action;
    }

    public void SetAttributeCommand1(String attributeCommand1) {
        this.attributeCommand1 = attributeCommand1;
    }

    public void SetAttributeCommand2(String attributeCommand2) {
        this.attributeCommand2 = attributeCommand2;
    }

    private void SplitCommand(String command)
    {
        String[] splitString=command.split(" ");
        SetApplicationName(splitString[0]);
        SetAction(splitString[1]);
        SetAttributeCommand1(splitString[2]);
        if(splitString.length==4)
            SetAttributeCommand2(splitString[3]);
        else
            attributeCommand2=null;
    }

    private void ReadFile(String filename){
        try {
            File inputFile = new File(filename);
            Scanner scannerInput = new Scanner(inputFile);
            while (scannerInput.hasNextLine()) {
                noThreads = scannerInput.nextLine();
                delay = scannerInput.nextLine();
                rootDir = scannerInput.nextLine();
                logLevel = scannerInput.nextLine();
            }
            scannerInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu exista");
            e.printStackTrace();
        }
        SetDelay(delay);
        SetNoThreads(noThreads);
        SetRootDir(rootDir);
        SetLogLevel(logLevel);
    }

    void ReadURL(String filename) {
        try {
            File inputFile = new File(filename);
            Scanner scannerInput = new Scanner(inputFile);
            while (scannerInput.hasNext()) {
                listOfURLs.add(scannerInput.next());
            }
            scannerInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu exista");
            e.printStackTrace();
        }


    }

    public String ReadCommands(){

        String line;
        Scanner scannerInput;
        try {
            scannerInput = new Scanner(System.in);
            line=scannerInput.nextLine();
            SplitCommand(line);

            switch (action) {
                case "filter":
                case "search":
                case "sitemap": {
                    SetActionToSend(action, attributeCommand1,attributeCommand2);
                    return GetAction();
                }
                case "read": {
                    ReadFile(attributeCommand1);
                    return GetReadAruments();
                    //return ;
                }
                case "readURL":{
                    ReadURL(attributeCommand1);
                    return GetListOfURLs().toString();
                }
                case "exit":
                    System.out.println("Aplicatia a terminat de rulat!");
                default:
                    break;
            }
            scannerInput.close();
        }
        catch (Exception e) {
            System.err.println("Nu ati introdus nicio comanda!");
            e.printStackTrace();
        }
        return null;
    }
}