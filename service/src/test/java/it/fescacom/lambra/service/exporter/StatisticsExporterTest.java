package it.fescacom.lambra.service.exporter;

import it.fescacom.lambra.service.exporter.collector.CollectorStatsStatsServiceImpl;
import it.fescacom.lambra.service.exporter.round.RoundByRoundExporter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by scanufe on 11/09/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context.xml"})
public class StatisticsExporterTest {
    @Autowired
    private CollectorStatsStatsServiceImpl collStatsService;

    @Test
    public void shouldCreateExcelExportOfThe5thRound() {
        Exporter exporter = new RoundByRoundExporter(collStatsService);
        exporter.export("LambraBLiga.xls", 1, 2, 3, 4, 5);
        Assert.assertTrue(new File("LambraBLiga.xls").exists());
    }
}
