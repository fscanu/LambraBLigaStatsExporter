package it.fescacom.lambra.service.extractors;

import it.fescacom.lambra.service.extractors.round.RoundByRoundExporter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by scanufe on 11/09/16.
 */
public class StatisticsExtractorTest {

    @Test
    public void extractDayByDay() {
        Extractor extractor = new RoundByRoundExporter();
        int giornataNo = 5;
        for (int i = 1; i <= giornataNo; i++) {
            extractor.export(i + "_giornata_LambraBLiga.xls", i);
            Assert.assertTrue(new File(i + "_giornata_LambraBLiga.xls").exists());

        }
    }
}
