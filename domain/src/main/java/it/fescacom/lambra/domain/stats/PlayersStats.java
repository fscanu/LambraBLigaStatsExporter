package it.fescacom.lambra.domain.stats;

import com.google.common.base.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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
        boolean samePlayerName = this.getName().contains((((PlayersStats) o).getName()));
        boolean sameTeamName = ((PlayersStats) o).getTeamName().equals(this.getTeamName());
        boolean sameRole = ((PlayersStats) o).getRole().equals(this.getRole());
        return (o instanceof PlayersStats)
                && samePlayerName
                && sameTeamName
                && sameRole;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName(), this.getTeamName(), this.getRole());
    }
}
