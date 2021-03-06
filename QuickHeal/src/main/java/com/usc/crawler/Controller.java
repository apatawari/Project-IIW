package com.usc.crawler;


import java.io.File;

import org.apache.commons.io.FileUtils;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {


	static String line;

    public void startCrawlingHomeRemedies() throws Exception {
    //org.apache.log4j.BasicConfigurator.configure();
    	
    
        String crawlStorageFolder = "data/crawl/root";
        int numberOfCrawlers = 5;
        FileUtils.cleanDirectory(new File("HomeRemedies/")); 
        try
        {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
      config.setMaxPagesToFetch(5);
        /*
         * Instantiate the controller for this crawl.
         */
        
        config.setMaxDepthOfCrawling(2);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
      
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        int politenessDelay=25000;
        config.setPolitenessDelay(politenessDelay);
        
        config.setUserAgentString("CSCIProject");
        System.out.println(config.toString());
        

        
        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */

        controller.addSeed("http://www.home-remedies-for-you.com/");
        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
     // Wait for 30 seconds
        Thread.sleep(60 * 1000);

        // Send the shutdown request and then wait for finishing
        controller.shutdown();
       
      
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
    }
    
}