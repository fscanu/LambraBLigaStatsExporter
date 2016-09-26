package it.fescacom.lambra.repository.search;

import it.fescacom.lambra.domain.stats.PlayersStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import it.fescacom.lambra.repository.serialization.util.SerializationUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by scanufe on 26/09/2016.
 */
public class SearchServiceImpl implements SearchService {
    public PlayersStats findByLastNameAndTeam(String lastName, String teamName, int round) throws IOException, ClassNotFoundException {
        final List<TeamStats> teamStatses = (List<TeamStats>) SerializationUtil.deserialize(round + "_giornata_LambraBLiga.ser");
        for (TeamStats teamStats : teamStatses) {
            final HashSet<PlayersStats> players = teamStats.getPlayers();
        }
        return null;
    }
}
