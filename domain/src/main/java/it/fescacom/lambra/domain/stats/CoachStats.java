package it.fescacom.lambra.domain.stats;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
@ToString
public class CoachStats implements Serializable {
    private final String name;
    private final String role;
    private final String teamName;
    private final Double vote;
    private final Double redCardMalus;
}
