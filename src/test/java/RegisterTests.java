import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class RegisterTests {
    WebDriver driver;
    WebDriverWait wait;
    String defaultRegisterUsername = "testingAQA";
    String defaultRegisterPassword = "testingAQA";

    @Before
    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    @Test
    public void registerWithEmptyCredentials(){
        // 1. Click on SignUp button
        WebElement signUpButton = driver.findElement(By.id("signin2"));
        signUpButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 2. Wait for SignUp Modal to display
        WebElement signUpModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModalLabel")));
        String actualSignUpModalText = signUpModal.getText();
        String expectedSignUpModalText = "Sign up";
        Assert.assertEquals(actualSignUpModalText, expectedSignUpModalText);

        // 3. Without filling in username and password, click on SignUp Button
        WebElement signUpButtonModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Sign up']")));
        signUpButtonModal.click();

        // 4. Check user was not signed up
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Please fill out Username and Password."));
        alert.accept();

        // 5. Check SignUp button is displayed
        WebElement homeSignUpButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin2")));
        Assert.assertTrue(homeSignUpButton2.isDisplayed());
    }

    @Test
    public void signUpWithEmptyPassword() {
        // 1. Click on SignUp button from HomePage
        WebElement homeSignUpButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin2")));
        homeSignUpButton.click();

        // 2. Wait for SignUp Modal to display
        WebElement signUpModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModalLabel")));
        String actualSignUpModalText = signUpModal.getText();
        String expectedSignUpModalText = "Sign up";
        Assert.assertEquals(actualSignUpModalText, expectedSignUpModalText);

        // 3. Insert SignUp username
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));
        usernameInput.clear();
        usernameInput.sendKeys(defaultRegisterUsername);

        // 4. Click on SignUp button - SignUp Form without filling in the password
        WebElement signUpButtonModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Sign up']")));
        signUpButtonModal.click();

        // 5. Check user was not signed up
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Please fill out Username and Password."));
        alert.accept();

        // 6. Check SignUp button is displayed
        WebElement homeSignUpButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin2")));
        Assert.assertTrue(homeSignUpButton2.isDisplayed());
    }

    @Test
    public void signUpWithEmptyEmail() {
        // 1. Click on SignUp button from HomePage
        WebElement homeSignUpButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin2")));
        homeSignUpButton.click();

        // 2. Wait for SignUp Modal to display
        WebElement signUpModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModalLabel")));
        String actualSignUpModalText = signUpModal.getText();
        String expectedSignUpModalText = "Sign up";
        Assert.assertEquals(actualSignUpModalText, expectedSignUpModalText);

        // 4. Without filling in SignUp Username, Insert SignUp Password
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-password")));
        passwordInput.clear();
        passwordInput.sendKeys(defaultRegisterPassword);

        // 5. Click on SignUp button - SignUp Form
        WebElement signUpButtonModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Sign up']")));
        signUpButtonModal.click();

        // 6. Check user was not signed up in
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Please fill out Username and Password."));
        alert.accept();

        // 7. Check SignUp button is displayed
        WebElement homeSignUpButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin2")));
        Assert.assertTrue(homeSignUpButton2.isDisplayed());
    }

    @After
    public void tearDown(){
        driver.close();
    }
}
