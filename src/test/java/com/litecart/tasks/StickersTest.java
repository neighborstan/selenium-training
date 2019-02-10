package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class StickersTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void presenceStickersInGoods() {
        driver.get("http://localhost/litecart/");

        List<WebElement> products = driver.findElements(By.cssSelector("li.product"));
        for (WebElement product : products) {
            assertEquals(product.findElements(By.cssSelector("div.image-wrapper div.sticker")).size(), 1);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
