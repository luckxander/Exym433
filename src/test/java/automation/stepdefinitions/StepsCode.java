package automation.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.HttpSessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Ordering;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


@SpringBootTest
public class StepsCode {
	
	public WebDriver dr = null;
	static String URL_login = "https://exym-vnext-teamtest2.azurewebsites.net/";
	static HttpSessionId stored = null;

	
	 //Browser SetUp and Login
	 ////////////////////////////////////////////////////////////////////////////////////////////////////
	 
    @Before
    public void setUp() throws Throwable { 
    	   System.out.println("Running setUp ...");
		   System.setProperty("webdriver.chrome.driver","C:/Users/luckx/OneDrive/Documents/workspace-spring-tool-suite-4-4.13.1.RELEASE/exym425/chromedriver.exe");
	       
		   dr = new ChromeDriver();
	       dr.manage().window().maximize();
	       dr.get(URL_login); 
		   
	       if(stored == null) {
			   HttpSessionId session = new HttpSessionId();
			   TimeUnit.SECONDS.sleep(6);
			   WebDriverWait wait = new WebDriverWait(dr, 10);
			   wait.until(ExpectedConditions.textToBePresentInElement(dr.findElement(By.xpath("//body[@id='kt_body']/app-root/ng-component/div/div/div/div/div/tenant-change/span")),"Current tenant"));

			   String currentText = dr.findElement(By.cssSelector("#kt_body > app-root > ng-component > div > div > div.d-flex.flex-center.flex-column.flex-column-fluid.p-10.pb-lg-20 > div > div > tenant-change > span")).getText();
			   //System.out.println("Current text => "+currentText);
			   if(currentText.intern() == "Current tenant: Not selected ( Change )") {
				  
				  //switch to default 
				  dr.findElement(By.linkText("Change")).click();
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.xpath("//*[@id=\"kt_body\"]/app-root/ng-component/div/div/div[1]/div/div/tenant-change/span/tenantchangemodal/div/div/div/form/div[2]/div[1]/div/label/input")).click();
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.cssSelector("#tenancyNameInput")).sendKeys("default");
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.xpath("//*[@id=\"kt_body\"]/app-root/ng-component/div/div/div[1]/div/div/tenant-change/span/tenantchangemodal/div/div/div/form/div[3]/button[2]")).click();
			   
				  //click openIDconnect
				  TimeUnit.SECONDS.sleep(6);
				  dr.findElement(By.linkText("OpenIdConnect")).click();
				  
				  //user credentials 
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.cssSelector("#signInName")).sendKeys("lori@ascendle.com");
				  dr.findElement(By.cssSelector("#password")).sendKeys("T3$tingisfun");
				  dr.findElement(By.cssSelector("#next")).click();
				  				  
			   }
			   
			   stored = session;
			   TimeUnit.SECONDS.sleep(3);
			}
    }
    
    /////////////////////////////////////////////////////////////////////////////
	//End Browser SetUp and Login

	
	@Given("I am a clinician user")
	public void i_am_a_clinician_user() throws Throwable {
		assertNotEquals(stored,null);
	}

	@When ("I go to the main page Exym vNext portal")
	public void i_go_to_the_main_page_Exym_vNext_portal() throws Throwable {
		TimeUnit.SECONDS.sleep(6);
		String expected_notes = dr.findElement(By.xpath("//*[@id=\"kt_wrapper\"]/div[2]/app-landing-dashboard/div/div/sub-header/div/div/div[1]/h5")).getText();
	    String current_notes ="Dashboard";
	    assertEquals(current_notes,expected_notes);

	}

	@Then ("I should see an 'i' icon on the right of activity name")
	public void see_an_icon() throws Throwable {
		//String icon_png = dr.findElement(By.xpath("//*[@id=\"kt_wrapper\"]/div[2]/app-landing-dashboard/div/div/div/note-list/div/div[1]")).getText();
		//WebElement icon_png = dr.findElement(By.id("icon-info"));
		try{
			dr.findElement(By.id("icon-info"));
			 //Since, no exception, so element is present
			 System.out.println("Icon present");
		}
		catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}
	
	@And ("I can hover over the icon")
	public void hover_the_icon() throws Throwable {
		//Localize the icon
		WebElement icon = dr.findElement(By.id("icon-info"));
		//Instantiating Actions class
		Actions actions = new Actions(dr);
		//Hovering on main menu
		actions.moveToElement(icon);
		
	}
	
    @And ("I should see a popover")
    public void see_popuover() throws Throwable {
		//Scroll down
    	JavascriptExecutor js = (JavascriptExecutor) dr;
    	js.executeScript("window.scrollBy(0,850)", "");   	
    	//Localize the icon
		WebElement icon = dr.findElement(By.id("icon-info"));
		//Instantiating Actions class
		Actions actions = new Actions(dr);
		//Hovering on main menu
		actions.moveToElement(icon);
		TimeUnit.SECONDS.sleep(2);
    }
		
    @And ("I should see a black background and white text with ‘Activity ID: 000000’ in the popover")
    public void see_id_text() throws Throwable{
		WebElement icon = dr.findElement(By.id("icon-info"));
		//Instantiating Actions class
		Actions actions = new Actions(dr);
		//Hovering on main menu
		actions.moveToElement(icon);
		//Hold the element using clickAndHold method
		actions.clickAndHold(icon).perform();
		
		/*** Need to finish the part below, text is null ***/
		//Get the text
		String IDtext = icon.getText();
		System.out.println("ID => "+IDtext);
    }
    
    @After //tearDown() (close browser)
    public void tearDown() throws Exception {
    	 TimeUnit.SECONDS.sleep(3);
        System.out.println("Running tearDown ...");
        dr.close();
    }
}
