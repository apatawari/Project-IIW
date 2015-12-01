package com.usc.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * @author Arihant
 *The idea is to extract data and place that in excel file with specific columns 
 *1. Name of the illness
 *2	 
 *3
 * 
 */

public class Wrapper {
	static Document doc;
	private int count=0;
	static PrintWriter writer;

	
	public void extractDataFromHtmlSource1(String folderLocation) throws IOException, URISyntaxException
	{
		//get the html files and apply jsoup parsing to extract specific content
		File folder = new File(folderLocation);
		File[] listOfFiles = folder.listFiles();

		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		       // System.out.println(file.getName());
		    	File input = new File("HomeRemedies/"+file.getName());
		    	 doc = Jsoup.parse(input, "UTF-8");
		    	
		    	//Name of the illness
		    	 String baseName = FilenameUtils.getBaseName(file.getName());
		    	 writer = new PrintWriter("HomeRemedies/text/"+baseName.replace("-", " ")+".txt", "UTF-8");
		 
    	Elements elements = doc.select(".anchoralign");
    	//System.out.println(elements);
    	if(elements.size()>0)
    	{
    		
    		handleQuotedMessages(elements,baseName.replace("-", " "));
    	count++;}
		    }
		    else System.out.println(file.getName());
		    }
		System.out.println(count);
}
	
	private static String handleQuotedMessages(Elements quotedMessages, String filename) throws IOException, URISyntaxException {
		 StringBuilder stringBuilder = new StringBuilder();
        Element firstQuotedMessage = quotedMessages.first();
        List<Node> siblings = firstQuotedMessage.siblingNodes();
        List<Node> elementsBetween = new ArrayList<Node>();
        Element currentQuotedMessage = firstQuotedMessage;
        for (int i = 0; i < siblings.size(); i++) {
            Node sibling = siblings.get(i);

            // see if this Node is a quoted message
            if (!isQuotedMessage(sibling)) {
            	System.out.println(sibling.toString());
            	elementsBetween.add(sibling);
            }	else {
            	
                createQuotePost(currentQuotedMessage, elementsBetween,filename);
                currentQuotedMessage = (Element) sibling;
                elementsBetween.clear();
            }
        }
        if (!elementsBetween.isEmpty()) {
           createQuotePost(currentQuotedMessage, elementsBetween,filename);
            
        }
        writer.close();

		return stringBuilder.toString();
        
     
    }

    private static boolean isQuotedMessage(Node node) {
        if (node instanceof Element) {
            Element el = (Element) node;
            return "div".equals(el.tagName()) && el.hasClass("anchoralign");
        }
        return false;
    }

    private static List<Element> filterElements(String tagName, List<Node> nodes) {
        List<Element> els = new ArrayList<Element>();
        for (Iterator<Node> it = nodes.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n instanceof Element) {
                Element el = (Element) n;
                if (el.tagName().equals(tagName)) {
                    els.add(el);
                }
            }
        }
        return els;
    }

    private static void createQuotePost(Element quote, List<Node> elementsBetween,String filename) {
    
   //System.out.println("createQuotePost: " + quote.getElementsByTag("a").attr("id"));
     // System.out.println("createQuotePost: " + elementsBetween.size()+ "\n\n");
    	//StringBuilder stringBuilder = new StringBuilder();
    	for(int i=0;i<elementsBetween.size();i++) 
    	{
    		if(elementsBetween.get(i).childNodeSize()!=0 )
    		{
    			   		writer.println(elementsBetween.get(i).unwrap().toString());
    		}
    		
    	}
//
//    		{
//    		if(!elementsBetween.get(i).unwrap().toString().equals(null))
//    			System.out.println(elementsBetween.get(i).unwrap());
//		
//    		}
//    	    	}
//      }
//    	if(elementsBetween.size()>3)
//    	{
//   		Document doc = Jsoup.parse(elementsBetween.get(3).toString());
//    	Elements link = doc.getElementsByTag("h2");
//    	if(link.text().length()>0)
//    			{
//       // System.out.println(link.text().substring(link.text().charAt(0), link.text().indexOf(' ')));
//        // handle imgs
//	    String[] words = link.text().split(" ");  
//    	System.out.println(words[0]);
//        Document doc1 =Jsoup.parse(elementsBetween.get(5).toString());       
//        Elements link1 = doc1.getElementsByTag("p");
//        System.out.println(link1.text()+"\n");
//    			}   
//    }	
    		   }
    
    public static void writeToFile(String htmlContent, String fileName) throws IOException, URISyntaxException {
   	 
   	 PrintWriter writer = new PrintWriter("HomeRemedies/text/"+fileName.replace(' ', '-')+".txt", "UTF-8");
   	 writer.println(htmlContent);
   	 writer.close();

   	
   	}
	}

