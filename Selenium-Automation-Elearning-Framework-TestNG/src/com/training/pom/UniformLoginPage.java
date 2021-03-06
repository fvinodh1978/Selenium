package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UniformLoginPage {

	private WebDriver driver; 
	private WebDriverWait wait;
	ExtentTest logger;
	
	public UniformLoginPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver; 
		this.logger=logger;
		this.wait= new WebDriverWait(driver, 5);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@name='email']")
	private WebElement userName; 
	
	@FindBy(xpath="//*[@name='password']")
	private WebElement password; 
	
	@FindBy(xpath="//input[@value='Login' and @type ='submit']")
	private WebElement login; 
	
	@FindBy(xpath="//*[@class='alert alert-danger']")
	private WebElement failureMessage; 
	
	public WebElement getUser() {
		return userName;
	}
	
	public void typeUser(String user) {
		this.userName.clear();
		this.userName.sendKeys(user);
		logger.log(LogStatus.PASS, "Type User : " + user);
	}		

	public void typePassword(String pass) {
		this.password.clear();
		this.password.sendKeys(pass);
		logger.log(LogStatus.PASS, "Type Password : " + pass);
	}	

	public void clickLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(login));
		this.login.click(); 
		logger.log(LogStatus.PASS, "Click Login Button");
	}	
	
	public void verifyEnteredUser() {
		String enteredUser=this.userName.getAttribute("value");
		logger.log(LogStatus.PASS, "Entered User is : " + enteredUser);
	}
	
	public void verifyEnteredPassword() {
		String enteredPassword=this.password.getAttribute("value");
		logger.log(LogStatus.PASS, "Entered Password is : " + enteredPassword);
	}		

	public void verifyLoginPageLaunched() throws Exception {
		boolean expected=true;
		boolean actual;
		try{
			wait.until(ExpectedConditions.visibilityOf(userName));
			actual=userName.isDisplayed();
			Assert.assertEquals(expected, actual, "Login Form is Displayed Successfully");
			logger.log(LogStatus.PASS, "Verify Login Form is Displayed");
		}catch(Exception e) {
			logger.log(LogStatus.FAIL, "Verify Login Form is Displayed");
			throw new Exception(e);
		}			
	}
	
	public void verifyLoginFailed() throws Throwable {
		String expected="Warning: No match for E-Mail Address and/or Password.";
		String actual;
		try{
			wait.until(ExpectedConditions.visibilityOf(failureMessage));
			actual=failureMessage.getText();
			Assert.assertEquals(actual, expected);
			logger.log(LogStatus.PASS, "Verify Login is Failed;  With Error : \"" + actual + "\"");
		}catch(Throwable t) {
			logger.log(LogStatus.FAIL, "Verify Login is Failed");
			throw new Throwable(t);
		}	
	}	
}
