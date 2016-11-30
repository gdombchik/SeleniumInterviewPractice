package practice.interview.selenium.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;


public class WebDriverUtils{

	WebDriver driver;
	
	public WebDriverUtils(WebDriver driver){
		this.driver = driver;
	}
	
	/*public void takeStreenShot(String screenShotName){
		File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
			FileService service = (FileService) context.getBean("fileService");
			//FileUtils.copyFile(screenShotFile, new File("/Users/gregorydombchik/Documents/workspace_luna/MavenJunitPrototypePractice/screenshots/" + screenShotName + ".png"));
			FileUtils.copyFile(screenShotFile, new File(service.getScreenShot() + screenShotName + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void takeStreenShot(Scenario scenario){
		try{
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); //stick it in the report
		}catch(WebDriverException somePlatformDontSupportScreenShots){
			System.err.println(somePlatformDontSupportScreenShots.getMessage());
		}    
	}
	
	public WebElement waitUntilWebElementIsAvailable(By locator, int seconds){
		return (new WebDriverWait(driver,seconds)).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void sleep(int seconds){
		try{
			Thread.sleep(seconds * 1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Return the element found by locator filtered by attribute value
	public WebElement getWebElementByLocatorFilteredByAttributeValue(By locator,String attribute, String attributeValue){
		WebElement webElementReturned=null;
		List<WebElement> webElements = driver.findElements(locator);
		for(WebElement webElement : webElements){
			if(webElement.getAttribute(attribute).contains(attributeValue)){
				webElementReturned = webElement;
			}
			//System.out.println("-----> " + webElement.getAttribute("value"));
		}
		return webElementReturned;
	};
	
	public List<WebElement> getWebElementsByLocatorFilteredByAttributeValue(By locator,String attribute, String attributeValue){
		List<WebElement> webElementReturned = new ArrayList<WebElement>();
		List<WebElement> webElements = driver.findElements(locator);
		for(WebElement webElement : webElements){
			if(webElement.getAttribute(attribute).contains(attributeValue)){
				webElementReturned.add(webElement);
			}
		}
		return webElementReturned;
	};
	
	public void checkConfirmTheValuesOfTheCurrentTodoItemsByMap(Collection<String> mapOfValues,List<WebElement> todoLabels){	
		Assert.assertNotNull(todoLabels);  //check the WebElement List is not null
		Assert.assertNotEquals(todoLabels.size(), 0);  //check the WebElement List is not zero
		for(WebElement webElement : todoLabels){
			Assert.assertTrue(mapOfValues.contains(webElement.getText()) || mapOfValues.contains(webElement.getAttribute("textContent")));  //check the WebElement List exists in the Cucumber Datatable Map
		}
	}
	
	public void checkConfirmTheValuesOfTheCurrentTodoItemsByList(List<List<String>> tableList,List<WebElement> todoLabels){
		int i = 1;
		for(WebElement webElement : todoLabels){
			Assert.assertTrue(webElement.getText().equals(tableList.get((i)).get(1)));
			i++;
		}		
	}
	
	public WebElement getWebElementByJavaScript(String javaScript){
		return (WebElement) ((JavascriptExecutor)driver).executeScript("return " + javaScript);
	}
	
	public List<WebElement> getWebElementsByJavaScript(String javaScript){
		return (List<WebElement>)  ((JavascriptExecutor)driver).executeScript("return " + javaScript);
	}
}
