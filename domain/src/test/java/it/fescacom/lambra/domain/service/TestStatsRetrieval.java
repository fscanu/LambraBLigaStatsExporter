package it.fescacom.lambra.domain.service;

import it.fescacom.lambra.domain.CoachStats;
import it.fescacom.lambra.web.accessor.MagicBAccessorImpl;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static it.fescacom.lambra.utils.UsefulMethods.waitForIdElement;
import static it.fescacom.lambra.utils.constants.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by scanufe on 22/09/16.
 */
public class TestStatsRetrieval {

    @Test
    public void testCoachStats() {

        MagicBAccessorImpl accessor = new MagicBAccessorImpl();
        WebDriver driver = accessor.accessStatistichePage();

        String teamName = "Virtus Entella";
        int round = 2;
        CoachStats coachStats = findCoachStats(driver, teamName, round);
        assertNotNull(coachStats);
        assertEquals(teamName, coachStats.getTeamName());
        assertEquals("Breda, Roberto", coachStats.getName());
        driver.quit();
    }

    private CoachStats findCoachStats(WebDriver driver, String teamName, int round) {
        driver.get("http://magicb.gazzetta.it/statistiche-serieb-calcio");
        waitForIdElement(driver, SECONDS_DEFAULT, "table_players");
        WebElement coach = driver.findElement(By.xpath(TABLE_PLAYERS_STATS_COACH));
        coach.click();
        waitForIdElement(driver, SECONDS_DEFAULT, "table_coach");
        WebElement orderTeamsAlphabetically = driver.findElement(By.xpath(ORDER_TABLE_PLAYERS_STATS_COACH));
        orderTeamsAlphabetically.click();

        CoachStats coachStats = extractCoqchStatsFromPage(driver, teamName, round);
        if (null == coachStats) {
            //Maybe in the nextpage
            driver.findElement(By.id("table_coach_next")).click();
            waitForIdElement(driver, SECONDS_DEFAULT, "table_coach");
            coachStats = extractCoqchStatsFromPage(driver, teamName, round);
        }
        return coachStats;
    }

    private CoachStats extractCoqchStatsFromPage(WebDriver driver, String teamName, int round) {
        String coachName = null;
        Double vote = null;
        Double redCardMalus = null;

        WebElement table_coaches = driver.findElement(By.id("table_coach"));
        List<WebElement> allPageRows = table_coaches.findElements(By.tagName(TAG_TR));
        int i = 0;
        while (i < allPageRows.size()) {
            WebElement row = allPageRows.get(i);

            List<WebElement> cells = row.findElements(By.xpath(XPATH_TH_TD_IN_TABLE));
            WebElement[] webElements = cells.toArray(new WebElement[cells.size()]);
            if (i != 0) {
                coachName = webElements[0].getText();
                String team = webElements[2].getAttribute("data-order");
                if (teamName.equals(team)) {
                    String linkToCoachPage = webElements[0].findElement(By.tagName("a")).getAttribute("href");
                    driver.get(linkToCoachPage);
                    WebElement coachTable = driver.findElement(By.className("player-stats"));
                    List<WebElement> coachMatches = coachTable.findElements(By.tagName(TAG_TR));
                    WebElement[] rowsInTable = coachMatches.toArray(new WebElement[coachMatches.size()]);

                    List<WebElement> elements = rowsInTable[round].findElements(By.xpath(XPATH_TH_TD_IN_TABLE));
                    WebElement[] array = elements.toArray(new WebElement[elements.size()]);


                    vote = Double.valueOf(array[2].getText());
                    redCardMalus = Double.valueOf(array[3].getText());
                    return new CoachStats(coachName, "AL", teamName, vote, redCardMalus);
                }
            }
            i++;
        }
        return null;
    }
}
