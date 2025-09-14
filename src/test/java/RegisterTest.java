import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Random;

public class RegisterTest {
    WebDriver driver;

    @Before
    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/index.html");
    }

        @Test
    public void registerUserWithValidCredentials(){
        Random random = new Random();
        int min = 0;
        int max = 9;
        int num1 = random.nextInt(max-min+1)+min;
        int num2 = random.nextInt(max-min+1)+min;
        int num3 = random.nextInt(max-min+1)+min;
        StringBuilder randoms = new StringBuilder();
        randoms.append(num1);
        randoms.append(num2);
        randoms.append(num3);
        String registerUsername = "my_test" + randoms;
        String registerPassword = "bla1711#";

        // 1. Click on SignUp button
        WebElement signUpButton = driver.findElement(By.id("signin2"));
        signUpButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 2. Insert SignUp username
        WebElement usernameInput = driver.findElement(By.id("sign-username"));
        usernameInput.clear();
        usernameInput.sendKeys(registerUsername);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 3. Insert SignUp Password
        WebElement passwordInput = driver.findElement(By.id("sign-password"));
        passwordInput.clear();
        passwordInput.sendKeys(registerPassword);

    }

    @After
    public void tearDown(){
        driver.close();
    }
}
