package it.fescacom.lambra.domain.stats;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
public class TeamStats implements Serializable {
    private final String teamName;
    private final HashSet<PlayersStats> players;
    private final CoachStats coach;
}
