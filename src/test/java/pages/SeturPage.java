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


}
