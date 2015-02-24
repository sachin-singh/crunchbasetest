package crunchbase;
 
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
 

import crunchbase.CSVWriter;
import crunchbase.Config;

public class Crawler {
	
	
	
	static int limit= 50000000;
	
	static int pause = 5;
	
	static String outputFileName = "crunchbase.csv";
	 
	static Map<String,String> data = new HashMap<String,String>();
	
	static String baseurl = "https://www.crunchbase.com/funding-rounds?page=";
	
	static String[] headers = {"Date", "Company Name"};
	
	static String curentPageSource= "";
	
	static void crawl(){
		
		int pagenum =1;
		try{
			Config.driver.get("https://www.crunchbase.com/funding-rounds");
			while(pagenum<=limit){																				//loop untill max limit is reached
//				
//				String url = baseurl + new Integer(pagenum);													
//				String html = Request.fetch(url);																//fetch html 
//				if(html.length()!=1){
//					 parsePage(html); 																			//parse page for the company name
//					
//				}
				if(pagenum%200==0){
					System.out.println("bakingup...........");
					outputFileName= "crunchbase_"+pagenum+".csv";
					String html = Config.driver.getPageSource();
					if(html.length()!=1){
						 parsePage(html); 																			//parse page for the company name
						
					}
					pagenum++; 
				}
				if(Config.driver.getPageSource().indexOf("Loading")!=-1){
					Thread.sleep(pause*1000);    //Pause for few seconds to avoid ip blocking
				}
				else{
					Config.scrollDown(pagenum%2);
					pagenum++; 
					System.out.print(pagenum+" ");
				}
////				System.out.print("* ");
//				Config.save(pagenum);
				
				
				
			}
			String html = Config.driver.getPageSource();
			if(html.length()!=1){
				 parsePage(html); 																			//parse page for the company name
				
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		
	}
	
	static void parsePage(String html){  
		
			Document doc = Jsoup.parse(html);
			Element listContainer = doc.select("ul.section-list.container").first();
			Elements lis = listContainer.getElementsByTag("li");
			
			for(Element li:lis){
				if(!isDateContainer(li)){															//Check if current element is date identifier								
					
					String companyName = li.getElementsByClass("info-block").first().getElementsByAttribute("title").first().text(); 
					data.put("Company Name", companyName);
					System.out.println("* Company Name: "+data.get("Company Name"));
					CSVWriter.writeCSV(headers, data, outputFileName);												//Write data to CSV
					
				}
			} 
	}
	
	static boolean isDateContainer(Element li){
		try{
			Element h2 = li.getElementsByClass("title_date").first();
			if(h2.text().length()>0){
				data.put("Date",h2.text()); 													//Change Date
				System.out.println("Date: "+h2.text());
			}
			return true;
		}catch(Exception e){ 
		}
		return false;
	}
	
	
	
	public static void main(String[] args){
		if(Config.useBrowser){
			Config.loadDriver();
		}
		crawl();
		
	}
	

}
