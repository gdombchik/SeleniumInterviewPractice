package practice.interview.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public enum WebDriverList {	
	FIREFOX{
		public WebDriver driverType(){
			return new FirefoxDriver(); 
		}
	},
	HTMLUNIT{
		public WebDriver driverType(){
			//webdriver = new HtmlUnitDriver(BrowserVersion.BEST_SUPPORTED,true); //FIREFOX_38			
			//return new HtmlUnitDriver(BrowserVersion.FIREFOX_45,true);
			return new HtmlUnitDriver(BrowserVersion.CHROME,true);
			//return new HtmlUnitDriver(); 
		}
	},
	CHROME{
		public WebDriver driverType(){
			return new ChromeDriver(); 
		}
	}/*,
	PHANTOMJS{
		public WebDriver driverType(){
			AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
			FileService service = (FileService) context.getBean("fileService");
			System.setProperty("phantomjs.binary.path", service.getPhantomJSDriver());
		    return new PhantomJSDriver(); 
		}
	}*/;
	
	public abstract WebDriver driverType();
}
