package it.fescacom.lambra.repository;

import it.fescacom.lambra.domain.stats.TeamStats;

import java.util.Map;

/**
 * Created by scanufe on 11/09/16.
 */
public interface TeamStatsRepository {
    Map<String, TeamStats> findAllTeamStats(int round);
}
