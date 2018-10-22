package com.scrape;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App
{
	
	public Elements getHrefElements(String url) {
		Elements hrefs = null;
		try {
			Document doc = Jsoup.connect(url).get();
			hrefs = doc.select("div.mw-parser-output").get(0).getElementsByTag("ul").select("[href*=/wiki/Java_Programming]");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return hrefs;
	}
	
	public void scrape(String pageName, int index) {
		String prefix = "https://en.wikibooks.org";
		String content = null;
		String url = prefix + pageName;
		try {
			Document doc = Jsoup.connect(url).get();
			content = doc.body().text();
			try(FileWriter fileWriter = new FileWriter("../scrapedPages/page" + index + ".txt")) {
				fileWriter.write(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void scrapeHrefs(Elements hrefs) {
		String pageName = null;
		for(int i=4; i<hrefs.size()-3; i++) {
			pageName = hrefs.get(i).attributes().get("href");
			scrape(pageName, i);
		}
	}
	
    public static void main( String[] args )
    {
    	App app = new App();
    	Elements hrefs = app.getHrefElements("https://en.wikibooks.org/wiki/Java_Programming");
    	app.scrapeHrefs(hrefs);
    }
}
