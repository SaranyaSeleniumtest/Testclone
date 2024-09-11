package Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class Base {

	public static Properties prop;
	public static FileInputStream	 fis;
	public WebDriver driver;
	public Logger logger;
	
	@Parameters({"browser"})
	@BeforeMethod
	public void initialize(String browser) throws IOException {
		System.out.println(browser);
		logger= LogManager.getLogger(this.getClass());
		Base.properties();
		if(prop.getProperty("Environment").equalsIgnoreCase("remote")) {

			DesiredCapabilities dc = new DesiredCapabilities();
			if(browser.equalsIgnoreCase("chrome")) {
				logger.info("browser is chrome");
				dc.setBrowserName("chrome");
			}else if(browser.equalsIgnoreCase("edge")) {
				logger.info("browser is edge");
				dc.setBrowserName("MicrosoftEdge");
			}
			driver= new RemoteWebDriver(new URL("http://localhost:4444"), dc);
			
	}
		
		if (prop.getProperty("Environment").equalsIgnoreCase("local")) {
			if(browser.equalsIgnoreCase("chrome")) {
				driver= new ChromeDriver();
			}
			if(browser.equalsIgnoreCase("Edge")) {
				driver= new EdgeDriver();
			}
			
		}
		
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		
	}

	public static void properties() throws IOException {
		fis= new FileInputStream(".\\config.properties");
		prop = new Properties();
		prop.load(fis);
		System.out.println(prop.getProperty("Environment"));

	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}

