package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SortGeoZonesTest {

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
    public void countriesByAlphabet() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        List<String> countriesString = getValueList(By.cssSelector(".row a"));

        for (int i = 0; i < countriesString.size(); i++) {
            assertEquals(countriesString.get(i), getSortedList(countriesString).get(i));
        }
    }

    @Test
    public void countriesGeoByAlphabet() {
        final String COUNT_GEO_PATH = "//tr[@class='row']/td[last()-1]";
        final String REF_COUNTRY_PATH = "//tr[@class='row']/td[last()-1]/preceding-sibling::td/a";
        final String HIDDEN_INPUT_TEXT = "#table-zones tr td input[type='hidden'][name*='[name]']";

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        int numMainMenu = getNumberOfElementsFound(By.xpath(COUNT_GEO_PATH));

        for (int i = 0; i < numMainMenu; i++) {

            if (Integer.parseInt(getElementWithIndex(By.xpath(COUNT_GEO_PATH), i).getText()) > 0) {
                getElementWithIndex(By.xpath(REF_COUNTRY_PATH), i).click();

                List<String> countriesGeoString = getAttributeValueList((By.cssSelector(HIDDEN_INPUT_TEXT)));

                for (int j = 0; j < countriesGeoString.size(); j++) {
                    assertEquals(countriesGeoString.get(j), getSortedList(countriesGeoString).get(j));
                }
                driver.navigate().back();
            }
        }
    }

    @Test
    public void geoZonesByAlphabet() {
        final String REF_GEO_PATH = "//tr[@class='row']/td[3]/a";
        final String OPTION_SELECTED_PATH = "//select[contains(@name,'[zone_code]')]/option[@selected='selected']";

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        int numMenu = getNumberOfElementsFound(By.xpath(REF_GEO_PATH));
        for (int i = 0; i < numMenu; i++) {
            getElementWithIndex(By.xpath(REF_GEO_PATH), i).click();
            List<String> geoList = new ArrayList<>();

            for (int j = 0; j < getNumberOfElementsFound(By.xpath(OPTION_SELECTED_PATH)); j++) {
                geoList.add(getElementWithIndex(By.xpath(OPTION_SELECTED_PATH), j).getText());
            }

            for (int k = 0; k < geoList.size(); k++) {
                assertEquals(geoList.get(k), getSortedList(geoList).get(k));
            }

            driver.navigate().back();
        }
    }

    private List<String> getValueList(By by) {
        List<String> result = new ArrayList<>();
        List<WebElement> elements = driver.findElements(by);

        for (WebElement element : elements) {
            if (!element.getText().isEmpty()) {
                result.add(element.getText());
            }
        }
        return result;
    }

    private List<String> getAttributeValueList(By by) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < driver.findElements(by).size(); i++) {
            if (!driver.findElements(by).get(i).getAttribute("value").isEmpty()) {
                result.add(driver.findElements(by).get(i).getAttribute("value"));
            }
        }
        return result;
    }

    private List<String> getSortedList(List<String> originalList) {
        List<String> result = new ArrayList<>(originalList);
        Collections.sort(result);
        return result;
    }

    private int getNumberOfElementsFound(By by) {
        return driver.findElements(by).size();
    }

    private WebElement getElementWithIndex(By by, int pos) {
        return driver.findElements(by).get(pos);
    }
}
