package Util;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;


public class AmzonOTPHandle {
  public static final String ACCOUNT_SID ="AC099b1bb0013701657b0da59172ede34c";
  public static final String AUTH_TOKEN = "86eb13b1a44e5b0d34c7d57a3c3231d6";
	public static void main(String[] args) throws Exception 
	{   		
		System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(@class,'nav-line-2')][contains(text(),'Account & Lists')]")).click();
		Thread.sleep(6000);
		driver.findElement(By.linkText("Start here.")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='ap_customer_name']")).sendKeys("Shraddha");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@id='auth-country-picker-container']//span[@class='a-button-inner']")).click();
		Thread.sleep(6000);
		driver.findElement(By.xpath("//a[@id='auth-country-picker_212']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='ap_phone_number']")).sendKeys("2055641987");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys("shraddha12salvi");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='continue']")).click();
      // get the OTP using Twilio APIs:
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String smsBody = getMessage();
		System.out.println(smsBody);
	String OTPNumber = smsBody.replaceAll("[^-?0-9]+","");
	System.out.println(OTPNumber);
	driver.findElement(By.id("auth-pv-enter-code")).sendKeys(OTPNumber);
		
		
		}
	public static String getMessage() {
		return getMessages().filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND)== 0)
		.filter(m -> m.getTo().equals("+12055641987")).map(Message::getBody).findFirst()
		.orElseThrow(IllegalStateException::new);
	}
	private static Stream<Message> getMessages() {
		ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
		return StreamSupport.stream(messages.spliterator(),false);
		
	}

}
