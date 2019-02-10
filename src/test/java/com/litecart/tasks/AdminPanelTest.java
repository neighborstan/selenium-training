package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class AdminPanelTest {

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

    @Test
    public void presenceReferencesAndHeaders() {
        int numMainMenu = getNumberOfElementsFound(By.cssSelector("#app- > a"));
        for (int i = 0; i < numMainMenu; i++) {
            getElementWithIndex(By.cssSelector("#app- > a"), i).click();
            driver.findElement(By.cssSelector("#content h1"));

            int numSubMenu = getNumberOfElementsFound(By.cssSelector("ul.docs li[id^=doc-] a"));
            for (int j = 0; j < numSubMenu; j++) {
                getElementWithIndex(By.cssSelector("ul.docs li[id^=doc-] a"), j).click();
                driver.findElement(By.cssSelector("#content h1"));

            }
        }
    }

    private int getNumberOfElementsFound(By by) {
        return driver.findElements(by).size();
    }

    private WebElement getElementWithIndex(By by, int pos) {
        return driver.findElements(by).get(pos);
    }

    private boolean existsElement(String locator) {
        try {
            driver.findElement(By.cssSelector(locator));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
