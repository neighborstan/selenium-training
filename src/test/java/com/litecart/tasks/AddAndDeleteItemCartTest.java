package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AddAndDeleteItemCartTest {

    private final String ADD_TO_CART = "//button[@name='add_cart_product']";
    private final String COUNT_ITEM = "//span[@class='quantity']";
    private final String ENTER_TO_CART = "//a[contains(text(), 'Checkout')]";
    private final String REMOVE = "//button[contains(text(), 'Remove')]";
    private final String TABLE_IN_CART = "//table[@class='dataTable rounded-corners']";

    private final String PRODUCT_1 = "//div[@id='box-latest-products']//a[@class='link'][@title='Green Duck']";
    private final String PRODUCT_2 = "//div[@id='box-latest-products']//a[@class='link'][@title='Red Duck']";
    private final String PRODUCT_3 = "//div[@id='box-latest-products']//a[@class='link'][@title='Blue Duck']";

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        driver.get("http://localhost/litecart");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void AddAndRemoveProductFromCart() {
        // Product 1 add to cart
        addItemToCart(By.xpath(PRODUCT_1));
        waitChangeCountItem();

        // Return to the previous page
        driver.navigate().back();

        // Product 2 add to cart
        addItemToCart(By.xpath(PRODUCT_2));
        waitChangeCountItem();

        // Return to the previous page
        driver.navigate().back();

        // Product 3 add to cart
        addItemToCart(By.xpath(PRODUCT_3));
        waitChangeCountItem();

        // Return to the previous page
        driver.navigate().back();

        // Enter to cart
        driver.findElement(By.xpath(ENTER_TO_CART)).click();

        // Product 1 remove from cart
        removeItemFromCart();

        // Product 2 remove from cart
        removeItemFromCart();

        // Product 3 remove from cart
        removeItemFromCart();

    }

    public void removeItemFromCart() {
        driver.findElement(By.xpath(REMOVE)).click();
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath(TABLE_IN_CART))));
    }

    public void addItemToCart(By by) {
        driver.findElement(by).click();
        driver.findElement(By.xpath(ADD_TO_CART)).click();
    }

    public void waitChangeCountItem() {
        WebElement element = driver.findElement(By.xpath(COUNT_ITEM));
        int countItem = Integer.parseInt(element.getText());
        int changeCountItem = countItem;
        while (changeCountItem == countItem) {
            changeCountItem = Integer.parseInt(driver.findElement(By.xpath(COUNT_ITEM)).getText());
        }
    }
}
