package practice.interview.selenium;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ToolsQAFormTest{
	/*private static WebDriver driver;

	@BeforeClass
	public static void setUp() throws Exception {
		driver =  new FirefoxDriver(); 
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}*/
	
	private WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		driver =  new FirefoxDriver(); 
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Test
	public void listWebElementLocators(){
		//by.name("")
		//by.id("")
		//by.tagName("")
		//by.cssSelector("")
		//by.linkText("")
		//by.partialLinkText("")
		//by.tagName("")
		//by.xpath("")
		//JavascriptExecutor.class
		//Assert.assertTrue(true);
	}

	@Test
	public void toolsQAForm() {
		driver.navigate().to("http://toolsqa.com/automation-practice-form");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Text Box
		WebElement firstName = driver.findElement(By.name("firstname"));
		firstName.sendKeys("First Name Text");
		Assert.assertTrue(firstName.getAttribute("value").equals("First Name Text"));
		
		//Radio Button
		WebElement yearsOfExperienceOne = driver.findElement(By.id("exp-0"));
		Assert.assertFalse(yearsOfExperienceOne.isSelected()); //not selected yet
		yearsOfExperienceOne.click(); //selected
		Assert.assertTrue(yearsOfExperienceOne.isSelected()); //confirm selection
		Assert.assertEquals("1",yearsOfExperienceOne.getAttribute("value")); //check value
		
		//Check Box
		WebElement seleniumIDE = driver.findElement(By.id("tool-1"));
		Assert.assertFalse(seleniumIDE.isSelected()); //not selected yet
		seleniumIDE.click(); //selected
		Assert.assertTrue(seleniumIDE.isSelected()); //confirm selection
		Assert.assertEquals("Selenium IDE",seleniumIDE.getAttribute("value")); //check value
		
		//Drop Down (Use --> org.openqa.selenium.support.ui.Select)
		Select continentsDropDown = new Select(driver.findElement(By.id("continents")));
		Assert.assertEquals(7,continentsDropDown.getOptions().size());
		WebElement asiaOption = continentsDropDown.getFirstSelectedOption();
		Assert.assertEquals("Asia",asiaOption.getText());
		/*continentsDropDown.deselectAll();
		try{
			continentsDropDown.getFirstSelectedOption();
		}catch(NoSuchElementException e){
			//confirm NoSuchElementException is thrown
			System.out.println("in catch");
		}*/
		continentsDropDown.selectByIndex(2);			//1.  Select by Index
		WebElement africaOption = continentsDropDown.getFirstSelectedOption();
		Assert.assertEquals("Africa", africaOption.getText());
		//continentsDropDown.selectByValue("Australia");		//2.  Select by Value (no current value in the form)
		//WebElement australiaOption = continentsDropDown.getFirstSelectedOption();
		//Assert.assertEquals("Australia", australiaOption.getText());
		continentsDropDown.selectByVisibleText("Australia");	//3.  Select by Visible Text
		WebElement australiaOption = continentsDropDown.getFirstSelectedOption();
		Assert.assertEquals("Australia", australiaOption.getText());
		
		//Submit Button
		WebElement submitButtonOne = driver.findElement(By.tagName("button"));
		submitButtonOne.submit();
		String urlOne = driver.getCurrentUrl();
		WebElement submitButtonTwo = driver.findElement(By.tagName("button"));
		submitButtonTwo.click();
		String urlTwo = driver.getCurrentUrl();
	}
	
	@Test
	public void implicitWait(){
		/* Implicit Wait: During Implicit wait if the Web Driver cannot find it immediately because of its availability, 
		 * it will keep polling (around 250 milli seconds) the DOM to get the element. If the element is not available within 
		 * the specified Time an NoSuchElementException will be raised. The default setting is zero. Once we set a time, 
		 * the Web Driver waits for the period of the WebDriver object instance.
		 */
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void explicitWait(){
		/*Explicit Wait: There can be instance when a particular element takes more than a minute to load. 
		 *In that case you definitely not like to set a huge time to Implicit wait, as if you do this your browser 
		 *will going to wait for the same time for every element.
		 *
		 *To avoid that situation you can simply put a separate time on the required element only. 
		 *By following this your browser implicit wait time would be short for every element and it would be large 
		 *for specific element.*/

		driver.get("http://toolsqa.com/automation-practice-form");
		WebElement firstName = (new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.name("firstname")));
	}
	
	@Test
	public void fluentWait(){
		/*Fluent Wait: Let’s say you have an element which sometime appears in just 1 second and some time it takes minutes to appear. 
		 *In that case it is better to use fluent wait, as this will try to find element again and again until it find it or until the final timer runs out.*/
		driver.get("http://toolsqa.com/automation-practice-form");
		new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)	 
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("firstname")));		 
	}
	
	@Test void visibilityOfWaits(){
		
	}
	
	@Test
	public void threadSleep(){
		/*Stop the thread at the exact specified time.  
		Does not check WebElements to be found. 
		Will always the specified the time.*/
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //2 seconds
	}
	
	@Test 
	public void navigationCommands(){
		driver.navigate().to("http://toolsqa.com/automation-practice-form");
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().refresh();
	}

	@Test
	public void simpleAlertPopUp(){
		driver.navigate().to("http://toolsqa.com/handling-alerts-using-selenium-webdriver/");
		//WebElement simpleAlertPopUpButton = driver.findElement(By.xpath("//button[text()='Simple Alert']"));
		//WebElement simpleAlertPopUpButton = driver.findElement(By.xpath("//button[@onclick='pushAlert()']"));
		//WebElement simpleAlertPopUpButton = driver.findElements(By.xpath("//button")).get(0);
		WebElement simpleAlertPopUpButton = driver.findElement(By.xpath("//*[text()='Simple Alert']"));
		
		String mainPage = driver.getWindowHandle();
		simpleAlertPopUpButton.click();
		Alert alt = driver.switchTo().alert();
		alt.accept(); //to click ok
		driver.switchTo().window(mainPage);
	}
	
	@Test
	public void confirmAlertBox(){
		driver.navigate().to("http://toolsqa.com/handling-alerts-using-selenium-webdriver/");
		//WebElement confirmAlertBoxButton = driver.findElement(By.xpath("//button[text()='Confirm Pop up']"));
		WebElement confirmAlertBoxButton = (new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Confirm Pop up']")));
		//WebElement confirmAlertBoxButton = driver.findElement(By.xpath("//button[@onclick='pushConfirm()']"));
		//WebElement confirmAlertBoxButton = driver.findElements(By.xpath("//button")).get(1);
		//WebElement confirmAlertBoxButton = driver.findElement(By.xpath("//*[text()='Confirm Pop up']"));
		
		String mainPage = driver.getWindowHandle();
		confirmAlertBoxButton.click();
		Alert alt = driver.switchTo().alert();
		alt.dismiss(); //to click to dismiss
		driver.switchTo().window(mainPage);
	}
	
	@Test
	public void promptAlertBox(){
		driver.navigate().to("http://toolsqa.com/handling-alerts-using-selenium-webdriver/");
		//WebElement promptAlertBoxButton = driver.findElement(By.xpath("//button[text()='Prompt Pop up']"));
		WebElement promptAlertBoxButton = driver.findElement(By.xpath("//button[@onclick='promptConfirm()']"));
		//WebElement promptAlertBoxButton = driver.findElements(By.xpath("//button")).get(2);
		//WebElement promptAlertBoxButton = driver.findElement(By.xpath("//*[text()='Prompt Pop up']"));
		
		String mainPage = driver.getWindowHandle();
		promptAlertBoxButton.click();
		Alert alt = driver.switchTo().alert();
		Assert.assertEquals("Do you like toolsqa?", alt.getText());
		alt.sendKeys("Yes"); //not exactly sure how to extract this info.  the gettext returns the text on above the input box
		alt.accept();
		driver.switchTo().window(mainPage);
	}
	
	@Test
	public void sendKeysReturnAndEnter(){
		driver.navigate().to("http://toolsqa.com/automation-practice-form");
		Actions act = new Actions(driver);
		act.sendKeys(Keys.RETURN);
		
		//WebElement firstName = driver.findElement(By.name("firstname"));
		//firstName.sendKeys(Keys.RETURN); // or Keys.Enter

		//System.out.println(driver.getCurrentUrl());
	}
	
	@Test
	public void differentWebDriverExceptions(){
		//NoSuchElementException:  getWebElement or getWebElements not found
		//StaleElementReferenceException
		//ElementNotVisibleException
		//ElementNotSelectableException
		//NoAlertPresentException
		//NoSuchAttributionException
		//NoSuchWindowException
		//TimeoutException
		//WebDriverException
	}
	
	@Test
	public void frameWorkDescriptions(){
		/*
		 * 1.  Data Driven Automation Framework:  Data-driven testing (DDT) is a term used in the testing of computer software to 
		 * 	   describe testing done using a table of conditions directly as test inputs and verifiable outputs as well as the process 
		 *     where test environment settings and control are not hard-coded.
		 * 
		 * 2.  Method Driven Automation Framework
		 * 
		 * 3.  Modular Automation Framework:  In most of the web application we have few set of actions which are always executed in the 
		 * 	   series of actions. Rather than writing those actions again and again in our test, we can club those actions in to a method 
		 * 	   and then calling that method in our test script. Modularity avoids duplicacy of code. In future if there is any change in the 
		 *     series of action, all you have to do is to make changes in your main modular method script. No test case will be impacted with the 
		 *     change.
		 * 
		 * 4.  Keyword Driven Automation Framework:  Keyword Driven Framework is a type of Functional Automation Testing Framework 
		 *     which is also known as Table-Driven testing or Action Word based testing. The basic working of the Keyword Driven 
		 *     Framework is to divide the Test Case in to four different parts. First is called as Test Step, second is Object of Test Step, 
		 *     third is Action on Test Object and fourth is Data for Test Object.
		 *     
		 *	   The above categorization can be done and maintained with the help of Excel spread sheet:
		 *		
		 *	   Test Step: It is a very small description of the Test Step or the description of the Action going to perform on Test Object.
		 *	   Test Object: It is the name of the Web Page object/element, like Username & Password.
		 *	   Action: It is the name of the action, which is going to perform on any Object such as click, open browser, input etc.
		 *	   Test Data: Data can be any value which is needed by the Object to perform any action, like Username value for Username field.
		 * 	
		 * 	   Example:
		 * 	   
		 * 	   Step Number 			Description						KeyWord			Locator/Data
		 * 	   1					Login into Application	  		login			
		 *     2  					Click on Homepage				clickLink		//*[@id='homepage']
		 *     3  					Verify logged in user			verifyLink		//*[@id='link']
		 * 5.  Hybrid Automation Framework
		 * 	   Test sheet would contain both the keywords and the Data.
		 * 
		 * 	   Step Number 			Description						KeyWord			Locator					Data
		 * 	   1					Navigate to login page			navigate
		 *     2  					Enter User Name					input			//*[@id='username']		userA
		 *     3  					Enter Password					input			//*[@id='password']		password
		 *     4 					Verify Home Page				verifyUser		//*[@id='user']
		 *     
		 * 6.  Behavior Driven Development Framework
		 * 	
		 * 	   Cucucumber / Gherkin	     
		 */
	}
	
	@Test
	public void handleWindows(){
		
	}
	
	@Test
	public void handleFrames(){
		
	}
	
	@Test
	public void webDriverPackage(){
		//driver.getClass().getPackage().getName() --> org.openqa.selenium.firefox
		Assert.assertTrue(driver.getClass().getPackage().getName().contains("org.openqa.selenium"));
	}
	
	@Test
	public void widthOfATextBox(){
		
	}
	
	@Test
	public void rightClickOnAnElement(){
		
	}
	
	@Test
	public void dragAndDropOnAnElement(){
		
	}
	
	@Test
	public void hoverTheMouseOnAnElement(){
		
	}
	
	@Test
	public void doubleClickOnAnElement(){
		
	}
	
	@Test
	public void superInterfaceOfWebDriver(){
		
	}
	
	@Test
	public void screenShotEventFiringWebDriver(){
		
	}
	
	@Test
	public void screenShotTakesScreenshot(){
		
	}
	
	@Test
	public void widthAndHeightOfTheRenderedElement(){
		
	}
	
	@Test
	public void syntaxSiblings(){
		
	}
	
	@Test
	public void maximizeTheWindow(){
		
	}
	
	@Test
	public void javascriptInSelenium(){
		
	}
	
	@Test
	public void xPathOfAElement(){
		//htmltag[@attname='attvalue']
		//htmltag[text()='textvalue']
		//htmltag[contains(text(),'textvalue')]
		//htmltag[contains(@attname,'atttvalue')]
	}
}