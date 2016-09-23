package it.fescacom.lambra.service.extractors;

/**
 * Created by scanufe on 11/09/16.
 */
public abstract class GenericExtractor implements Extractor {
    public abstract void export(String fileName, int... numeroGiornata);
}
