package it.fescacom.lambra.service.exporter;

/**
 * Created by scanufe on 11/09/16.
 */
public interface Exporter {
    void export(String fileName, int... rounds);
}
