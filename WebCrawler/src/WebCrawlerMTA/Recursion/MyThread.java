package WebCrawlerMTA.Recursion;

import WebCrawlerMTA.Logger.Info;
import WebCrawlerMTA.Logger.Logger;
import WebCrawlerMTA.Logger.Severe;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing an extension for threads
 *
 *
 * @author Gutu Bogdan
 */

public class MyThread implements Runnable{
    int id;
    int numberMax;
    int noThread;
    List<String> list;


    /**
     * This is the constructor
     * @param idul is the thread id
     * @param Nul is the size of the list that will be paralelized
     * @param Pul is the number of threads
     */
    public MyThread(int idul, int Nul, int Pul)
    {
        id=idul;
        numberMax=Nul;
        noThread=Pul;
        list= new ArrayList<>();
    }

    /**
     * This is the function that will divide all the size of the list that will be used and will assign them to different threads
     */
    public void run()
    {
        try
        {
            int start = Math.round(id * numberMax/noThread);
            int end = Math.round((id+1) * numberMax/noThread) ;
            for(int i=start;i<end;i++)
            {
                Recursion.GoRecursion(0, i, list);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Logger logger = new Severe();
            logger.LoggerInfo(e.getMessage());
        }
    }
}
