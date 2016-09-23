package it.fescacom.lambra.service.exporter.collector;

import it.fescacom.lambra.domain.TeamStats;

import java.util.List;

/**
 * Created by scanufe on 21/09/16.
 */
public interface CollectorStatsService {
    List<TeamStats> collectTeamStatsByRound(int round);
}
