package it.fescacom.lambra.repository.web;

import it.fescacom.lambra.domain.stats.CoachStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import it.fescacom.lambra.repository.TeamStatsRepository;
import it.fescacom.lambra.repository.serialization.util.SerializationUtil;
import lombok.Data;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.fescacom.lambra.common.UsefulMethods.waitForIdElement;
import static it.fescacom.lambra.common.UsefulMethods.waitForXpathElement;
import static it.fescacom.lambra.common.constants.Constants.*;
import static it.fescacom.lambra.repository.web.constant.WebElementConstants.*;
import static it.fescacom.lambra.repository.web.utils.DataExtractorUtility.collectTeamStatsData;

/**
 * Created by scanufe on 11/09/16.
 */
@Service("teamStatsRepository")
public class TeamStatsRepositoryWebImpl implements TeamStatsRepository {


    private static final Logger LOGGER = Logger.getLogger(TeamStatsRepositoryWebImpl.class);
    private WebDriver driver;

    private final String accessorUrl;

    private final String accessorEmail;

    private final String accessorPassword;

    private boolean firstAccess = true;

    public TeamStatsRepositoryWebImpl(
            @Value("${accessor.url}") String accessorUrl,
            @Value("${accessor.email}") String accessorEmail,
            @Value("${accessor.password}") String accessorPassword) {
        this.accessorUrl = accessorUrl;
        this.accessorEmail = accessorEmail;
        this.accessorPassword = accessorPassword;
        this.firstAccess = true;
    }

    private WebDriver getDriver(String url) {
        if (null == driver) {
            driver = getFirefoxDriver();
        }
        driver.get(url);
        return driver;
    }

    private WebDriver getFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "/Users/scanufe/Downloads/geckodriver");
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        return new FirefoxDriver(capabilities);
    }

    private WebDriver accessStatistichePage() {

        WebDriver driver = getDriver(accessorUrl);
        if (firstAccess) {
            firstAccess = false;
            WebElement error = driver.findElement(By.xpath("//div[9]/center/button"));
            error.click();

            WebElement emailEl = driver.findElement(By.id(INPUT_EMAIL));
            emailEl.sendKeys(accessorEmail);

            WebElement passwordEl = driver.findElement(By.id(INPUT_PASSWORD));
            passwordEl.sendKeys(accessorPassword);

            WebElement accedi = driver.findElement(By.name("submit"));
            accedi.click();
        }

        WebElement statistiche = driver.findElement(By.xpath("//a[contains(text(),'STATISTICHE')]"));
        statistiche.click();
        return driver;
    }

    public Map<String, TeamStats> findAllTeamStats(int round) {
        Map<String, TeamStats> teamStatsMap = new HashMap<String, TeamStats>();

        final WebDriver driver = accessStatistichePage();

        waitForIdElement(driver, SECONDS_DEFAULT, "table_players");

        List<TeamInfo> teamInfos = getIDsForTheTeamsIPage(driver);
        for (TeamInfo teamInfo : teamInfos) {

            //find the right round for the coach
            driver.get(accessorUrl);
            final String teamName = teamInfo.getTeamName();
            CoachStats coachStats = collectCoachStats(driver, teamName, round);

            driver.get(teamInfo.getHref());

            findTheRightRound(round, driver);

            WebElement regulars = driver.findElement(By.xpath(TABLE_PLAYERS_STATS_REGULARS));
            WebElement reserves = driver.findElement(By.xpath(TABLE_PLAYERS_STATS_RESERVES));

            TeamStats teamStatsPlayers = collectTeamStatsData(teamName, coachStats, regulars, reserves);
            teamStatsMap.put(teamName, teamStatsPlayers);

            LOGGER.info(teamStatsPlayers.getTeamName());

        }
        try {
            SerializationUtil.serialize(teamStatsMap, round + "_giornata_LambraBLiga.ser");
        } catch (IOException e) {
            LOGGER.error("Error during serialization");
        }
        return teamStatsMap;
    }

    private CoachStats collectCoachStats(WebDriver driver, String teamName, int round) {
        waitForIdElement(driver, SECONDS_DEFAULT, "table_players");
        WebElement coach = driver.findElement(By.xpath(TABLE_PLAYERS_STATS_COACH));
        coach.click();
        waitForIdElement(driver, SECONDS_DEFAULT, "table_coach");
        WebElement orderTeamsAlphabetically = driver.findElement(By.xpath(ORDER_TABLE_PLAYERS_STATS_COACH));
        orderTeamsAlphabetically.click();

        CoachStats coachStats = extractCoachStatsFromPage(driver, teamName, round);
        if (null == coachStats) {
            //Maybe in the nextpage
            driver.findElement(By.id("table_coach_next")).click();
            waitForIdElement(driver, SECONDS_DEFAULT, "table_coach");
            coachStats = extractCoachStatsFromPage(driver, teamName, round);
        }
        LOGGER.info("Trovato Allentore della: " + coachStats.getTeamName());
        return coachStats;
    }

    private void findTheRightRound(int round, WebDriver driver) {
        WebElement round_number;

        waitForIdElement(driver, SECONDS_DEFAULT, ROUND_NUMBER);
        Integer roundNumberValue;
        round_number = driver.findElement(By.id(ROUND_NUMBER));
        roundNumberValue = Integer.valueOf(round_number.getText());
        findRound(driver);
        while (roundNumberValue != round) {
            if (roundNumberValue < round) {
                driver.findElement(By.xpath(ARROW_NEXT)).click();
            } else {
                driver.findElement(By.xpath(ARROW_PREV)).click();
            }
            try {
                round_number = driver.findElement(By.id(ROUND_NUMBER));
                roundNumberValue = Integer.valueOf(round_number.getText());
            } catch (org.openqa.selenium.StaleElementReferenceException e) {

                waitForIdElement(driver, SECONDS_DEFAULT, ROUND_NUMBER);
                round_number = driver.findElement(By.id(ROUND_NUMBER));
                roundNumberValue = Integer.valueOf(round_number.getText());

            }
            findRound(driver);
        }
    }

    private CoachStats extractCoachStatsFromPage(WebDriver driver, String teamName, int round) {

        waitForIdElement(driver, SECONDS_DEFAULT, "table_coach");
        WebElement table_coaches = driver.findElement(By.id("table_coach"));
        List<WebElement> allPageRows = table_coaches.findElements(By.tagName(TAG_TR));
        return getCoachStatsByRound(driver, teamName, round, allPageRows);
    }

    private CoachStats getCoachStatsByRound(WebDriver driver, String teamName, int round, List<WebElement> allPageRows) {
        String coachName;
        int i = 0;
        while (i < allPageRows.size()) {
            WebElement row = allPageRows.get(i);

            waitForXpathElement(driver, SECONDS_DEFAULT, XPATH_TH_TD_IN_TABLE);
            List<WebElement> cells = row.findElements(By.xpath(XPATH_TH_TD_IN_TABLE));
            WebElement[] webElements = cells.toArray(new WebElement[cells.size()]);
            if (i != 0) {
                coachName = webElements[0].getText();
                String team = webElements[2].getAttribute("data-order");
                if (teamName.equals(team)) {
                    String linkToCoachPage = webElements[0].findElement(By.tagName("a")).getAttribute("href");
                    driver.get(linkToCoachPage);
                    return extractCoachStatsFromPageByRound(teamName, round, coachName);
                }
            }
            i++;
        }
        return null;
    }

    private CoachStats extractCoachStatsFromPageByRound(String teamName, int round, String coachName) {
        Double vote = 0d;
        Double redCardMalus = 0d;

        try {
            WebElement coachTable = driver.findElement(By.className("player-stats"));

            List<WebElement> coachMatches = coachTable.findElements(By.tagName(TAG_TR));
            WebElement[] matches = coachMatches.toArray(new WebElement[coachMatches.size()]);

            for (int roundIndex = 1; roundIndex < matches.length; roundIndex++) {
                WebElement match = matches[roundIndex];
                List<WebElement> matchStats = match.findElements(By.xpath(XPATH_TH_TD_IN_TABLE));
                WebElement[] matchStatsarray = matchStats.toArray(new WebElement[matchStats.size()]);

                Integer roundFromRow = Integer.valueOf(matchStatsarray[0].getText());
                if (roundFromRow.equals(round)) {
                    vote = Double.valueOf(matchStatsarray[2].getText());
                    redCardMalus = Double.valueOf(matchStatsarray[3].getText());

                    return new CoachStats(coachName, "AL", teamName, vote, redCardMalus);
                }
            }
            return new CoachStats(coachName, "AL", teamName, vote, redCardMalus);

        } catch (org.openqa.selenium.NoSuchElementException ene) {
            return new CoachStats("ALLENATORE - NUOVO ANCORA SENZA VOTO", "AL", teamName, vote, redCardMalus);
        }

    }

    private void findRound(WebDriver driver) {
        try {
            driver.findElement(By.xpath(ARROW_PREV));
        } catch (org.openqa.selenium.NoSuchElementException nse) {
            LOGGER.error("Waiting for the element!!!!ARROW_PREV");
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            waitForXpathElement(driver, SECONDS_DEFAULT, ARROW_PREV);
            driver.findElement(By.xpath(ARROW_PREV));
        }
        try {
            driver.findElement(By.xpath(ARROW_NEXT));
        } catch (org.openqa.selenium.NoSuchElementException nse) {
            LOGGER.error("Waiting for the element!!!!ARROW_NEXT");
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            waitForXpathElement(driver, SECONDS_DEFAULT, ARROW_NEXT);
            driver.findElement(By.xpath(ARROW_NEXT));

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
