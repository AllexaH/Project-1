package com.revature.tests;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberTests {
	
	private WebDriver driver;
	
	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
		System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.get("http://localhost:5500/index.html");
	}
	
	@When("I type {string} in the username text bar")
	public void i_type_in_the_username_text_bar(String string) {
	    WebElement usernameTextBar = driver.findElement(By.xpath("//form[@id='username']"));
	    
	    usernameTextBar.sendKeys("" + string);
	}

	@When("I type {string} in the password text bar")
	public void i_type_in_the_password_text_bar(String string) {
		WebElement usernameTextBar = driver.findElement(By.id("//form[@id='password']"));
	    
	    usernameTextBar.sendKeys("" + string);
	}

	@When("I click on the login button")
	public void i_click_on_the_login_button() {
	    WebElement loginButton = driver.findElement(By.id("//form[@id='login-btn']"));
	    
	    loginButton.click();
	}

	@Then("I should be redirected into the employee homepage")
	public void i_should_be_redirected_into_the_employee_homepage() {
	    System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver_win32/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.get("http://localhost:8080/employee-homepage.html");
	}
	
	@Then("I should be redirected into the manager homepage")
	public void i_should_be_redirected_into_the_manager_homepage() {
		System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver_win32/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.get("http://localhost:5500/manager-homepage.html");
	}

	@When("I type in the number {int} in the amount text bar")
	public void i_type_in_the_number_in_the_amount_text_bar(Integer int1) {
		WebElement usernameTextBar = driver.findElement(By.id("//form[@id='password']"));
	    
	    usernameTextBar.sendKeys("" + int1);
	}

	@When("I select {string} in the drop down menu")
	public void i_select_in_the_drop_down_menu(String string) {
		WebElement usernameTextBar = driver.findElement(By.id("//form[@id='password']"));
	    
	   
	}

	@When("I type {string} in the description text bar")
	public void i_type_in_the_description_text_bar(String string) {
		WebElement usernameTextBar = driver.findElement(By.id("//form[@id='password']"));
	    
	    usernameTextBar.sendKeys("" + string);
	}

	@When("I click the choose file button")
	public void i_click_the_choose_file_button() {
		WebElement loginButton = driver.findElement(By.id("//form[@id='login-btn']"));
	    
	    loginButton.click();
	}

	@When("I submit an image file")
	public void i_submit_an_image_file() {
		WebElement loginButton = driver.findElement(By.id("//form[@id='login-btn']"));
	    
	    loginButton.click();
	}

	@When("I click on submit reimbursement")
	public void i_click_on_submit_reimbursement() {
		WebElement loginButton = driver.findElement(By.id("//form[@id='login-btn']"));
	    
	    loginButton.click();
	}

	@Then("I should see the reimbursement added to the table")
	public void i_should_see_the_reimbursement_added_to_the_table() {
		
	}
}
