package crunchbase;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Request {
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36";

	private static final String referrer = "https://www.crunchbase.com/funding-rounds";
	public static String fetch(String url) throws Exception {
		  
		if(Config.useBrowser){
			return makeBrowserRequest(url);
		}
		return makeGetRequest(url);
		
		
		
 
	}
	
	static String makeBrowserRequest(String url) throws Exception{
		Config.driver.get(url);
		return Config.driver.getPageSource();
	}
	
	static String makeGetRequest(String url) throws Exception {
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(url); 
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("Referer", "https://www.crunchbase.com/funding-rounds");
		request.addHeader("Connection", "keep-alive");
		
		
		System.out.println("Loading....  " + url);
		HttpResponse response = client.execute(request);
		int resultCode= response.getStatusLine().getStatusCode();
		
		 
		
		if(resultCode==200){
			BufferedReader rd = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				result.append(line);
			}
			 
			return result.toString();
		}
		else{
			System.out.println("Unexpected Result Code: "+resultCode);
			 
		}
		return "";
	}

}
