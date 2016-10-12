package it.fescacom.lambra.repository.web.connection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by scanufe on 12/10/16.
 */
@Component("firefoxConnector")
public class FirefoxConnector implements BrowserConnector
{
    public WebDriver getDriver() {
        return getFirefoxDriver();
    }


    private WebDriver getFirefoxDriver() {

        System.setProperty("webdriver.gecko.driver", "/Users/scanufe/Downloads/geckodriver");
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        FirefoxBinary binary = new FirefoxBinary();
        File firefoxProfileFolder = new
                File("/Users/scanufe/Library/Application Support/Firefox/Profiles/waogs32f.lambrabliga/");
        FirefoxProfile profile = new FirefoxProfile(firefoxProfileFolder);
        profile.setAcceptUntrustedCertificates(true);
        return new org.openqa.selenium.firefox.FirefoxDriver(binary, profile, capabilities);
    }
}
