package it.fescacom.lambra.domain;

import lombok.Data;

/**
 * Created by scanufe on 21/09/16.
 */
@Data
public class CoachStats {
    private final String name;
    private final String role;
    private final String match;
    private final double vote;
    private final double redCardMalus;
}
