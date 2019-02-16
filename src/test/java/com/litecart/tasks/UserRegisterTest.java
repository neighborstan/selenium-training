package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UserRegisterTest {

    Random random = new Random();

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
    public void userRegister() {
        String firstName = getFirstName();
        String lastName = getLastName();
        String address = getAddressOrCity();
        String postcode = getPostcode();
        String city = getAddressOrCity();
        String email = getEmailRandom(10);
        String phone = getPhoneNumber();
        String pass = getPassword();

        Map<String, String> map = new HashMap<>();
        map.put("//input[@name='firstname']", firstName);
        map.put("//input[@name='lastname']", lastName);
        map.put("//input[@name='address1']", address);
        map.put("//input[@name='postcode']", postcode);
        map.put("//input[@name='city']", city);
        map.put("//input[@name='email']", email);
        map.put("//input[@name='phone']", phone);
        map.put("//input[@name='password']", pass);
        map.put("//input[@name='confirmed_password']", pass);

        // New customers click reference
        driver.findElement(By.xpath("//a[contains(text(), 'New customers click here')]")).click();

        // Form Filling
        registrationFormFilling(map);

        // Click button 'Create Account'
        driver.findElement(By.xpath("//button[contains(text(), 'Create Account')]")).click();

        // Click button 'Logout'
        driver.findElement(By.xpath("//a[contains(text(), 'Logout')]")).click();

        // Send valid email/pass for login fields
        driver.findElement(By.xpath("//td[contains(text(), 'Email Address')]/input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//td[contains(text(), 'Password')]/input[@name='password']")).sendKeys(pass);

        // Click button 'Login'
        driver.findElement(By.xpath("//span[@class='button-set']/button[@name='login']")).click();

        // Click button 'Logout'
        driver.findElement(By.xpath("//a[contains(text(), 'Logout')]")).click();

    }

    public void registrationFormFilling(Map<String, String> stringMap){
        for (Map.Entry entry : stringMap.entrySet()) {
            driver.findElement(By.xpath(String.valueOf(entry.getKey()))).sendKeys((CharSequence) entry.getValue());
        }
        driver.findElement(By.xpath("//input[@name='captcha']")).sendKeys(getCaptcha());
    }

    public String getEmailRandom(int max) {
        char[] symbols = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        String endEmail = "@example.com";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < max; i++) {
            result.append(symbols[random.nextInt(36)]);
        }
        result.append(endEmail);
        return result.toString();
    }

    public String getFirstName() {
        String[] firstName = "Дмитрий, Максим, Даниил".split(", ");
        return firstName[random.nextInt(firstName.length)];

    }

    public String getLastName() {
        String[] lastName = "Смирнов, Иванов, Кузнецов".split(", ");
        return lastName[random.nextInt(lastName.length)];
    }

    public String getAddressOrCity() {
        String[] address = "Долинск, Домодедово, Дубна, Дубовка".split(", ");
        return address[random.nextInt(address.length)];
    }

    public String getPostcode() {
        String[] postcode = "000000, 111111, 222222, 333333, 444444".split(", ");
        return postcode[random.nextInt(postcode.length)];
    }

    public String getPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder("+7");
        char[] symbolsNum = "0123456789".toCharArray();
        for (int i = 0; i < 10; i++) {
            phoneNumber.append(symbolsNum[random.nextInt(10)]);
        }
        return phoneNumber.toString();
    }

    public String getPassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            password.append(random.nextInt(10));
        }
        return password.toString();
    }

    public String getCaptcha() {
        return JOptionPane.showInputDialog("Please enter the captcha value:");
    }

}
