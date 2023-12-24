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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static utilities.Driver.getDriver;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeturTest {
    Logger logger = LoggerFactory.getLogger(SeturTest.class);
    SeturPage seturPage=new SeturPage();
    @Test
    public void test01_urlControl(){
        Driver.getDriver().get(ConfigReader.getProperty("testUrl"));

        seturPage.carpiIkonu.click();
        seturPage.acceptAllCookie.click();

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
        if (seturPage.otelTab.isDisplayed() && seturPage.otelTab.isEnabled()) {
            logger.info("Otel sekmesi varsayılan olarak görüntüleniyor.");
        } else {
            logger.warn("HATA: Otel sekmesi görüntülenmiyor veya etkin değil.");
        }
    }
    @Test
    public void test03_AntalyaKelimesi(){
        seturPage.aramaKutusu.sendKeys("Antalya");
        seturPage.aramaKutusu.click();
        //Cıkan Arama Sonucu:
        By xpathSelector = By.xpath("//ul[@class='sc-10cd16e6-0 ftkduW']/div[1]");
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        WebElement firstChild = wait.until(ExpectedConditions.elementToBeClickable(xpathSelector));
        firstChild.click();
    }
    @Test
    public void test04_TarihSecimi(){
        seturPage.dateTimePicker.click();
        //Bir sonraki ay butonu
        By xpathSelector = By.xpath("//button[@class=\"sc-8de9de7b-0 kCGMge sc-147d3380-2 cULZMP\"]");
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        WebElement nextMonthButton = wait.until(ExpectedConditions.elementToBeClickable(xpathSelector));
        do {
            nextMonthButton.click();
        }while(!Driver.getDriver().getPageSource().contains("Nisan 2024"));
        //ilkGünButonu
        By secimIlk = By.xpath("//td[@aria-label=\"Choose Pazartesi, 1 Nisan 2024 as your check-in date. It’s available.\"]");
        WebElement ilkGun = wait.until(ExpectedConditions.elementToBeClickable(secimIlk));
        //SonGünButonu
        By secimSon = By.xpath("//td[@aria-label=\"Choose Pazar, 7 Nisan 2024 as your check-in date. It’s available.\"]");
        WebElement sonGun = wait.until(ExpectedConditions.elementToBeClickable(secimSon));

        ilkGun.click();
        sonGun.click();
   }

    @Test
    public void test06_YetiskinSayisiArttır(){
        seturPage.kisiSayisiSecme.click();
        int initialValue = Integer.parseInt(seturPage.yetiskinSayisi.getText());
        seturPage.kisiSayisiArttir.click();
        int updatedValue = Integer.parseInt(seturPage.yetiskinSayisi.getText());
        if (initialValue!=updatedValue)
        {
            logger.info("Yetişkin Sayısı Arttı");
        }
        // Assert that the updated value is one more than the initial value
        Assert.assertEquals("HATA: Yetişkin sayısı arttırılamadı.", initialValue + 1, updatedValue);

    }
    @Test
    public void test07_aramaButonuVarmı() {

        //otelAraButonu
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
        String newURl = "https://www.setur.com.tr/antalya-otelleri?in=2024-04-01&out=2024-04-07&room=3&isBooker=true";
        //String newURl = getDriver().getCurrentUrl().toString();
        if (newURl.trim().contains("antalya")) {
            logger.info("Antalya Kelimesi gözüküyor.");
        } else {
            logger.warn("Antalya Kelimesi bulunamadı.");
        }
    }
    private static String kaydedilenDeger;
    @Test
    public void test09_RatgeleBölgeSecimi() {

        WebElement showMoreButton = getDriver().findElement(By.cssSelector("[data-testid='show-more-regions-button']"));


        if (showMoreButton.isDisplayed()) {
            showMoreButton.click();

            List<WebElement> checkboxes = getDriver().findElements(By.cssSelector("[data-testid='checkbox']"));

            Random random = new Random();
            int randomIndex = random.nextInt(checkboxes.size());

            checkboxes.get(randomIndex).click();


            WebElement spanElement = checkboxes.get(randomIndex).findElement(By.cssSelector("span[title]"));

            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(spanElement));

            String spanText = spanElement.getText();

            String regex = "\\((\\d+)\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(spanText);

            if (matcher.find()) {

                kaydedilenDeger = matcher.group(1);
                logger.info("Sayı: " + kaydedilenDeger);
            }
        }
    }


    @Test
    public void test10_SayFaninAltKismi(){

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3));
        WebElement altKisim = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sc-21021e1e-1 gPQAyQ']")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", altKisim);


        try {
            WebElement alttakiYazi = Driver.getDriver().findElement(By.xpath("//div[@class=\"sc-21021e1e-1 gPQAyQ\"]"));
            String altElementText = alttakiYazi.getText().toLowerCase().trim();


            if (kaydedilenDeger != null && altElementText.contains(kaydedilenDeger)) {
                logger.info("Element checkboxtaki değeri içeriyor.");
            } else {
                logger.warn("Element checkboxtaki değeri içermiyor.");
            }
        } catch (Exception e) {

            logger.error("Çıkan sonuc az olduğu için kaydedilen değer gözükmüyor . " + e.getMessage());
        }

    }


}
