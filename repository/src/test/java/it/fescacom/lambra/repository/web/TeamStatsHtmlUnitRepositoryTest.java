package it.fescacom.lambra.repository.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by scanufe on 12/10/16.
 */
public class TeamStatsHtmlUnitRepositoryTest {
    @Test
    public void homePage() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
            Assert.assertEquals("HtmlUnit " + StringEscapeUtils.unescapeJava("\\u2013") + " Welcome to HtmlUnit", page.getTitleText());

            final String pageAsXml = page.asXml();
            Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

            final String pageAsText = page.asText();
            Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        }
    }
}