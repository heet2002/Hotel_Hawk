package crawlers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;



	

public class Make_My_Trip {

	
		// TODO Auto-generated method stub

	    public HashMap<String, ArrayList<String>> data = new HashMap<>();

	    public static Hashtable<String,Hashtable> extractCities(String url, String[] cities) {
	    	ArrayList<String> links = new ArrayList<String>();
	        ArrayList<String> title = new ArrayList<String>();
	        ArrayList<String> price = new ArrayList<String>();
	        
	        Hashtable<String,Hashtable> hs = new Hashtable<>();

	        for (String city : cities) {
	        	// Create a new instance of the Chrome driver
	            WebDriver driver = new ChromeDriver();
	            try {
	                // Open a website using Selenium
	            	driver.get("https://www.makemytrip.com/hotels/hotel-listing/?checkin=03242024&city=CT" +city.substring(0, 5).toUpperCase()+ "&checkout=03292024&roomStayQualifier=2e0e&locusId=CT"+city.substring(0, 5).toUpperCase()+"&country=CAN&locusType=city&searchText="+city+"&regionNearByExp=3&rsc=1e2e0e");
	            	
	            	// wait for 5 seconds for the page to load
	            	//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	            	
	            	// Maximize the window
	                driver.manage().window().maximize();
	            	for(int i=0;i<5; i++) {  	 	
	            		JavascriptExecutor js = (JavascriptExecutor) driver;
	                    // First scroll down , then scroll up a little and then scroll down to trigger loading of more jobs
	                    js.executeScript("window.scrollBy(0,5000)");
	                    js.executeScript("window.scrollBy(0,-2000)");
	                    js.executeScript("window.scrollBy(0,5000)");
	                    // Wait for the jobs to load
	                    Thread.sleep(5000);
	            	}
	            	
	                // Get the HTML content of the webpage using Selenium
	                String html = driver.getPageSource();
	                
	             // Parse the HTML using Jsoup
	                Document document = Jsoup.parse(html);
	                
	              //  System.out.println("document:" + document);
	                
	    		    Elements e= document.getElementsByAttributeValue("itemtype", "http://schema.org/Hotel");
	   		    	Hashtable<String,hotels> hb = new Hashtable<String,hotels>();//title hotel details

	   		    for(Element l:e){
	   		    	// Getting links for the hotel
	   		    	
	   		    	String str1 = l.firstChild().attr("href");
	   		        if (!links.contains(str1)){
	   		        	links.add(str1);
	   		           // System.out.println(str1);
	   		        }
	   		        // Getting name of the hotel
	   		        Elements ele2 = l.getElementsByClass("wordBreak appendRight10");
	   		        String str2 = ele2.text();
	   		        if (!title.contains(str2)){
	   		        	title.add(str2);
	   		           // System.out.println(str2);
	   		        }
	   		        // Getting final price of the hotel
	   		        Element ele3 = l.getElementById("hlistpg_hotel_shown_price");
			        String str3 = ele3.text();
			        if (!price.contains(str3)){
			        	price.add(str3);
			           // System.out.println(str3);
			        }
			        
			        hb.put(str2,new hotels(str3,str1));
	   		    }
	   		    // Closing the driver
	   		    hs.put(city,hb);
	                driver.quit();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
//	        System.out.println("links:" + links);
//	        System.out.println("title:" + title);
//	        System.out.println("price:" + price);
	        return hs;
	    }

	    public static void main(String[] args) {
	        String url = "";
	        String[] cities = new String[] {
	        		"Toronto",
	        		//"Windsor"
	        		};

	        Hashtable<String,Hashtable> js = extractCities(url, cities);
	        System.out.println("here is the size of the following "+js.size());
	        
	        System.out.println("\n\n\n");

	        
	        Enumeration<String> e = js.keys();
	        
	        // Iterating through the Hashtable
	        // object
	 
	        // Checking for next element in Hashtable object
	        // with the help of hasMoreElements() method
	        while (e.hasMoreElements()) {
	 
	            // Getting the key of a particular entry
	            String key = e.nextElement();
	            
	            System.out.println("all the hotels of the "+key+" are the following");
	            
	            Hashtable<String,hotels> jj = js.get(key);
	            
	            Enumeration<String> yy = jj.keys();
	            System.out.println("there are "+jj.size()+ " hotels in "+key+"");
	            int i=0;
	            while(yy.hasMoreElements())
	            {
	            	String key1 = yy.nextElement();
	            	
	            	System.out.println("\n\n"+i+" hotel name : "+key1);
	            	hotels alldetails = jj.get(key1);
	            	
	            	System.out.println("prize :"+alldetails.price);
	            	System.out.println("url :"+alldetails.url);
	            	
	            	i++;
	            	
	            	
	            }
	 
	            // Print and display the Rank and Name
	            
	            System.out.println("enter the number of the hotel you want to crawl ");
	            
	        }
	    }
	    
	  
}

	 class hotels
	 {  
		  String price;
		  String url;
		 
		 hotels(String price, String url)
		 {
		     this.price=price;
		     this.url=url;
		 }
	 }


	



