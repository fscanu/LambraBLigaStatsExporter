package it.fescacom.lambra.repository.serialization.util;

import it.fescacom.lambra.domain.stats.PlayersStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by scanufe on 25/09/16.
 */
public class TestSerializationUtil {

    private static final double SAMPLE_VOTE = 1d;
    private Map<String, TeamStats> listTeamStats;

    @Before
    public void init() {
    }

    @Test
    public void serializeTeamStatsList() throws IOException {
        SerializationUtil.serialize(listTeamStats, "listTeamStats.ser");
        assertTrue(new File("listTeamStats.ser").exists());
    }

    @Test
    public void deSerializeTeamStatsList() throws IOException, ClassNotFoundException {
        Object listTeamStats = SerializationUtil.deserialize("listTeamStats.ser");
        assertTrue(listTeamStats instanceof List);
    }

    @Test
    @Ignore
    public void shouldFindThePlayer() throws IOException, ClassNotFoundException {
        //given
        Object listTeamStats1 = SerializationUtil.deserialize("1_giornata_LambraBLiga.ser");
        assertTrue(listTeamStats1 instanceof Map);
        listTeamStats = new HashMap<String, TeamStats>();
        listTeamStats.putAll((Map<String, TeamStats>) listTeamStats1);
        PlayersStats playerToSearch = PlayersStats.builder().name("Micai").role("PR").teamName("Bari").build();
        //when
        PlayersStats playersStats = searchPlayer(playerToSearch);

        //then
        assertNotNull(playersStats);
        assertEquals(4.0d, playersStats.getVote(), 0);

    }

    private PlayersStats searchPlayer(PlayersStats playerToSearch) {
        TeamStats teamStats = listTeamStats.get(playerToSearch.getTeamName());

        HashSet<PlayersStats> players = teamStats.getPlayers();
        for (PlayersStats player : players) {
            if (player.equals(playerToSearch)) {
                return player;
            }
        }
        return null;
    }
}

//        P	Micai, Alessandro *	17	Bari
//        P	Petkovic, Lazar 	1	Carpi
//
//        D	Donazzan, Nicola	3	Cittadella
//        D	Casasola, Tiago 	6	Trapani
//        D	Lisuzzo, Andrea 	7	Pisa
//        D	Valjent, Martin 	8	Trapani
//        D	Del Fabro, Dario 	6	Pisa
//        D	Sernicola, Leonardo 	1	Ternana
//        D	Bianchi, Davide 	1	Vicenza
//
//        C	Fedato, Francesco	19	Bari
//        C	Chiaretti, Lucas 	17	Cittadella
//        C	Lollo, Lorenzo 	19	Carpi
//        C	Bellomo, Nicola 	14	Vicenza
//        C	Fedele, Matteo 	8	Carpi
//        C	Colombatto, Santiago	6	Pisa
//        C	Zivkov, Petar 	3	Vicenza
//
//        A	Maniero Riccardo	28	Bari
//        A	Petkovic, Bruno 	27	Trapani
//        A	Avenatti, Felipe 	20	Ternana
//        A	KouamÉ, Cristian 	4	Cittadella
//        A	Palombi, Simone 	7	Ternana
//
//        ALL	Lerda, Franco 	18	Vicenza