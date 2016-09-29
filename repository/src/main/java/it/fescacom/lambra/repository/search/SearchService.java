package it.fescacom.lambra.repository.search;

import it.fescacom.lambra.domain.stats.PlayersStats;

import java.io.IOException;

/**
 * Created by scanufe on 26/09/2016.
 */
public interface SearchService {

    PlayersStats findByLastNameAndTeam(String lastName, String teamName, int round) throws IOException, ClassNotFoundException;
}
