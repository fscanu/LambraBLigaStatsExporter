package it.fescacom.lambra.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
public class TeamStats  implements Serializable {
    private final String teamName;
    private final List<PlayersStats> players;
    private final CoachStats coach;
}
