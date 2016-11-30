package practice.interview.selenium;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import practice.interview.selenium.utils.WebDriverList;

public abstract class AbstractPageStepDefinition {
	protected static WebDriver webdriver;

	public WebDriver getWebdriver() {
		//web driver is null or webdriver.quit (not webdriver.close)
		if(webdriver==null || ((RemoteWebDriver)webdriver).getSessionId() == null){
			webdriver = WebDriverList.FIREFOX.driverType();
			webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //default is 0 seconds
		}
		return webdriver;
	}
}
