package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LinksOpenInANewWindowTest {

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
    public void openAndCloseNewWindows() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.xpath("//a[contains(text(), 'Add New Country')]")).click();
        // список ссылок открывающихся в новом окне
        List<WebElement> aElements = driver.findElements(By.xpath("//i[contains(@class, 'a-external-link')]/.."));
        // идентификатор основного окна
        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows;
        for (WebElement aElement : aElements) {
            // сет идентификаторов всех открытых вкладок
            oldWindows = driver.getWindowHandles();
            // открываем новое окно
            aElement.click();
            // ожидание появления окна
            String newWindow = wait.until(anyWindowOtherThan(oldWindows));
            System.out.println("New ---> " + newWindow);
            // переключение на новое окно
            driver.switchTo().window(newWindow);
            // закрытие нового окна
            driver.close();
            // переключение в основное окно
            driver.switchTo().window(mainWindow);
            System.out.println("Main ---> " + mainWindow);
        }
    }

    // ожидание появления нового окна, идентификатор которого отсутствует в списке oldWindows
    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}

