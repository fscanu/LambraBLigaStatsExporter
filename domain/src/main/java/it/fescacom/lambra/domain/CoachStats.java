package it.fescacom.lambra.domain;

import lombok.Data;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
public class CoachStats {
    private final String name;
    private final String role;
    private final String teamName;
    private final Double vote;
    private final Double redCardMalus;
}
