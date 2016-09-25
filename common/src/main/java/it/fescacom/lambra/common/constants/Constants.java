package it.fescacom.lambra.common.constants;

/**
 * Created by scanufe on 11/09/16.
 */
public class Constants {
    //Page constants
    public static final int SECONDS_DEFAULT = 10;
    public static final String TABLE_PLAYERS_STATS_REGULARS = "//div[@id='team_player_stats']/table[1]";
    public static final String TABLE_PLAYERS_STATS_RESERVES = "//div[@id='team_player_stats']/table[3]";
    public static final String TABLE_PLAYERS_STATS_COACH = "//a[contains(text(),'ALLENATORE')]";
    public static final String ORDER_TABLE_PLAYERS_STATS_COACH = "//table[@id='table_coach']/thead/tr/th[3]";
    public static final String XPATH_TH_TD_IN_TABLE = ".//*[local-name(.)='th' or local-name(.)='td']";
    public static final String TAG_TR = "tr";

    public static final String INPUT_EMAIL = "email";
    public static final String INPUT_PASSWORD = "password";
}
