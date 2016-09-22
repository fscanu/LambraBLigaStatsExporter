package it.fescacom.lambra.service.extractors.constants;

/**
 * Created by scanufe on 11/09/16.
 */
public class ServiceConstants {
    public static final int SECONDS_DEFAULT = 60;
    public static final String ID_TABLE_PLAYERS = "table_players";
    public static final String TABLE_PLAYERS_STATS_REGULARS = "//div[@id='team_player_stats']/table[1]";
    public static final String TABLE_PLAYERS_STATS_RESERVES = "//div[@id='team_player_stats']/table[3]";
    public static final String TABLE_PLAYERS_STATS_COACH = "//div[@id='team_player_stats']/table[2]";
    public static final String ID_TABLE_PLAYERS_INFO = "table_players_info";
    public static final String XPATH_TH_TD_IN_TABLE = ".//*[local-name(.)='th' or local-name(.)='td']";
    public static final String TAG_TR = "tr";

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
}
