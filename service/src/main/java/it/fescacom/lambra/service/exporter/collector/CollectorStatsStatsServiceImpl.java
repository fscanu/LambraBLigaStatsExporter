package it.fescacom.lambra.service.exporter.collector;

import it.fescacom.lambra.repository.TeamStatsRepository;
import it.fescacom.lambra.repository.web.TeamStatsRepositoryWebImpl;
import it.fescacom.lambra.domain.TeamStats;

import java.util.List;

/**
 * Created by scanufe on 21/09/16.
 */
public class CollectorStatsStatsServiceImpl implements CollectorStatsService {

    public List<TeamStats> collectTeamStatsByRound(int round) {

        TeamStatsRepository teamStatsRepositoryWeb = new TeamStatsRepositoryWebImpl();
        return teamStatsRepositoryWeb.findAllTeamStats(round);
    }
}
