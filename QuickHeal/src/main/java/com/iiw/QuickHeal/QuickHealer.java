package com.iiw.QuickHeal;

import com.usc.crawler.Controller;
import com.usc.wrapper.Wrapper;

/**
 * @author Arihant
 *
 */
public class QuickHealer 
{
	private static String folderLocationHR="HomeRemedies";
    public static void main( String[] args ) throws Exception
    {
    	//Crawls the links from the HomeRemedies (Source 1)
    	//Outputs html files available in HomeRemedies folder
    	//with list of illness in keywords.txt in homeremedies folder
    	
    	//Controller crawl=new Controller();
    	//crawl.startCrawlingHomeRemedies();
    	
    	//It is wrapper to extract required content from the html files extracted above
    	//generates the excel file in the 
    	Wrapper wrapper=new Wrapper();
    	wrapper.extractDataFromHtmlSource1(folderLocationHR);
    	
    	
    }
}
