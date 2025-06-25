package com.Test_Components;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MMTCal {

	public static WebDriver driver;
	
	@Test
	public void mmttest() throws InterruptedException {
		
	    
		WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(co);
		driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
		driver.get("https://www.makemytrip.com");
		
		Thread.sleep(3000);
		
		driver.manage().window().maximize();
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='commonModal__close']")));
		driver.findElement(By.xpath("//span[@class='commonModal__close']")).click();
		
		driver.findElement(By.xpath("//input[@id='departure']/following::*[1]")).click();
		
		Thread.sleep(3000);
		String month = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption']/div)[1]")).getText();
		
		System.out.println("The month is : "+month);
		
		LocalDate ld = LocalDate.now();
		Month monthly = ld.getMonth();
		String yearly = String.valueOf(ld.getYear());
		System.out.println(monthly+" "+yearly);
		Thread.sleep(3000);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
        String monthAbbreviation = ld.format(formatter);
		System.out.println(monthAbbreviation);
		
		List<WebElement> datePriceElements = driver.findElements(By.xpath("//div[contains(@class,'DayPicker-Day')][@aria-disabled='false'][contains(@aria-label,'"+monthAbbreviation+"')]"));
		
		System.out.println("Dates : "+datePriceElements .size());
		
		System.out.println("Date and Prices:");
		int minPrice = Integer.MAX_VALUE;
		int minDay = 0;
		for (WebElement element : datePriceElements)
        {
        	System.out.println(element.getAttribute("outerHTML"));
        	////div[contains(@class,'DayPicker-Day')][@aria-disabled='false']
                String date = element.findElement(By.xpath(".//div[@class = 'dateInnerCell']/p[1]")).getText();
                String price =element.findElement(By.xpath(".//div[@class = 'dateInnerCell']//following-sibling::*[1]")).getText();
                System.out.println(date +" → " + price);
                if(Integer.parseInt(price.replace(",", "")) < minPrice)
                {
                	minPrice = Integer.parseInt(price.replace(",", ""));
                	minDay = Integer.parseInt(date);
                }
        }
		System.out.println("---- The MIN Travel date and Price----");
		System.out.println(minDay+" : "+minPrice);
		/*
		if(month.equalsIgnoreCase(monthly+" "+yearly))
		{
			for(int i=0;i<dates.size();i++)
			{
				String price = driver.findElement(By.xpath("//div[@class='DayPicker-Week']/div/div/p[1]")).getText();
				System.out.println("Price:"+price);
			}
		}
		*/
		Thread.sleep(3000);
		//String price = driver.findElement(By.xpath("//div[@class='DayPicker-Week']/div/div/p[2]")).getText();
		
        //System.out.println("Price:"+price);
		
		//Thread.sleep(3000);
		driver.quit();

	}

}
