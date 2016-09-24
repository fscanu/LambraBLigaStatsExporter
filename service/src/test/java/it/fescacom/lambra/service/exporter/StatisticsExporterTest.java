package it.fescacom.lambra.service.exporter;

import it.fescacom.lambra.service.exporter.round.RoundByRoundExporter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by scanufe on 11/09/16.
 */
public class StatisticsExporterTest {

    @Test
    public void shouldCreateExcelExportOfThe5thRound() {
        Exporter exporter = new RoundByRoundExporter();
        exporter.export("LambraBLiga.xls", 1,2,3, 4, 5);
        Assert.assertTrue(new File("LambraBLiga.xls").exists());
    }
}
