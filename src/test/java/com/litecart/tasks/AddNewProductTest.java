package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class AddNewProductTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void addNewProduct() {
        driver.findElement(By.xpath("//li[@id='app-']/a[span[contains(text(), 'Catalog')]]")).click();
        driver.findElement(By.xpath("//td[@id='content']//a[contains(text(), 'Add New Product')]")).click();

        // General
        driver.findElement(By.xpath("//div[@id='tab-general']//label[contains(text(), 'Enabled')]/input[@name='status']")).click();
        driver.findElement(By.xpath("//td[strong[contains(text(), 'Name')]]/span[@class='input-wrapper']/input")).sendKeys("test name");
        driver.findElement(By.xpath("//td[strong[contains(text(), 'Code')]]/input")).sendKeys("testcode");
        driver.findElement(By.xpath("//td[strong[contains(text(), 'Categories')]]//tbody/tr[1]/td/input")).click();
        driver.findElement(By.xpath("//td[strong[contains(text(), 'Categories')]]//tbody/tr[2]/td/input")).click();
        driver.findElement(By.xpath("//td[strong[contains(text(), 'Quantity')]]/input")).clear();
        driver.findElement(By.xpath("//td[strong[contains(text(), 'Quantity')]]/input")).sendKeys("30.00");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys("G://addNewDuck.png");
        driver.findElement(By.xpath("//input[@type='date'][@name='date_valid_from']")).sendKeys("01.01.1980");
        driver.findElement(By.xpath("//input[@type='date'][@name='date_valid_to']")).sendKeys("01.01.1980");

        // Information
        driver.findElement(By.xpath("//a[contains(text(), 'Information')]")).click();
        Select selectManufacturer = new Select(driver.findElement(By.xpath("//select[@name='manufacturer_id']")));
        selectManufacturer.selectByValue("1");
        driver.findElement(By.xpath("//strong[contains(text(), 'Keywords')]/following-sibling::input[@name='keywords']")).sendKeys("test keywords");
        driver.findElement(By.xpath("//strong[contains(text(), 'Short Description')]/following-sibling::span/input")).sendKeys("test short description");
        driver.findElement(By.xpath("//div[@class='trumbowyg-button-pane']/following-sibling::div[@class='trumbowyg-editor']")).sendKeys("test description");
        driver.findElement(By.xpath("//strong[contains(text(), 'Head Title')]/following-sibling::span/input")).sendKeys("test head title");
        driver.findElement(By.xpath("//strong[contains(text(), 'Meta Description')]/following-sibling::span/input")).sendKeys("test meta description");

        // Prices
        driver.findElement(By.xpath("//a[contains(text(), 'Prices')]")).click();
        driver.findElement(By.xpath("//strong[contains(text(), 'Purchase Price')]/following-sibling::input")).clear();
        driver.findElement(By.xpath("//strong[contains(text(), 'Purchase Price')]/following-sibling::input")).sendKeys("30.00");
        Select selectMoney = new Select(driver.findElement(By.xpath("//strong[contains(text(), 'Purchase Price')]/following-sibling::select")));
        selectMoney.selectByValue("EUR");
        driver.findElement(By.xpath("//strong[contains(text(), 'USD')]/preceding-sibling::input")).sendKeys("30.00");
        driver.findElement(By.xpath("//strong[contains(text(), 'EUR')]/preceding-sibling::input")).sendKeys("20.00");

        driver.findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        assertTrue(isElementPresent(driver, By.xpath("//tr[@class='footer']/preceding-sibling::tr//a[contains(text(), 'test name')]")));
//        JOptionPane.showInputDialog("Please enter the captcha value:");
    }

    private boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

}
