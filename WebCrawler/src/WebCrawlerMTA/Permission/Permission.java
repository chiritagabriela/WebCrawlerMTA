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
 * Class defining structure of a permission
 * A permission is an object that defines the url from robots.txt, path where crawler is
 * not allowed to crawl.
 *
 * @author Chirita Gabriela
 */
public class Permission{
    /**
     * Member decription
     */
    private String url;
    private Integer delay = 0;


    /**
     * Permission class constructor
     * @param url The url that need to be crawled.
     * @param delay Time that crawler needs to wait before crawling another link.
     */
    public Permission(String url, Integer delay) {
        this.url = url;
        this.delay = delay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
