package it.fescacom.lambra.repository.web;

import it.fescacom.lambra.domain.stats.TeamStats;
import it.fescacom.lambra.repository.TeamStatsRepository;
import it.fescacom.lambra.repository.web.connection.BrowserConnector;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/**
 * Created by scanufe on 12/10/16.
 */
public class TeamStatsHtmlUnitRepository implements TeamStatsRepository
{

    @Qualifier("htmlUniConnector")
    private final BrowserConnector browserConnector;

    public TeamStatsHtmlUnitRepository(BrowserConnector browserConnector) {
        this.browserConnector = browserConnector;
    }

    public Map<String, TeamStats> findAllTeamStats(int round) {
        WebDriver driver = browserConnector.getDriver();

        return null;
    }
}
