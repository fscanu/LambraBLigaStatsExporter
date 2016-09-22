package it.fescacom.lambra.web.accessor;

import lombok.Data;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ResourceBundle;

import static it.fescacom.lambra.utils.UsefulMethods.waitForIdElement;
import static it.fescacom.lambra.web.constants.ExtractorConstants.*;

/**
 * Created by scanufe on 11/09/16.
 */
@Data
public class MagicBAccessorImpl extends GenericAccessor {
    private static final Logger LOGGER = Logger.getLogger(MagicBAccessorImpl.class);


    private WebDriver getDriver(String url) {

        WebDriver driver = getFirefoxDriver();
        driver.get(url);
        return driver;
    }

    private WebDriver getFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "/Users/scanufe/Downloads/geckodriver");
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        return new FirefoxDriver(capabilities);
    }

    public WebDriver accessStatistichePage() {
        ResourceBundle accessorProps = ResourceBundle.getBundle("accessor");

        WebDriver driver = getDriver(accessorProps.getString(PROPS_URL));
        WebElement error = driver.findElement(By.xpath("//div[9]/center/button"));
        error.click();

        WebElement emailEl = driver.findElement(By.id(INPUT_EMAIL));
        emailEl.sendKeys(accessorProps.getString(PROPS_EMAIL));

        WebElement passwordEl = driver.findElement(By.id(INPUT_PASSWORD));
        passwordEl.sendKeys(accessorProps.getString(PROPS_PASSWORD));

        WebElement accedi = driver.findElement(By.name("submit"));
        accedi.click();

        WebElement statistiche = driver.findElement(By.xpath("//a[contains(text(),'STATISTICHE')]"));
        statistiche.click();

        waitForIdElement(driver, SECONDS_DEFAULT, "table_players");
        return driver;
    }
}
