package it.fescacom.lambra.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
public class PlayersStats implements Serializable{
    private final String name;
    private final String role;
    private final String teamName;
    private final double vote;
    private final double goalBonus;
    private final double assistBonus;
    private final double yellowCardMalus;
    private final double redCardMalus;
    private final double penaltyDefendedBonus;
    private final double penaltyDefendedMalus;
    private final double autoGoalMalus;
}
