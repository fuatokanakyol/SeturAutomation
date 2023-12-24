package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class SeturPage {

    public SeturPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }
    @FindBy(xpath = "(//span[@class=\"ins-close-button\"])[2]")
    public WebElement carpiIkonu;

    @FindBy(xpath = "//*[@id=\"CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll\"]")
    public WebElement acceptAllCookie;

    @FindBy(xpath = "//input[@aria-label=\"search-input\"]")
    public WebElement aramaKutusu;

    @FindBy(xpath = "//button[@class=\"sc-5391ca11-1 crUBM\"]")
    public WebElement otelTab;

    @FindBy(xpath = "//div[@class='sc-d78bffa1-3 jGqWuQ']")
    public WebElement dateTimePicker;

    @FindBy(xpath = "//div[@class=\"sc-b2c3f6ee-18 bRTqaJ\"]")
    public WebElement kisiSayisiSecme;

    @FindBy(xpath = "(//span[@data-testid=\"count-label\"])[1]")
    public WebElement yetiskinSayisi;
    @FindBy(xpath = "(//div[@class='sc-423a98f0-0 iibhk']//button[@data-testid='increment-button'])[1]")
    public WebElement kisiSayisiArttir;
}
