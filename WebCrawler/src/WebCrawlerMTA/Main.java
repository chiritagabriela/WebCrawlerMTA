package WebCrawlerMTA;

import WebCrawlerMTA.InputData.InputData;
import WebCrawlerMTA.Permission.Permission;
import WebCrawlerMTA.Permission.PermissionManager;
import WebCrawlerMTA.Permission.PermissionManagerInterface;
import WebCrawlerMTA.Recursion.Recursion;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        InputData inputData= new InputData();
        Recursion rec = Recursion.GetInstance(inputData);
    }

}
