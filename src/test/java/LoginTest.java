import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class LoginTest {
    public void loginWithValidData() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        String loginUsername = "bla171";
        String loginPassword = "bla171";
        // 1. Go to the website
        driver.get("https://demoblaze.com/index.html");
        // 2. Click on Login button
        driver.findElement(By.id("login2")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // 3. Insert login username
        WebElement emailInput = driver.findElement(By.id("loginusername"));
        emailInput.clear();
        emailInput.sendKeys(loginUsername);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // 4. Insert Login Password
        WebElement passwordInput = driver.findElement(By.id("loginpassword"));
        passwordInput.clear();
        passwordInput.sendKeys(loginPassword);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // 5. Click on Login button
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // 6. Check user was logged in with success
        WebElement welcomeText = driver.findElement(By.id("nameofuser"));
        if(welcomeText.getText().contains("Welcome")){
            System.out.println("Logged in with Success!");
        }
        // 7. Logout
        try {
            driver.findElement(By.id("logout2")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        } catch
            (StaleElementReferenceException e){
                driver.findElement(By.id("logout2")).click();
            }
        // 8. Check Login button is displayed
        WebElement loginButton = driver.findElement(By.id("login2"));
        if(loginButton.isDisplayed()){
             System.out.println("Logged out with Success!");
        }

        driver.close();
    }
}

