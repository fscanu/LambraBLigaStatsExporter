package it.fescacom.lambra.service.extractors;

/**
 * Created by scanufe on 11/09/16.
 */
public interface Extractor {
    void export(String fileName, int... rounds);
}
