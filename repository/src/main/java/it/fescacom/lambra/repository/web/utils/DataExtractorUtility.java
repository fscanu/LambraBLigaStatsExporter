package it.fescacom.lambra.repository.web.utils;

import it.fescacom.lambra.domain.stats.CoachStats;
import it.fescacom.lambra.domain.stats.PlayersStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;

import static it.fescacom.lambra.common.UsefulMethods.waitForIdElement;
import static it.fescacom.lambra.common.constants.Constants.*;

/**
 * Created by scanufe on 21/09/16.
 */
public class DataExtractorUtility {

    public static TeamStats collectTeamStatsData(String teamName, CoachStats coachStats, WebDriver driver, WebElement... tablePlayers) {
        HashSet<PlayersStats> playersStatses = new HashSet<PlayersStats>();
        for (WebElement tablePlayer : tablePlayers) {
            List<WebElement> allPageRows;
            try {
                allPageRows = tablePlayer.findElements(By.tagName(TAG_TR));
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                waitForIdElement(driver, SECONDS_DEFAULT, TAG_TR);
                allPageRows = tablePlayer.findElements(By.tagName(TAG_TR));
            }
            for (int i = 0; i < allPageRows.size(); i++) {
                WebElement row = allPageRows.get(i);
                List<WebElement> cells;
                try {
                    cells = row.findElements(By.xpath(XPATH_TH_TD_IN_TABLE));
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    waitForIdElement(driver, SECONDS_DEFAULT, XPATH_TH_TD_IN_TABLE);
                    cells = row.findElements(By.xpath(XPATH_TH_TD_IN_TABLE));
                }
                if (i != 0) {
                    //            Voto = Votogazza + goal(SE P o D 4 altri 3) - Amm * 0.5 - Esp * 1 + RigPar 3 + RigSba -3 + Auto -2
                    WebElement[] webElements = cells.toArray(new WebElement[cells.size()]);
                    String nome = webElements[0].getText();
                    String ruolo = webElements[1].getText();
                    Double votoGazza = Double.valueOf(webElements[3].getText());
                    Double voto = 0d;
                    if (!ruolo.equals("AL")) {
                        Double goals = Double.valueOf(webElements[4].getText());
                        Double ass = Double.valueOf(webElements[5].getText());
                        Double amm = Double.valueOf(webElements[6].getText());
                        Double esp = Double.valueOf(webElements[7].getText());
                        Double rigPar = Double.valueOf(webElements[8].getText());
                        Double rigSba = Double.valueOf(webElements[9].getText());
                        Double auto = Double.valueOf(webElements[10].getText());
                        Double totGazza = Double.valueOf(webElements[11].getText().replace("PT", ""));
                        Double goalTradotti = 0d;
                        Double autoGoalTradotti = 0d;
                        boolean portiereODifensore = ruolo.equals("PR") || ruolo.equals("DF");
                        boolean centroCampista = ruolo.equals("CC");
                        boolean portiere = ruolo.equals("PR");
                        goalTradotti = translateGoalsByRole(goals, goalTradotti, portiereODifensore, centroCampista);
                        autoGoalTradotti = translateAutoGoalByRole(auto, portiere, autoGoalTradotti);
                        if (portiere) {
                            // We are forced to consider the total because the Gazzetta does not insert the goals
                            // taken by the goalkeeper
                            voto = totGazza;
                        } else {
                            voto = votoGazza + goalTradotti + amm + esp + rigPar + rigSba + autoGoalTradotti;
                        }

                        playersStatses.add(
                                PlayersStats.builder().
                                        name(nome).
                                        role(ruolo).
                                        teamName(teamName).
                                        vote(voto).
                                        goalBonus(goalTradotti).
                                        assistBonus(ass).
                                        yellowCardMalus(amm).
                                        redCardMalus(esp).
                                        penaltyDefendedBonus(rigPar).
                                        penaltyDefendedMalus(rigSba).
                                        autoGoalMalus(autoGoalTradotti)
                                        .build());
                    }
                }
            }

        }
        return new TeamStats(teamName, playersStatses, coachStats);

    }

    private static Double translateAutoGoalByRole(Double auto, boolean portiere, Double autoGoalTradotti) {
        if (0 != auto) {
            if (portiere) {
                autoGoalTradotti = auto * 2;
            } else {
                autoGoalTradotti = auto;
            }
        }
        return autoGoalTradotti;
    }

    private static Double translateGoalsByRole(Double goals, Double goalTradotti, boolean portiereODifensore, boolean centroCampista) {
        if (0 != goals) {
            boolean noNegativeGoals = goals > 0;
            if (portiereODifensore) {
                if (noNegativeGoals) {
                    goalTradotti = (goals / 5) * 4;
                } else {
                    goalTradotti = goals;
                }
            } else if (centroCampista) {
                goalTradotti = (goals / 4) * 3;
            } else {
                goalTradotti = goals;
            }
        }
        return goalTradotti;
    }
}
