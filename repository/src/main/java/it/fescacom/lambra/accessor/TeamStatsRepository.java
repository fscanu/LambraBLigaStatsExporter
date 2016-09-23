package it.fescacom.lambra.accessor;

import it.fescacom.lambra.domain.TeamStats;

import java.util.List;

/**
 * Created by scanufe on 11/09/16.
 */
public interface TeamStatsRepository {
    List<TeamStats> findAllTeamStats(int round);
}
