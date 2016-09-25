package it.fescacom.lambra.repository.serialization.util;

import it.fescacom.lambra.domain.CoachStats;
import it.fescacom.lambra.domain.PlayersStats;
import it.fescacom.lambra.domain.TeamStats;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        List<PlayersStats> playerList = new ArrayList<PlayersStats>();
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
}
