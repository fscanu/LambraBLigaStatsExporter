package it.fescacom.lambra.repository.serialization.util;

import it.fescacom.lambra.domain.stats.CoachStats;
import it.fescacom.lambra.domain.stats.PlayersStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by scanufe on 25/09/16.
 */
public class TestSerializationUtil {

    private static final double SAMPLE_VOTE = 1d;
    private List<TeamStats> listTeamStats;

    @Before
    public void init() {
        PlayersStats playersStats1 = new PlayersStats("player1", "PR", "team1", SAMPLE_VOTE, SAMPLE_VOTE,
                SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE);
        PlayersStats playersStats2 = new PlayersStats("player2", "PR", "team1", SAMPLE_VOTE, SAMPLE_VOTE,
                SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE);
        PlayersStats playersStats3 = new PlayersStats("player3", "PR", "team1", SAMPLE_VOTE, SAMPLE_VOTE,
                SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE);
        PlayersStats playersStats4 = new PlayersStats("player4", "PR", "team1", SAMPLE_VOTE, SAMPLE_VOTE,
                SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE, SAMPLE_VOTE);
        HashSet<PlayersStats> playerList = new HashSet<PlayersStats>();
        playerList.add(playersStats1);
        playerList.add(playersStats2);
        playerList.add(playersStats3);
        playerList.add(playersStats4);
        CoachStats coach = new CoachStats("coach1", "AL", "team1", SAMPLE_VOTE, SAMPLE_VOTE);
        TeamStats teamStats1 = new TeamStats("team1", playerList, coach);
        TeamStats teamStats2 = new TeamStats("team2", playerList, coach);
        TeamStats teamStats3 = new TeamStats("team3", playerList, coach);
        TeamStats teamStats4 = new TeamStats("team4", playerList, coach);
        TeamStats teamStats5 = new TeamStats("team5", playerList, coach);
        listTeamStats = new ArrayList<TeamStats>();
        listTeamStats.add(teamStats1);
        listTeamStats.add(teamStats2);
        listTeamStats.add(teamStats3);
        listTeamStats.add(teamStats4);
        listTeamStats.add(teamStats5);
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
        Object listTeamStats = SerializationUtil.deserialize("1_giornata_LambraBLiga.ser");
        assertTrue(listTeamStats instanceof List);

        //when

        //then

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