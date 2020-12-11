package WebCrawlerMTA.Permission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
/**
 * Class defining the management of permissions
 * Permission manager builds a permission list and can say which link is disallowed from crawling.
 * It also implements methods that are needed to called in PermissionManagerInterface.
 *
 * @author Chirita Gabriela
 */
public class PermissionManager implements PermissionManagerInterface {
    /**
     * Member description
     */
    private List<Permission> permissionsList;

    /**
     * PermissionManager class constructor
     * Initialisation of permission list.
     */
    public PermissionManager() {
        this.permissionsList = new ArrayList<Permission>();
    }

    /**
     * Method IsFound
     * @param charToFind indicates $ or * in order to be able to build a regex.
     * @param url indicates the url from a permission.
     */
    private Boolean IsFound(char charToFind, String url){
        for(int i=0;i<url.length();i++)
        {
            if(url.charAt(i) == charToFind){
                return true;
            }
        }
        return false;
    }
    /**
     * Method GetRegex
     * Implements the conversion of a permission url
     * into a regex expression in order to applied to a regex matcher.
     * @param url indicates the url from a permission.
     */
    private String GetRegex(String url)
    {
        if(IsFound('*',url)){
            int nrTillAsterix = 0;
            for (int i = 0; i < url.length(); i++) {
                if (url.charAt(i) != '*') {
                    nrTillAsterix++;
                }
                else {
                    break;
                }
            }
            String substring1 = url.substring(0, nrTillAsterix);
            String substring2 = url.substring(nrTillAsterix + 1, url.length());
            return ".*" + substring1 + ".*" + substring2;
        }
        return ".*" + url;
    }

    /**
     * Method GetPermissions
     * gets the url that needs to be crawled and puts into permissionList the rules that need
     * to be followed in order to crawl the url.If there is no file robots, the method returns null.
     * Otherwise, it returns the list of permissions for that url.
     * @param urlString indicates the url from a site.
     */
    @Override
    public void GetPermissions(String urlString) {
        this.permissionsList.clear();
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Internet is connected");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                String agent = "";
                Integer delay = 0;
                while ((str = in.readLine()) != null) {
                    StringTokenizer tokenizer = new StringTokenizer(str, " ");
                    int isAgent = 0;
                    int isDisallowed = 0;
                    int isDelay = 0;
                    while (tokenizer.hasMoreElements()) {
                        String token = tokenizer.nextToken();
                        if (token.equals("User-agent:")) {
                            isAgent = 1;
                        }
                        if (!token.equals("User-agent:") && isAgent == 1) {
                            agent = token;
                        }
                        if (token.equals("Crawl-delay:")) {
                            isDelay = 1;
                        }
                        if (!token.equals("Crawl-delay:") && isDelay == 1) {
                            delay = Integer.parseInt(token);
                        }
                        if (token.equals("Disallow:")) {
                            isDisallowed = 1;
                        }
                        if (!token.equals("Disallow:") && isDisallowed == 1 && agent.equals("*")) {
                            Permission permission = new Permission(token, delay);
                            this.permissionsList.add(permission);
                        }
                    }
                }
                in.close();
            }
            catch (MalformedURLException e)
            {
                System.out.println(e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }

        }
        catch (MalformedURLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method AllowedToCrawl
     * Verifies if an url can be crawled based on the permissionsList.
     * @param url indicates the url from a site.
     */
    @Override
    public Boolean AllowedToCrawl(String url) {

        for(int i=0;i<this.permissionsList.size();i++) {
            System.out.println(this.permissionsList.get(i).getUrl());
            if (this.permissionsList.get(i).getUrl().equals("/")) {
                return false;
            }
        }

        for(int i=0;i<this.permissionsList.size();i++) {
            String regexUrl = GetRegex(this.permissionsList.get(i).getUrl());
            if(url.matches(regexUrl)){
                return false;
            }
        }
        return true;
    }
}
