package com.litecart.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CorrectProductPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testMethod(){
        final String REGULAR_PRICE_CAMPAIGN_PATH = "#box-campaigns > div > ul > li > a.link > div.price-wrapper > s";
        final String CAMPAIGN_PRICE_PATH = "#box-campaigns > div > ul > li > a.link > div.price-wrapper > strong";

        final String REGULAR_PRICE_PRODUCT_PATH = "#box-product > div.content > div.information > div.price-wrapper > s";
        final String CAMPAIGN_PRICE_PRODUCT_PATH = "#box-product > div.content > div.information > div.price-wrapper > strong";

        driver.get("http://localhost/litecart");
        String nameCampaigns = getTextElement("#box-campaigns > div > ul > li > a.link > div.name");
        String regularPriceCampaigns = getTextElement(REGULAR_PRICE_CAMPAIGN_PATH);
        String campaignPrice = getTextElement(CAMPAIGN_PRICE_PATH);
        String regularPriceCampaignsStyle = getAttributeElement(REGULAR_PRICE_CAMPAIGN_PATH);
        String campaignPriceStyle = getAttributeElement(CAMPAIGN_PRICE_PATH);

        driver.findElement(By.cssSelector("#box-campaigns a.link")).click();

        String nameProduct = getTextElement("#box-product > div:nth-child(1) > h1");
        String regularPriceProduct = getTextElement(REGULAR_PRICE_PRODUCT_PATH);
        String campaignPriceProduct = getTextElement(CAMPAIGN_PRICE_PRODUCT_PATH);
        String regularPriceProductStyle = getAttributeElement(REGULAR_PRICE_PRODUCT_PATH);
        String campaignPriceProductStyle = getAttributeElement(CAMPAIGN_PRICE_PRODUCT_PATH);

        assertEquals(nameCampaigns, nameProduct);
        assertEquals(regularPriceCampaigns, regularPriceProduct);
        assertEquals(campaignPrice, campaignPriceProduct);
        assertEquals(regularPriceCampaignsStyle, regularPriceProductStyle);
        assertEquals(campaignPriceStyle, campaignPriceProductStyle);

    }

    private String getTextElement(String locator){
        return driver.findElement(By.cssSelector(locator)).getText();
    }

    private String getAttributeElement(String locator){
        return driver.findElement(By.cssSelector(locator)).getAttribute("class");
    }



    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
