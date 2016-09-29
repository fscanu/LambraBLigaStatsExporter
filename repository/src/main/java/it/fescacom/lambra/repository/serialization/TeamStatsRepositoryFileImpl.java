package it.fescacom.lambra.repository.serialization;

import it.fescacom.lambra.domain.stats.TeamStats;
import it.fescacom.lambra.repository.TeamStatsRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by scanufe on 23/09/2016.
 */
@Service
public class TeamStatsRepositoryFileImpl implements TeamStatsRepository {

    public Map<String, TeamStats> findAllTeamStats(int round) {
        return null;
    }
}
