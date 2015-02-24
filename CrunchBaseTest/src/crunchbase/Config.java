package crunchbase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Config {
	
	static boolean useBrowser = true;
	static WebDriver driver;
	
	//read config to get pagenumber searched so far
	static int readConfig(){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("config.properties"); 
			prop.load(input);
			int page = new Integer(prop.getProperty("pagenum"))+1;
			useBrowser = new Boolean(prop.getProperty("browser"));
			System.out.println("Resuming from Page Num: "+page);
			System.out.println("Using Browser: "+useBrowser);
			return page; 
	 
		} catch (IOException ex) { 
			return 1;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//write pagenum just searched to config
	static void save(int i){
		Properties prop = new Properties();

    	try { 
    		prop.setProperty("pagenum", new Integer(i).toString());  
    		prop.setProperty("browser", Boolean.toString(useBrowser)); 
    		prop.store(new FileOutputStream("config.properties"), null);

    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}

	
	static void scrollDown(int i){
//		if(driver.getPageSource().contains("Loading")){
//			return ;
//		}
//		if(i!=1){
//			i=randInt(0,2);
//		}
		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		jse.executeScript("window.scrollTo(0,document.body.scrollHeight/"+(i+1)+");");
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight);");
	}
	
	public static int randInt(int min, int max) { 
	    Random rand = new Random();
 
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
static void loadDriver(){
		
		try{
			driver.close();
		}catch(Exception e){
		} 
		
		try{
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
//			driver= new ChromeDriver(); 
			driver = new FirefoxDriver();
		}catch(Exception e){e.printStackTrace();} 
	}
}
