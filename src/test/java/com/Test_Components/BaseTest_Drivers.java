package com.Test_Components;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.PageElements.LoginPage;
import com.Utils.Config;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest_Drivers {
	
	public WebDriver driver;
	public LoginPage login ;
	Config config = new Config();
	
	public WebDriver initializeDriver() throws IOException
	{		
		//System.getProperty is added in String browser to ensure that the project run as per updates
		// through the maven commands from terminal
		
		String browser = System.getProperty("browser")!=null ? 
				System.getProperty("browser"):config.configure("browser");
		
		if(browser.contains("chrome"))
		{
			ChromeOptions co = new ChromeOptions();
			co.addArguments("--remote-allow-origins=*"); //using this to get rid of Web socket issues in Chrome version > 111..
			co.addArguments("--ignore-ssl-errors=yes");
			co.addArguments("--ignore-certificate-errors");
			WebDriverManager.chromedriver().setup();
			if(browser.contains("headless"))
			{
				co.addArguments("headless");
			}
			driver = new ChromeDriver(co);
			//To set size
			Dimension d = new Dimension(1440,900);
			System.out.println("Dimension : "+d);
			driver.manage().window().setSize(d);
		}
		
		else if(browser.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		
		else if(browser.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		
        driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
		
		driver.manage().window().maximize();
		
		return driver;
	}

	@BeforeMethod(alwaysRun=true)
	public LoginPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		
		login = new LoginPage(driver);
		
		String url = config.configure("url");
		
		login.goTo(url);
		
		return login;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
	
}
