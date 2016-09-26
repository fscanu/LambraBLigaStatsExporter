package it.fescacom.lambra.service.exporter;

import it.fescacom.lambra.domain.stats.CoachStats;
import it.fescacom.lambra.domain.stats.PlayersStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by scanufe on 26/09/2016.
 */
public class CalculateResultsService {
    private static final Double SAMPLE_VOTE_0 = 0d;
    private static final Double SAMPLE_VOTE_1 = 1d;
    private static final Double SAMPLE_VOTE_2 = 2d;
    private static final Double SAMPLE_VOTE_3 = 3d;

    @Before
    public void setupTeams() {

        PlayersStats playersStats1 = new PlayersStats("player1", "PR", "team1", SAMPLE_VOTE_1, SAMPLE_VOTE_1,
                SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1);
        PlayersStats playersStats2 = new PlayersStats("player2", "DF", "team2", SAMPLE_VOTE_1, SAMPLE_VOTE_1,
                SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_2, SAMPLE_VOTE_1, SAMPLE_VOTE_1);
        PlayersStats playersStats3 = new PlayersStats("player3", "DF", "team3", SAMPLE_VOTE_1, SAMPLE_VOTE_1,
                SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_3, SAMPLE_VOTE_1, SAMPLE_VOTE_1);
        PlayersStats playersStats4 = new PlayersStats("player4", "DF", "team4", SAMPLE_VOTE_1, SAMPLE_VOTE_1,
                SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_0, SAMPLE_VOTE_1, SAMPLE_VOTE_1);
        PlayersStats playersStats5 = new PlayersStats("player4reserve", "DF", "team5", SAMPLE_VOTE_1, SAMPLE_VOTE_1,
                SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_1, SAMPLE_VOTE_3, SAMPLE_VOTE_1, SAMPLE_VOTE_1);
        HashSet<PlayersStats> playerList = new HashSet<PlayersStats>();
        playerList.add(playersStats1);
        playerList.add(playersStats2);
        playerList.add(playersStats3);
        playerList.add(playersStats4);
        playerList.add(playersStats5);
        CoachStats coach = new CoachStats("coach1", "AL", "team1", SAMPLE_VOTE_1, SAMPLE_VOTE_1);
        TeamStats teamStats1 = new TeamStats("team1", playerList, coach);
        ArrayList<TeamStats> listTeamStats = new ArrayList<TeamStats>();
        listTeamStats.add(teamStats1);
    }

    @Test
    public void shouldCalculateResults() {
        //Team module info ex: 3 4 3
        //Obtain votes for all the team by round
        //Collect by role
        //Conditions to calculate correctly
    }
}
