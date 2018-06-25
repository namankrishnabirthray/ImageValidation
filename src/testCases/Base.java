package testCases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.codehaus.jackson.JsonParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;

import libraries.ExtentManager;
import libraries.Utility;
//import net.bytebuddy.description.modifier.SynchronizationState;


public class Base 
{
	 
      static WebDriver driver=null;
      public static ExtentReports extent; 
     public static ExtentHtmlReporter htmlReporter;
	  public static ExtentTest logger;
	    public static  Log logger1;
	  public static ExtentXReporter extentx;
	  public  static List<WebElement> images=null;
	  public static List<String>         img1=new ArrayList<>();
	  public static List<String>         img2=new ArrayList<>();
	 
	
	@BeforeSuite
	public void preRequisite()
	{          
		extent=ExtentManager.Instance();
		
		System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
	
		
	}
	@BeforeMethod
	public void setup()
	{
		
			
	}
	
		
	protected void validateTest(String URL) 
	{
		 setupDriver( URL);
		 logger= extent.createTest(URL);
		        
		      
		 
		images=driver.findElements(By.tagName("img"));
		 for(int k=0;k<=images.size()-1;k++) {
		  String  a=images.get(k).getAttribute("src");
		         if(a!=null) {img1.add(a);}
		        
		           
		 
		            
		           
		 }
		 
		 for(int i=0;i<=images.size()-1;i++) {
			 
			 String  b=images.get(i).getAttribute("data-src");
	            if(b!=null){ img2.add(b);}
		 }
		 
		 for(int i=0;i<=img1.size()-1;i++) {
		 System.out.println(img1.get(i));
		 String imgURL=img1.get(i);
			
		 
		 
		 
			
			 if(imgURL!=null) {
				 try {
						driver.navigate().to(imgURL);
					    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					   	Thread.sleep(3000);
					   /* logger.pass(img1.get(i),MediaEntityBuilder.createScreenCaptureFromPath(captureScreen()).build());*/
						 }
						 catch(Exception e) {}
						 
					      

			 int u=0;	 
			 String s=null;
			 
		
			
			 try {
				 URL uri1 = new URL( imgURL);
				 String nullFragment = null;
				 URI uri = new URI(uri1.getProtocol(), uri1.getHost(), uri1.getPath(), uri1.getQuery(), nullFragment);                
				 String request = uri.toASCIIString();
				 System.out.println(request);
				 HttpClient client = HttpClientBuilder.create().build();           
				 HttpGet	  request1= new HttpGet(request);
				 HttpResponse response = client.execute(request1);
				 System.out.println( );
				 System.out.println(response.getStatusLine().getProtocolVersion());
				 System.out.println(response.getStatusLine());
				 String resCode=response.getStatusLine().toString();
				 String resVersion =response.getStatusLine().getProtocolVersion().toString();
		        
		         
		         
		         
		               u=response.getStatusLine().getStatusCode();
		               if(u<400) {
		               System.out.println(u);	   
		               logger.pass(imgURL,MediaEntityBuilder.createScreenCaptureFromPath(captureScreen()).build());
		               }else if(u>=400) {
		               logger.fail(imgURL,MediaEntityBuilder.createScreenCaptureFromPath(captureScreen()).build());
		               }else if(u==0){
		            	  System.out.println("check_by_PageTitle"); 
		               }
		               Integer str=new Integer(u);
		               s=str.toString();
		               System.out.println(s);
		               if(!resVersion.contains("HTTP/1.1")) {
		               System.out.println("check Page Title"); }
			           } 
			          catch(MalformedURLException e3) {e3.printStackTrace(); }
			          catch (URISyntaxException e) {}
			          catch (ClientProtocolException e1) {e1.printStackTrace();}
			          catch (IOException e1) {e1.printStackTrace();}
			          catch(java.lang.IllegalArgumentException e1) {
				       e1.printStackTrace(); }
			         
			          
			 
			                               
			                          }
			 
			 
			/* try {
			// driver.navigate().to(img1.get(i));
			// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		   	Thread.sleep(3000);
		   //logger.pass(img1.get(i),MediaEntityBuilder.createScreenCaptureFromPath(captureScreen()).build());
			 }
			 catch(Exception e) {}*/
			 
		      }
		if( img2.size()!=0) {
			
			for(int i=0;i<=img2.size()-1;i++) {
				           String imgURL=null;
				           String oracle="https://www.oracle.com";
				           imgURL=oracle+img2.get(i);
				           

							 if(imgURL!=null) {
								 try {
										driver.navigate().to(imgURL);
									    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
									   	Thread.sleep(3000);
									  /*  logger.pass(imgURL,MediaEntityBuilder.createScreenCaptureFromPath(captureScreen()).build());*/}    
										 
								   catch(Exception e) {}
										 
									      

							 int u=0;	 
							 String s=null;
							 
						
							
							 try {
								 
								 URL uri1=null;
								 URI uri;
								 String request=null;
								 try {
								  uri1 = new URL( imgURL);
								  String nullFragment = null;
								  uri = new URI(uri1.getProtocol(), uri1.getHost(), uri1.getPath(), uri1.getQuery(), nullFragment); 
								  request = uri.toASCIIString();
								    }
			
								   catch (MalformedURLException e) {
								      System.out.println("URL " + imgURL + " is a malformed URL");
								    } catch (URISyntaxException e) {
								      System.out.println("URI " + imgURL + " is a malformed URL");
								    }
								  
								  
								 
								  System.out.println(request);
								 HttpClient client = HttpClientBuilder.create().build();           
								 HttpGet	  request1= new HttpGet(request);
								 HttpResponse response = client.execute(request1);
								 System.out.println( );
								 System.out.println(response.getStatusLine().getProtocolVersion());
								 System.out.println(response.getStatusLine());
								 String resCode=response.getStatusLine().toString();
								 String resVersion =response.getStatusLine().getProtocolVersion().toString();
						        
						         
						         
						         
						               u=response.getStatusLine().getStatusCode();
						               if(u<400) {
						               System.out.println(u);	   
						               logger.pass(imgURL,MediaEntityBuilder.createScreenCaptureFromPath(captureScreen()).build());
						               }else if(u>=400) {
						               logger.fail(imgURL,MediaEntityBuilder.createScreenCaptureFromPath(captureScreen()).build());
						               }else if(u==0){
						            	  System.out.println("check_by_PageTitle"); 
						               }
						               Integer str=new Integer(u);
						               s=str.toString();
						               System.out.println(s);
						               if(!resVersion.contains("HTTP/1.1")) {
						               System.out.println("check Page Title"); }
							           } 
							          catch(MalformedURLException e3) {e3.printStackTrace(); }
							       //   catch (URISyntaxException e) {}
							          catch (ClientProtocolException e1) {e1.printStackTrace();}
							          catch (IOException e1) {e1.printStackTrace();}
							          catch(java.lang.IllegalArgumentException e1) {
								      e1.printStackTrace(); }
							 
							                               
							                          }
							 
				
				
				
				
				
				
			}
			
			
			
			
			
			
			
			
		}
		 
		 System.out.println(images.size());
		 System.out.println(img1.size());
		 System.out.println(img2.size());
	
	
		
		 
		 
        
		 
		 
		 
		  
		 
	/*try {
		    logger.log(Status.INFO, "Image1"+logger.addScreenCaptureFromPath(captureScreen()));
		    logger.log(Status.INFO, "Image2"+logger.addScreenCaptureFromPath(captureScreen()));
		    logger.log(Status.INFO, "Image3"+logger.addScreenCaptureFromPath(captureScreen()));
		    logger.log(Status.PASS, "Image4"+logger.addScreenCaptureFromPath(captureScreen()));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		*/
		
		}      
		


	        		                
	public static String  captureScreen() throws IOException {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest =System.getProperty("user.dir") +"./Screenshot/WebTech_Automation_Report"+Utility.GetCurrentTimeStamp().replace(":","_").replace(".","_")+".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}
	
	
	protected void openBrowser(String image) {
		   
	       if(driver==null)
			{
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(image);
			}
		
	}
	
	protected void closeBrowser() {
		if(driver!=null){
			driver.close();
		                }
		
		driver=null;
		
	      }
	
	      
	
	private void setupDriver(String URL) 
	{
		try 
		{
			if(driver==null)
			{
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(URL);
			}
		Thread.sleep(2000);
		} catch(Exception ie) {
		ie.printStackTrace();
		}
		
		
	}
	
	@DataProvider(name="URLs")
	public Object[][] loginData() 
	{
		Object[][] arrayObject = Utility.getExcelData("./TestData/TestURLs.xlsx","URLs");
		return arrayObject;
	}
	@AfterMethod
	public void updateResults()
	{
		
		driver.close();
		extent.flush();
		//driver=null;
		//driver.get(htmlrepoter);
	}
	
	@AfterSuite
	 public void tear()
	 {
		
	 }

	
}
