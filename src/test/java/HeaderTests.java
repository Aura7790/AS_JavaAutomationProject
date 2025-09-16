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

public class HeaderTests {
    WebDriver driver;
    WebDriverWait wait;
    List<String> expectedMenuItems = Arrays.asList("Home", "Contact", "About us", "Cart", "Log in", "Sign up");

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
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".navbar-nav.ml-auto")));
        List<WebElement> headerMenuElements = driver.findElements(By.cssSelector("ul.navbar-nav > li > a.nav-link"));
        List<String> headerMenuTexts = new ArrayList<>();
        for (WebElement elem : headerMenuElements){
            headerMenuTexts.add(elem.getText().replace("(current)", "").trim());
            headerMenuTexts.removeIf(item -> item == null || item.trim().isEmpty());
        }

        Assert.assertEquals((expectedMenuItems.size()), headerMenuTexts.size());
        for (int i = 0; i < expectedMenuItems.size(); i++) {
            String expected = expectedMenuItems.get(i);
            String actual = headerMenuTexts.get(i);
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void checkHeaderElementsProductPage(){
        // 1. Click on 1st product
        WebElement product1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='prod.html?idp_=1']")));
        product1.click();

        // 2. Check header menu elements
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".navbar-nav.ml-auto")));
        List<WebElement> headerMenuElements = driver.findElements(By.cssSelector("ul.navbar-nav > li > a.nav-link"));
        List<String> headerMenuTexts = new ArrayList<>();
        for (WebElement elem : headerMenuElements){
            headerMenuTexts.add(elem.getText().replace("(current)", "").trim());
            headerMenuTexts.removeIf(item -> item == null || item.trim().isEmpty());
        }

        Assert.assertEquals((expectedMenuItems.size()), headerMenuTexts.size());
        for (int i = 0; i < expectedMenuItems.size(); i++) {
            String expected = expectedMenuItems.get(i);
            String actual = headerMenuTexts.get(i);
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void checkHeaderElementsCartPage(){
        // 1. Click on Cart
        WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartur")));
        cartLink.click();

        // 2. Check header menu elements
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".navbar-nav.ml-auto")));
        List<WebElement> headerMenuElements = driver.findElements(By.cssSelector("ul.navbar-nav > li > a.nav-link"));
        List<String> headerMenuTexts = new ArrayList<>();
        for (WebElement elem : headerMenuElements){
            headerMenuTexts.add(elem.getText().replace("(current)", "").trim());
            headerMenuTexts.removeIf(item -> item == null || item.trim().isEmpty());
        }

        Assert.assertEquals((expectedMenuItems.size()), headerMenuTexts.size());
        for (int i = 0; i < expectedMenuItems.size(); i++) {
            String expected = expectedMenuItems.get(i);
            String actual = headerMenuTexts.get(i);
            Assert.assertEquals(expected, actual);
        }
    }

    @After
    public void tearDown(){
        driver.close();
    }
}
