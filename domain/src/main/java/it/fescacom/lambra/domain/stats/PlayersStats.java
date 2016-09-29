package it.fescacom.lambra.domain.stats;

import com.google.common.base.Objects;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
public class PlayersStats implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        return (o instanceof TeamStats)
                && (((PlayersStats) o).getName().equals(this.getName()))
                && (((PlayersStats) o).getTeamName().equals(this.getTeamName()))
                && (((PlayersStats) o).getRole().equals(this.getRole()));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName(), this.getTeamName(), this.getRole());
    }
}
