import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;


public class LoginTests {
    WebDriver driver;
    WebDriverWait wait;
    String loginUsername = "bla171";
    String loginPassword = "bla171";
    String nonExistentUsername = "bla1712";
    String wrongPassword = "wrong password";

    @Before
    public void initDriver(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/index.html");
    }
    @Test
    public void loginWithValidData() {

        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 3. Insert login username
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        emailInput.clear();
        emailInput.sendKeys(loginUsername);

        // 4. Insert Login Password
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));
        passwordInput.clear();
        passwordInput.sendKeys(loginPassword);

        // 5. Click on Login button - Login Form
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log in']")));
        loginButton.click();

        // 6. Check user was logged in with success
        String actualWelcomeText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))).getText();
        String expectedWelcomeText = "Welcome " + loginUsername;
        Assert.assertEquals(expectedWelcomeText, actualWelcomeText);

        // 7. Logout
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2"))).click();
        } catch
            (StaleElementReferenceException e){
                driver.findElement(By.id("logout2")).click();
            }

        // 8. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void loginWithNotExistentUser() {
        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 3. Insert login username
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        emailInput.clear();
        emailInput.sendKeys(nonExistentUsername);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 4. Insert Login Password
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));
        passwordInput.clear();
        passwordInput.sendKeys(loginPassword);

        // 5. Click on Login button - Login Form
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log in']")));
        loginButton.click();

        // 6. Check user was not logged in
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("User does not exist."));
        alert.accept();

        // 7. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void loginWithWrongPassword() {
        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 3. Insert login username
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        emailInput.clear();
        emailInput.sendKeys(loginUsername);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 4. Insert Login Password
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));
        passwordInput.clear();
        passwordInput.sendKeys(wrongPassword);

        // 5. Click on Login button - Login Form
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log in']")));
        loginButton.click();

        // 5. Check user was not logged in
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Wrong password."));
        alert.accept();

        // 6. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void loginWithEmptyPassword() {
        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 3. Insert login username
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        emailInput.clear();
        emailInput.sendKeys(loginUsername);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 4. Click on Login button - Login Form without filling in the password
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log in']")));
        loginButton.click();

        // 5. Check user was not logged in
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Please fill out Username and Password."));
        alert.accept();

        // 6. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void loginWithEmptyEmail() {
        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 4. Without filling in Login Username, Insert Login Password
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));
        passwordInput.clear();
        passwordInput.sendKeys(wrongPassword);

        // 5. Click on Login button - Login Form
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log in']")));
        loginButton.click();

        // 6. Check user was not logged in
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Please fill out Username and Password."));
        alert.accept();

        // 7. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void loginWithEmptyCredentials() {
        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 3. Without filling in Username and Password, Click on Login button - Login Form
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log in']")));
        loginButton.click();

        // 4. Check user was not logged in
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Please fill out Username and Password."));
        alert.accept();

        // 5. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void closeModalWithoutLoggingInCloseButton() {
        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 3. Close Login Modal without logging in - Close button
        WebElement closeLoginModalButton = driver.findElement(By.xpath("//div[@id='logInModal']//button[text()='Close']"));
        closeLoginModalButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginModal));

        // 4. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void closeModalWithoutLoggingInXButton() {
        // 1. Click on Login button from HomePage
        WebElement homeLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        homeLoginButton.click();

        // 2. Wait for Login Modal to display
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModalLabel")));
        String actualLoginModalText = loginModal.getText();
        String expectedLoginModalText = "Log in";
        Assert.assertEquals(actualLoginModalText, expectedLoginModalText);

        // 3. Close Login Modal without logging in - Close button
        WebElement closeLoginModalXButton = driver.findElement(By.xpath("//div[@id='logInModal']//div[@class='modal-header']//button[@class='close']"));
        closeLoginModalXButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginModal));

        // 4. Check Login button is displayed
        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());

    }

    @After
    public void tearDown(){
         driver.close();
    }
}

