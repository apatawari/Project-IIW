package com.iiw.QuickHeal;

import com.usc.crawler.Controller;

/**
 * Hello world!
 *
 */
public class QuickHealer 
{
    public static void main( String[] args )
    {
    	Controller crawl=new Controller();
    	try {
			crawl.startCrawlingHomeRemedies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
