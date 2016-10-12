package it.fescacom.lambra.service.exporter.collector;

import it.fescacom.lambra.domain.stats.TeamStats;
import it.fescacom.lambra.repository.web.TeamStatsFirefoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by scanufe on 21/09/16.
 */
@Service
public class CollectorStatsStatsServiceImpl implements CollectorStatsService {
    private final TeamStatsFirefoxRepository teamStatsRepository;

    @Autowired
    public CollectorStatsStatsServiceImpl(TeamStatsFirefoxRepository teamStatsRepository) {
        this.teamStatsRepository = teamStatsRepository;
    }

    public Map<String, TeamStats> collectTeamStatsByRound(int round) {

        return teamStatsRepository.findAllTeamStats(round);
    }
}
