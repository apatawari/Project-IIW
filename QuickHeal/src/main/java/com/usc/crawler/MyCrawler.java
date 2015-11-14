package com.usc.crawler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	//created to eliminate Spider Traps
	ArrayList<String> urls=new ArrayList<String>();
	ArrayList<String> referenceKeywords=new ArrayList<String>();
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("http://www.home-remedies-for-you.com/") && href.contains(".htm") && href.contains("/remedy/") && !urls.contains(href);
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);
         
         urls.add(url);
         
         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             List<WebURL> links = htmlParseData.getOutgoingUrls();
             
             System.out.println("Text length: " + text.length());
             System.out.println("Html length: " + html.length());
             System.out.println("Number of outgoing links: " + links.size());
             
             Object[] arrayView = links.toArray();

             for (int i = 0; i < links.size(); i++) {
            	 	 urls.add(arrayView[i].toString());
            	 	try {
            	 		String baseName = FilenameUtils.getBaseName(arrayView[i].toString());
				         String extension = FilenameUtils.getExtension(arrayView[i].toString());
            	 		//System.out.println(arrayView[i].toString());
            	 		if(extension.contains("htm") && arrayView[i].toString().contains("/remedy/"))
            	 		{
				         Document doc = Jsoup.connect(arrayView[i].toString()).get();
						
						//System.out.println(doc.html());

				    	 
						String finalName=baseName+"."+extension;

				    	 referenceKeywords.add(baseName);
						System.out.println(finalName);
					
				         writeToFile(doc.html(),finalName);
            	 		}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

           	 PrintWriter writer;
			try {
				writer = new PrintWriter("HomeRemedies/0Keywords.html", "UTF-8");
			 for(i=0;i<referenceKeywords.size();i++)   	 
            	 writer.println(referenceKeywords.get(i));
                	 writer.close();          
             } catch (FileNotFoundException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}}
             
         }}

     public void writeToFile(String htmlContent, String fileName) throws IOException, URISyntaxException {
    	 
    	 PrintWriter writer = new PrintWriter("HomeRemedies/"+fileName, "UTF-8");
    	 writer.println(htmlContent);
    	 writer.close();

    	
    	}
}