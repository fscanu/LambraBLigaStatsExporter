package it.fescacom.lambra.domain.service;

import it.fescacom.lambra.domain.TeamStats;

import java.util.List;

/**
 * Created by scanufe on 21/09/16.
 */
public interface ExportService {
    List<TeamStats> exportTeamStats(int round);
}
