import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

    private void addProductToCart(String productName, String productHref){
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='" + productHref + "']")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", productLink);
        productLink.click();
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn.btn-success.btn-lg")));
        addToCartBtn.click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        // Navigate to home screen
        driver.findElement(By.id("nava")).click();
    }
    @Test
    public void addSingleItemToCart() {
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

        WebElement productNameCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tbody/tr/td[2]")));
        String productName = productNameCell.getText();
        Assert.assertTrue(productName.contains("Samsung galaxy s6"));

        // 4. Delete the item from cart
        WebElement deleteLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
        deleteLink.click();
        WebElement cartTable = driver.findElement(By.id("tbodyid"));
        wait.until(ExpectedConditions.invisibilityOf(cartTable));

        // 5. Logout and check Login button is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2"))).click();

        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void addMultipleItemsToCart() {
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

        // 2. Add item1 to the Cart
        addProductToCart("Samsung galaxy s6", "prod.html?idp_=1");

        // 3. Add item2 to the Cart
        addProductToCart("Nokia lumia 1520", "prod.html?idp_=2");

        // 4. Verify the products in cart
        WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartur")));
        cartLink.click();

        List<WebElement> productNames = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tbody/tr/td[2]")));
        List<String> cartItems = new ArrayList<>();
        for (WebElement item : productNames){
            cartItems.add(item.getText());
        }
        Assert.assertTrue("Samsung galaxy s6 should be in the cart", cartItems.contains("Samsung galaxy s6"));
        Assert.assertTrue("Nokia lumia 1520 should be in the cart", cartItems.contains("Nokia lumia 1520"));
        Assert.assertEquals("Cart should have 2 items", 2, cartItems.size());

        // 5. Delete the items from cart
        WebElement deleteLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
        deleteLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        deleteLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement cartTable = driver.findElement(By.id("tbodyid"));
        wait.until(ExpectedConditions.invisibilityOf(cartTable));

        // 6. Logout and check Login button is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2"))).click();

        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void totalPriceCalculation(){
        // 1. Login user
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

        // 2. Add 2 items to the cart
        addProductToCart("Samsung galaxy s6", "prod.html?idp_=1");
        addProductToCart("Nokia lumia 1520", "prod.html?idp_=2");

        // 3. Go to the cart and verify total price
        WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartur")));
        cartLink.click();
        List<WebElement> priceCells = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tbody/tr/td[3]")));
        int sum = 0;
        for (WebElement cell : priceCells){
            String priceText = cell.getText();
            int price = Integer.parseInt(priceText);
            sum = sum + price;
        }
        WebElement totalPrice = driver.findElement(By.id("totalp"));
        int displayedTotal = Integer.parseInt(totalPrice.getText());
        Assert.assertEquals("Total should match sum of products", sum, displayedTotal);

        // 4. Delete the items from cart
        WebElement deleteLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
        deleteLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        deleteLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement cartTable = driver.findElement(By.id("tbodyid"));
        wait.until(ExpectedConditions.invisibilityOf(cartTable));

        // 5. Logout and check Login button is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2"))).click();

        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());
    }

    @Test
    public void checkEmptyCartAfterDeletingAllItems(){
        // 1. Login user
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

        // 2. Add 2 items to the cart
        addProductToCart("Samsung galaxy s6", "prod.html?idp_=1");
        addProductToCart("Nexus 6", "prod.html?idp_=3");

        // 3. Delete the items from cart
        WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartur")));
        cartLink.click();

        WebElement deleteLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
        deleteLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        deleteLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        List<WebElement> tableRows = driver.findElements(By.xpath("//table//tbody/tr"));
        Assert.assertTrue("Cart should be empty", tableRows.isEmpty());

        // 4. Logout and check Login button is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2"))).click();

        WebElement homeLoginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(homeLoginButton2.isDisplayed());

    }

    @After
    public void tearDown(){
        driver.close();
    }
}
