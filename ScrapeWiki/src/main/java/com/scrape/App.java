package com.scrape;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App
{
	
	public int count;
	public String baseUrl;
	public Map<String, Boolean> urlMap;
	
	public App() {
		count = 0;
		baseUrl = "https://docs.oracle.com/javase/tutorial/";
		urlMap = new HashMap<String, Boolean>();
	}
	
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
			Elements pElems = doc.body().getElementsByTag("p");
			Elements preElems = doc.getElementsByTag("pre");
			for (int i = 0; i < pElems.size(); i++) {
				content = pElems.get(i).text().trim();
				if(content.equals("")) {
					continue;
				}
				try(FileWriter fileWriter = new FileWriter("../crawledWikiContent/page" + index + "content" + i + ".txt")) {
					fileWriter.write(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < preElems.size(); i++) {
				content = preElems.get(i).text().trim();
				if(content.equals("")) {
					continue;
				}
				try(FileWriter fileWriter = new FileWriter("../crawledWikiContent/page" + index + "contentCode" + i + ".txt")) {
					fileWriter.write(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
	
	public void scrapeWiki(String url) {
		Elements hrefs = this.getHrefElements(url);
		this.scrapeHrefs(hrefs);
		
	}
	
	public void scrapeOracleLevel2(String url) {
		Elements hrefs = null;
		String content = null;
		try {
			Document doc = Jsoup.connect(url).get();
			//hrefs = doc.select("div.mw-parser-output").get(0).getElementsByTag("ul").select("[href*=/wiki/Java_Programming]");
			hrefs = doc.select("#PageContent");
			if(hrefs.size() > 0) {
				hrefs = hrefs.get(0).getElementsByTag("a");
			}
			Elements pElems = doc.body().getElementsByTag("p");
			Elements preElems = doc.getElementsByTag("pre");
			for (int i = 1; i < pElems.size()-2; i++) {
				this.count++;
				content = pElems.get(i).text().trim();
				if(content.equals("") || content.length() < 100) {
					continue;
				}
				try(FileWriter fileWriter = new FileWriter("../crawledWikiContent/oracle" + this.count + "content.txt")) {
					fileWriter.write(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < preElems.size(); i++) {
				this.count++;
				content = preElems.get(i).text().trim();
				if(content.equals("") || content.length() < 100) {
					continue;
				}
				try(FileWriter fileWriter = new FileWriter("../crawledWikiContent/oracle" + this.count + "contentCode.txt")) {
					fileWriter.write(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return;
		}
		
	}
	
	public void scrapeOracleLevel1(String url) {
		Elements hrefs = null;
		try {
			Document doc = Jsoup.connect(url).get();
			//hrefs = doc.select("div.mw-parser-output").get(0).getElementsByTag("ul").select("[href*=/wiki/Java_Programming]");
			hrefs = doc.select("#PageContent").get(0).getElementsByTag("a");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		for(Element href: hrefs) {
			String pageName = href.attributes().get("href");
			if(pageName.equals("") || pageName.contains("http") || pageName.contains("#")) {
				continue;
			}
			String[] urlComps = url.split("/");
			int len = urlComps[urlComps.length-1].length();
			String url1 = url.substring(0, url.length()-len) + pageName;
			if(this.urlMap.containsKey(url1)) {
				continue;
			} else {
				this.urlMap.put(url1, true);
			}
			this.scrapeOracleLevel2(url1);
		}
	}
	
	public void scrapeOracle(String url) {
		Elements hrefs = null;
		try {
			Document doc = Jsoup.connect(url).get();
			//hrefs = doc.select("div.mw-parser-output").get(0).getElementsByTag("ul").select("[href*=/wiki/Java_Programming]");
			hrefs = doc.select("#TutBody").get(0).getElementsByTag("a");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Element href: hrefs) {
			String pageName = href.attributes().get("href");
			if(pageName.equals("") || pageName.contains("http") || pageName.contains("#")) {
				continue;
			}
			String url1 = this.baseUrl + pageName;
			this.scrapeOracleLevel1(url1);
		}
	}
	
    public static void main( String[] args )
    {
    	App app = new App();
    	app.scrapeWiki("https://en.wikibooks.org/wiki/Java_Programming");
    	app.scrapeOracle("https://docs.oracle.com/javase/tutorial/");
    }
}