package it.fescacom.lambra.service.extractors;

import it.fescacom.lambra.service.extractors.daily.DayByDayTeamStatisticsExtractor;
import it.fescacom.lambra.service.extractors.totals.SerieBTotalStatisticsExtractor;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by scanufe on 11/09/16.
 */
public class StatisticsExtractorTest {

    @Test
    public void extractTotalStatiticheSerieB() {
        SerieBTotalStatisticsExtractor serieBTotalStatisticsExtractor = new SerieBTotalStatisticsExtractor();
        serieBTotalStatisticsExtractor.exportTotalStats("TOTAL - Magic B Votes.xls");
        Assert.assertTrue(new File("TOTAL - Magic B Votes.xls").exists());
    }

    @Test
    public void extractDayByDay() {
        Extractor extractor = new DayByDayTeamStatisticsExtractor();
        int giornataNo = 3;
        for (int i = 1; i <= giornataNo; i++) {
            extractor.export(i + "_giornata_LambraBLiga.xls", i);
            Assert.assertTrue(new File(i + "_giornata_LambraBLiga.xls").exists());

        }
    }
}
