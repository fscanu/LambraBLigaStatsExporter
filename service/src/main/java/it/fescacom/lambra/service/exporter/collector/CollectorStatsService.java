package it.fescacom.lambra.service.exporter.collector;

import it.fescacom.lambra.domain.stats.TeamStats;

import java.util.Map;

/**
 * Created by scanufe on 21/09/16.
 */
public interface CollectorStatsService {
    Map<String, TeamStats> collectTeamStatsByRound(int round);
}
