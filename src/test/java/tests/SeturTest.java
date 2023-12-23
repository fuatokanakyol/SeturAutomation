package tests;

import org.junit.*;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.SeturPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runners.MethodSorters;


import static utilities.Driver.getDriver;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class SeturTest {
    /* Get the class name to be printed on */
    Logger logger = LoggerFactory.getLogger(SeturTest.class);




    @Test
    public void test01_urlControl(){

        logger.info("Info log message");


        Driver.getDriver().get(ConfigReader.getProperty("testUrl"));
        SeturPage seturPage=new SeturPage();
        seturPage.carpiIkonu.click();
        seturPage.acceptAllCookie.click();

        //JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        //jsExecutor.executeScript("arguments[0].click();", seturPage.carpiIkonu);
        //jsExecutor.executeScript("arguments[0].click();", seturPage.acceptAllCookie);




        String expectedUrl = "https://www.setur.com.tr/";
        String actualUrl =Driver.getDriver().getCurrentUrl();
        if (actualUrl.equals(expectedUrl)) {
            logger.info("URL doğru.");
        } else {
            logger.warn("HATA: URL beklenen değerle uyuşmuyor. Beklenen: " + expectedUrl + ", Gerçek: " + actualUrl);
        }

    }

    @Test
    public void test02_OtelTabControl(){

        SeturPage seturPage=new SeturPage();
        WebElement otelTab=Driver.getDriver().findElement(By.xpath("//button[@class=\"sc-5391ca11-1 crUBM\"]"));
        if (otelTab.isDisplayed() && otelTab.isEnabled()) {
            logger.info("Otel sekmesi varsayılan olarak görüntüleniyor.");
        } else {
            logger.warn("HATA: Otel sekmesi görüntülenmiyor veya etkin değil.");
        }
    }
    @Test
    public void test03_AntalyaKelimesi(){

        SeturPage seturPage=new SeturPage();
        seturPage.aramaKutusu.sendKeys("Antalya");
        seturPage.aramaKutusu.click();



        By xpathSelector = By.xpath("//ul[@class='sc-10cd16e6-0 ftkduW']/div[1]");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        WebElement firstChild = wait.until(ExpectedConditions.elementToBeClickable(xpathSelector));

        firstChild.click();
    }
    @Test
    public void test04_TarihSecimi(){


        WebElement dateTimePicker = Driver.getDriver().findElement(By.xpath("//div[@class='sc-d78bffa1-3 jGqWuQ']"));
        dateTimePicker.click();


        By xpathSelector = By.xpath("//button[@class=\"sc-8de9de7b-0 kCGMge sc-147d3380-2 cULZMP\"]");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        WebElement nextMonthButton = wait.until(ExpectedConditions.elementToBeClickable(xpathSelector));

        do {
            nextMonthButton.click();
        }while(!Driver.getDriver().getPageSource().contains("Nisan 2024"));


        By secimIlk = By.xpath("//td[@aria-label=\"Choose Pazartesi, 1 Nisan 2024 as your check-in date. It’s available.\"]");

        WebElement ilkGun = wait.until(ExpectedConditions.elementToBeClickable(secimIlk));


        By secimSon = By.xpath("//td[@aria-label=\"Choose Pazar, 7 Nisan 2024 as your check-in date. It’s available.\"]");
        WebElement sonGun = wait.until(ExpectedConditions.elementToBeClickable(secimSon));

        ilkGun.click();
        sonGun.click();


   }

    @Test
    public void test06_YetiskinSayisiArttır(){

        WebElement KisiButonu=Driver.getDriver().findElement(By.xpath("//div[@class=\"sc-b2c3f6ee-18 bRTqaJ\"]"));
        KisiButonu.click();

        WebElement countElement = Driver.getDriver().findElement(By.xpath("(//span[@data-testid=\"count-label\"])[1]"));
        int initialValue = Integer.parseInt(countElement.getText());

        // Click the increment button
        WebElement incrementButton = Driver.getDriver().findElement(By.xpath("(//div[@class='sc-423a98f0-0 iibhk']//button[@data-testid='increment-button'])[1]"));
        incrementButton.click();

        // Get the updated value
        int updatedValue = Integer.parseInt(countElement.getText());

        // Assert that the updated value is one more than the initial value
        Assert.assertEquals("HATA: Yetişkin sayısı arttırılamadı.", initialValue + 1, updatedValue);

    }
    @Test
    public void test07_aramaButonuVarmı() {
        WebElement searchElement = Driver.getDriver().findElement(By.xpath("//button[@class=\"sc-8de9de7b-0 dYTYAP\"]"));
        if (searchElement.isDisplayed()) {
            logger.info("Arama butonu gözüküyor.");
            searchElement.click();
        } else {
            logger.warn("Arama butonu bulunamadı.");
        }
    }

    @Test
    public void test08_antalyaKelimesiVarMı(){
        WebElement anntalyaKelimesi=Driver.getDriver().findElement(By.xpath("//input[@data-testid=\"select-location-box-input\"]"));
        if (anntalyaKelimesi.isDisplayed()) {
            logger.info("Antalya Kelimesi gözüküyor.");
            anntalyaKelimesi.click();
        } else {
            logger.warn("Antalya Kelimesi bulunamadı.");
        }
    }
    private static String deger;
    @Test
    public void test09_RatgeleBölgeSecimi() {

        WebElement checkBoxesDiv = Driver.getDriver().findElement(By.xpath("//div[@class=\"sc-2569635-2 PSzMH\"]"));
        List<WebElement> chcBoxes = getDriver().findElements(By.xpath("//div[@class=\"sc-e4b3cd20-0 ihtOYP\"]"));
        Random rnd = new Random(chcBoxes.size());
        WebElement checked = chcBoxes.get(rnd.nextInt(chcBoxes.size()));
        checked.click();
        deger = extractNumber(checked.getText());
        logger.info("Okunan Değer: " + deger);
    }


    @Test
    public void test10_SayFaninAltKismi(){
        WebElement altKisim=Driver.getDriver().findElement(By.xpath("//div[@class=\"sc-21021e1e-1 gPQAyQ\"]"));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", altKisim);

        WebElement alttakiYazi=Driver.getDriver().findElement(By.xpath("//div[@class=\"sc-21021e1e-1 gPQAyQ\"]"));


        String altElementText = alttakiYazi.getText();


        if (deger != null && altElementText.contains(deger)) {
            logger.info("Element checkboxtaki değeri içeriyor.");
        } else {
            logger.warn("Element checkboxtaki değeri içermiyor.");
        }
        logger.info("Bir hata oluştu");
    }
    private String extractNumber(String textWithNumberInParentheses) {
        // Örnek: "Side (52)" --> "52"
        String extractedNumber = textWithNumberInParentheses.replaceAll("[^0-9]", "");
        return extractedNumber;
    }

}
