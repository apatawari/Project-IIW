package com.usc.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	private int count=0;
	public void extractDataFromHtmlSource1(String folderLocation) throws IOException
	{
		//get the html files and apply jsoup parsing to extract specific content
		File folder = new File(folderLocation);
		File[] listOfFiles = folder.listFiles();

		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		       // System.out.println(file.getName());
		    	File input = new File("HomeRemedies/"+file.getName());
		    	Document doc = Jsoup.parse(input, "UTF-8");
		    	
		    	//Name of the illness
		    	System.out.println(doc.getElementsByTag("h1").text());
		    	
		 
    	Elements elements = doc.select(".anchoralign");
    	//System.out.println(elements);
    	if(elements.size()>0)
    	{
    		handleQuotedMessages(elements);
    	count++;}
		    }
		    else System.out.println(file.getName());
		    }
		System.out.println(count);
}
	
	private static void handleQuotedMessages(Elements quotedMessages) {
		
        Element firstQuotedMessage = quotedMessages.first();
        List<Node> siblings = firstQuotedMessage.siblingNodes();
        List<Node> elementsBetween = new ArrayList<Node>();
        Element currentQuotedMessage = firstQuotedMessage;
        for (int i = 1; i < siblings.size(); i++) {
            Node sibling = siblings.get(i);

            // see if this Node is a quoted message
            if (!isQuotedMessage(sibling)) {
            	if(sibling.nodeName()=="aside" || sibling.childNodeSize()>1)
            	{
            		if(sibling.nodeName()=="div" || sibling.nodeName()=="aside" || sibling.nodeName()=="section")
            		{
            			
            		}
            		else
            		{
            		
            			elementsBetween.add(sibling);
            		//	System.out.println(sibling.toString());	
            		}
            	
            	}
            	else 
            	{
            		
            		elementsBetween.add(sibling);
            	//System.out.println(sibling.toString());
            	} 
            }	else {
            	
                createQuotePost(currentQuotedMessage, elementsBetween);
                currentQuotedMessage = (Element) sibling;
                elementsBetween.clear();
            }
        }
        if (!elementsBetween.isEmpty()) {
            createQuotePost(currentQuotedMessage, elementsBetween);
        }
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

    private static void createQuotePost(Element quote, List<Node> elementsBetween) {
       // System.out.println("createQuotePost: " + quote);
       System.out.println("createQuotePost: " + elementsBetween.size()+ "\n\n");
//    	
//    	Document doc = Jsoup.parse(elementsBetween.get(3).toString());
//    	Elements link = doc.getElementsByTag("h2");
//
//    	if(link.text().length()>0)
//    			{
//       // System.out.println(link.text().substring(link.text().charAt(0), link.text().indexOf(' ')));
//        // handle imgs
//    		String[] words = link.text().split(" ");  
//    		System.out.println(words[0]);
//        Document doc1 =Jsoup.parse(elementsBetween.get(5).toString());
//        Elements link1 = doc1.getElementsByTag("p");
//        System.out.println(link1.text()+"\n");
//    			}   
    }
	}

