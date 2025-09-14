import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class CartTest {
    public void addItemToCart(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        String loginUsername = "bla171";
        String loginPassword = "bla171";
        // 1. Go to the website and Login
        driver.get("https://demoblaze.com/index.html");

        driver.findElement(By.id("login2")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement emailInput = driver.findElement(By.id("loginusername"));
        emailInput.clear();
        emailInput.sendKeys(loginUsername);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement passwordInput = driver.findElement(By.id("loginpassword"));
        passwordInput.clear();
        passwordInput.sendKeys(loginPassword);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement welcomeText = driver.findElement(By.id("nameofuser"));
        if(welcomeText.getText().contains("Welcome")){
            System.out.println("Logged in with Success!");
        }
        // 2. Add item to the Cart
        try {
            driver.findElement(By.cssSelector("a[href='prod.html?idp_=1']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        } catch
        (StaleElementReferenceException e){
            driver.findElement(By.cssSelector("a[href='prod.html?idp_=1']")).click();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement addToCartBtn = driver.findElement(By.linkText("Add to cart"));
        addToCartBtn.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // Alert alert = driver.switchTo().alert();
        // if (alert.getText().contains("Product Added")) {
        //     System.out.println("Product successfully added to the cart");
        // }
        // alert.accept();
        driver.navigate().refresh(); // workaround to get rid of the alert which cannot be intercepted
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // 3. Verify the item in cart
        WebElement cartLink = driver.findElement(By.id("cartur"));
        cartLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement cartTable = driver.findElement(By.id("tbodyid"));
        if (cartTable.getText().contains("Samsung galaxy s6")){
            System.out.println("Item added is displayed in the cart");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // 4. Delete the item from cart
        WebElement deleteLink = driver.findElement(By.linkText("Delete"));
        deleteLink.click();
        if (!cartTable.getText().contains("Samsung galaxy s6")){
            System.out.println("Item is no longer displayed in the cart");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // 5. Logout
        try {
            driver.findElement(By.id("logout2")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        } catch
        (StaleElementReferenceException e){
            driver.findElement(By.id("logout2")).click();
        }

        WebElement loginButton = driver.findElement(By.id("login2"));
        if(loginButton.isDisplayed()){
            System.out.println("Logged out with Success!");
        }

        driver.close();
    }

}
