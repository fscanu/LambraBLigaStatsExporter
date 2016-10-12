package it.fescacom.lambra.repository.web.connection;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

/**
 * Created by scanufe on 12/10/16.
 */
@Component("htmlUnitConnector")
public class HtmlUnitConnector implements BrowserConnector {
    public WebDriver getDriver() {
        return new org.openqa.selenium.htmlunit.HtmlUnitDriver();
    }
}
