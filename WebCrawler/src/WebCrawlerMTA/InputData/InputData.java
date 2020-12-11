package WebCrawlerMTA.InputData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Class implementing SiteMap component
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
    private Integer dimensionToCrawl;
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
            if(result[0].equals("type"))
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
    public String GetCommand(String[] args){
        String command = "";
        int isOkCommand;
        if(args[0].equals("filter")){
            isOkCommand = 1;
            int isOkForFilter = 0;

            if(args.length < 3 || args.length > 4)
            {
                //error
                return null;
            }

            command = command + args[0] + " ";
            if(args[1].equals("size")){
                command = command + args[1] + "#" + args[2] + " " + args[3];
                isOkForFilter = 1;
            }
            if(args[1].equals("type")){
                command = command + args[1] + "#" + args[2];
                isOkForFilter = 1;
                return command;
            }
            if(args[1].equals("date")){
                command = command + args[1] + "#" + args[2];
                isOkForFilter = 1;
                return command;
            }
            if(isOkForFilter == 0)
            {
                //error
                return null;
            }
            else
            {
                return command;
            }
        }
        if(args[0].equals("search"))
        {
            if(args.length == 1)
            {
                //error
                return null;
            }
            isOkCommand = 1;
            command = args[0] + " " + args[1];
            return command;
        }
        if(args[0].equals("crawl"))
        {
            isOkCommand = 1;
            if(args.length < 2)
            {
                //error
                return null;
            }
            if(GetExtension(args[1]).equals("cnf"))
            {
                try {
                    File filename = new File(args[1]);
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
                        if (step == 6) {
                            String[] result = myReader.nextLine().split("=");
                            this.SetPropertyValue(result);
                        }
                        step++;
                    }
                    myReader.close();
                    if(step < 6)
                    {
                        //error
                        return null;
                    }
                    else
                    {
                        return "crawl cnf ok";
                    }
                } catch (FileNotFoundException e) {
                    //System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            }
            if(GetExtension(args[1]).equals("txt")){
                int isOkForTxt = 0;
                try {
                    File filename = new File(args[1]);
                    Scanner myReader = new Scanner(filename);
                    while (myReader.hasNextLine()) {
                        String url = myReader.nextLine();
                        this.urlsList.add(url);
                        isOkForTxt = 1;
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    //System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                if(isOkForTxt == 1)
                {
                    return "crawl txt is ok";
                }
            }
        }
        return null;
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
     *Method getDimensionToCrawl is used to return dimension to crawl
     */
    public Integer getDimensionToCrawl() {
        return dimensionToCrawl;
    }

    /**
     *Method setDimensionToCrawl
     * @param dimensionToCrawl indicates dimension to crawl
     */
    public void setDimensionToCrawl(Integer dimensionToCrawl) {
        this.dimensionToCrawl = dimensionToCrawl;
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
