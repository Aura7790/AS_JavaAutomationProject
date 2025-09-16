import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void initDriver(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/index.html");
    }

    @Test
    public void checkHeaderElementsHomePage(){
        // 1. Go to Home page
        WebElement homeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nav-link")));
        homeLink.click();

        // 2. Check header menu elements
        List<String> expectedMenuItems = Arrays.asList("home", "contact", "about us", "cart", "log in", "sign up");

        List<WebElement> headerMenuElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".navbar-collapse")));
        List<String> headerMenuTexts = new ArrayList<>();
        for (WebElement el : headerMenuElements){
            String text = el.getText().trim().replace("(current)", "").replace("\n", "").trim();
            headerMenuTexts.add(text.toLowerCase());
        }
        System.out.println(headerMenuTexts);

        for (String expected : expectedMenuItems){
            Assert.assertTrue("Header menu should contain " + expected, headerMenuTexts.contains(expected));
        }
    }

    public void checkHeaderElementsProductPage(){
        // 1. Click on 1st product
        WebElement product1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='prod.html?idp_=1']")));
        product1.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // 2. Check header menu elements
    }

    @After
    public void tearDown(){
        driver.close();
    }
}
