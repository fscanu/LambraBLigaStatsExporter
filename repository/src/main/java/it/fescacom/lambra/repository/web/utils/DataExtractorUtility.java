package it.fescacom.lambra.repository.web.utils;

import it.fescacom.lambra.domain.stats.CoachStats;
import it.fescacom.lambra.domain.stats.PlayersStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;

import static it.fescacom.lambra.common.constants.Constants.TAG_TR;
import static it.fescacom.lambra.common.constants.Constants.XPATH_TH_TD_IN_TABLE;

/**
 * Created by scanufe on 21/09/16.
 */
public class DataExtractorUtility {

    public static TeamStats collectTeamStatsData(String teamName, CoachStats coachStats, WebElement... tablePlayers) {
        HashSet<PlayersStats> playersStatses = new HashSet<PlayersStats>();
        for (WebElement tablePlayer : tablePlayers) {
            List<WebElement> allPageRows = tablePlayer.findElements(By.tagName(TAG_TR));
            for (int i = 0; i < allPageRows.size(); i++) {
                WebElement row = allPageRows.get(i);

                List<WebElement> cells = row.findElements(By.xpath(XPATH_TH_TD_IN_TABLE));
                if (i != 0) {
                    //            Voto = Votogazza + goal(SE P o D 4 altri 3) - Amm * 0.5 - Esp * 1 + RigPar 3 + RigSba -3 + Auto -2
                    WebElement[] webElements = cells.toArray(new WebElement[cells.size()]);
                    String nome = webElements[0].getText();
                    String ruolo = webElements[1].getText();
                    double votoGazza = Double.valueOf(webElements[3].getText());
                    double voto = 0;
                    if (!ruolo.equals("AL")) {
                        Integer goals = Integer.valueOf(webElements[4].getText());
                        double amm = Double.valueOf(webElements[6].getText());
                        Integer esp = Integer.valueOf(webElements[7].getText());
                        Integer rigPar = Integer.valueOf(webElements[8].getText());
                        Integer rigSba = Integer.valueOf(webElements[9].getText());
                        Integer auto = Integer.valueOf(webElements[10].getText());
                        boolean portiereODifensore = ruolo.equals("PR") || ruolo.equals("DF");
                        boolean centroCampista = ruolo.equals("CC");
                        boolean portiere = ruolo.equals("PR");
                        int goalTradotti = 0;
                        if (0 != goals) {
                            if (portiereODifensore) {
                                goalTradotti = (goals / 5) * 4;
                            } else if (centroCampista) {
                                goalTradotti = (goals / 4) * 3;
                            } else {
                                goalTradotti = goals;
                            }
                        }
                        int autoGoalTradotti = 0;
                        if (0 != auto) {
                            if (portiere) {
                                autoGoalTradotti = auto * 2;
                            } else {
                                autoGoalTradotti = auto;
                            }
                        }
                        voto = votoGazza + goalTradotti + amm + esp + rigPar + rigSba + autoGoalTradotti;

                        playersStatses.add(new PlayersStats(nome, ruolo, teamName,
                                voto, goalTradotti, 0, amm, esp, rigPar, rigSba, autoGoalTradotti));
                    }
                }
            }

        }
        return new TeamStats(teamName, playersStatses, coachStats);

    }
}
