package it.fescacom.lambra.domain.service;

import it.fescacom.lambra.domain.TeamStats;
import it.fescacom.lambra.web.accessor.MagicBAccessorImpl;
import lombok.Data;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static it.fescacom.lambra.domain.service.utils.DataExtractorUtility.extractTeamStatsData;
import static it.fescacom.lambra.utils.UsefulMethods.waitForIdElement;
import static it.fescacom.lambra.utils.UsefulMethods.waitForXpathElement;
import static it.fescacom.lambra.utils.constants.ExtractorConstants.*;
import static it.fescacom.lambra.web.constants.ExtractorConstants.PROPS_URL;
import static it.fescacom.lambra.web.constants.ExtractorConstants.SECONDS_DEFAULT;

/**
 * Created by scanufe on 21/09/16.
 */
public class RoundByRoundLeagueStatsCollector implements ExportService {
    private static final Logger LOGGER = Logger.getLogger(RoundByRoundLeagueStatsCollector.class);

    public List<TeamStats> exportTeamStats(int round) {

        ResourceBundle accessorProps = ResourceBundle.getBundle("properties.accessor");
        List<TeamStats> teamStatsList = new ArrayList<TeamStats>();
        MagicBAccessorImpl accessor = new MagicBAccessorImpl();
        WebDriver driver = accessor.accessStatistichePage();

        List<TeamInfo> teamInfos = getIDsForTheTeamsIPage(driver);
        for (TeamInfo teamInfo : teamInfos) {
            driver.get(teamInfo.getHref());

            findTheRightRound(round, driver);

            WebElement regulars = driver.findElement(By.xpath(TABLE_PLAYERS_STATS_REGULARS));
            WebElement reserves = driver.findElement(By.xpath(TABLE_PLAYERS_STATS_RESERVES));

            //find the right round for the coach
            driver.get(accessorProps.getString(PROPS_URL));
            WebElement coach = driver.findElement(By.xpath(TABLE_PLAYERS_STATS_COACH));

            TeamStats teamStatsPlayers = extractTeamStatsData(teamInfo.getTeamName(), regulars, reserves, coach);
            teamStatsList.add(teamStatsPlayers);

            LOGGER.info(teamStatsPlayers.getTeamName());

        }
        return teamStatsList;
    }

    private void findTheRightRound(int round, WebDriver driver) {
        WebElement round_number;

        waitForIdElement(driver, SECONDS_DEFAULT, "round_number");
        Integer roundNumberValue;
        round_number = driver.findElement(By.id("round_number"));
        roundNumberValue = Integer.valueOf(round_number.getText());
        try {
            driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div/span/i"));
        } catch (org.openqa.selenium.NoSuchElementException nse) {
            LOGGER.error("Error element not recognized");
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            waitForXpathElement(driver, SECONDS_DEFAULT, "//div[@id='team_round_box']/div[2]/div/div/span/i");
            driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div/span/i"));
        }
        try {
            driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div[3]/span/i"));
        } catch (org.openqa.selenium.NoSuchElementException nse) {
            LOGGER.error("Error element not recognized");
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            waitForXpathElement(driver, SECONDS_DEFAULT, "//div[@id='team_round_box']/div[2]/div/div[3]/span/i");
            driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div[3]/span/i"));
        }
        while (roundNumberValue != round) {
            if (roundNumberValue < round) {
                driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div[3]/span/i")).click();
            } else {
                driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div/span/i")).click();
            }
            try {
                round_number = driver.findElement(By.id("round_number"));
                roundNumberValue = Integer.valueOf(round_number.getText());
            } catch (org.openqa.selenium.StaleElementReferenceException e) {

                waitForIdElement(driver, SECONDS_DEFAULT, "round_number");
                round_number = driver.findElement(By.id("round_number"));
                roundNumberValue = Integer.valueOf(round_number.getText());

            }
            try {
                driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div/span/i"));
            } catch (org.openqa.selenium.NoSuchElementException nse) {
                LOGGER.error("Error element not recognized");
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                waitForXpathElement(driver, SECONDS_DEFAULT, "//div[@id='team_round_box']/div[2]/div/div/span/i");
                driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div/span/i"));
            }
            try {
                driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div[3]/span/i"));
            } catch (org.openqa.selenium.NoSuchElementException nse) {
                LOGGER.error("Error element not recognized");
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                waitForXpathElement(driver, SECONDS_DEFAULT, "//div[@id='team_round_box']/div[2]/div/div[3]/span/i");
                driver.findElement(By.xpath("//div[@id='team_round_box']/div[2]/div/div[3]/span/i"));
            }
        }
    }

    private List<TeamInfo> getIDsForTheTeamsIPage(WebDriver driver) {
        List<TeamInfo> teamInfos = new ArrayList<TeamInfo>();
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.inline-list li a"));
        for (WebElement element : elements) {

            String href = element.getAttribute("href");
            String title = element.getAttribute("title");
            teamInfos.add(new TeamInfo(href, title));
        }
        return teamInfos;
    }

    @Data
    private class TeamInfo {
        final String href;
        final String teamName;
    }
}
