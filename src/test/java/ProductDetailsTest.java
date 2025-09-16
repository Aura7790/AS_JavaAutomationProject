import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLOutput;
import java.time.Duration;

public class ProductDetailsTest {
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
    public void checkProductDetails(){
        // 1. Go to Home page
        WebElement homeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nav-link")));
        homeLink.click();

        // 2. Click on 1st product
        WebElement product1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='prod.html?idp_=1']")));
        product1.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // 3. Check 1st product details
        WebElement productImage = driver.findElement(By.cssSelector(".carousel-inner"));
        // Assert.assertTrue(productImage.isDisplayed());
        WebElement productDescription = driver.findElement(By.id("tbodyid"));
        // Assert.assertTrue(productDescription.isDisplayed());
        WebElement productTitle = driver.findElement(By.cssSelector(".name"));
        Assert.assertEquals(productTitle.getText(), "Samsung galaxy s6");
        WebElement productPrice = driver.findElement(By.cssSelector(".price-container"));
        WebElement moreInformation = driver.findElement(By.cssSelector("#more-information > strong"));
        String moreInfoText = moreInformation.getText();
        System.out.println(moreInfoText);
        WebElement moreInformationParagraph = driver.findElement(By.cssSelector("#more-information > p"));
        String moreInfoParaText = moreInformationParagraph.getText();
        System.out.println(moreInfoParaText);
        WebElement addToCartButton = driver.findElement(By.cssSelector("a.btn.btn-success.btn-lg"));
        Assert.assertEquals(addToCartButton.getText(), "Add to cart");
    }

    @After
    public void tearDown(){
        driver.close();
    }
}
