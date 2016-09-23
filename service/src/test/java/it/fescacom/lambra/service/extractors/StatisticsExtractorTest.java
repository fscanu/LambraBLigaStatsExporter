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
        extractor.export("LambraBLiga.xls", 1, 2, 3, 4, 5);
        Assert.assertTrue(new File("LambraBLiga.xls").exists());

    }
}
