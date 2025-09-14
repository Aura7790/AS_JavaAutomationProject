import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartTest {
    WebDriver driver;
    WebDriverWait wait;
    String loginUsername = "bla171";
    String loginPassword = "bla171";

    @Before
    public void initDriver(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/index.html");
    }
    @Test
    public void addItemToCart() {
        // 1. Login User
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        emailInput.clear();
        emailInput.sendKeys(loginUsername);

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));
        passwordInput.clear();
        passwordInput.sendKeys(loginPassword);

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log in']")));
        loginButton.click();

        String actualWelcomeText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))).getText();
        String expectedWelcomeText = "Welcome " + loginUsername;
        Assert.assertEquals(expectedWelcomeText, actualWelcomeText);

        // 2. Add item to the Cart
        WebElement selectProduct1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='prod.html?idp_=1']")));
        selectProduct1.click();

        WebElement addToCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add to cart")));
        addToCartBtn.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Product added."));
        alert.accept();

        // 3. Verify the item in cart
        WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartur")));
        cartLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement cartTable = driver.findElement(By.id("tbodyid"));
        WebElement productNameCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tbody/tr/td[2]")));
        String productName = productNameCell.getText();
        Assert.assertTrue(productName.contains("Samsung galaxy s6"));

        // 4. Delete the item from cart
        WebElement deleteLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
        deleteLink.click();
        wait.until(ExpectedConditions.invisibilityOf(cartTable));

        // 5. Logout and check Login button is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2"))).click();

        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

        @After
        public void tearDown(){
            driver.close();
        }
}
