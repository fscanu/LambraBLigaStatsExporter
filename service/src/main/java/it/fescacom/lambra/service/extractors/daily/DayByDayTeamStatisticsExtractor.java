package it.fescacom.lambra.service.extractors.daily;

import it.fescacom.lambra.service.extractors.GenericExtractor;
import it.fescacom.lambra.web.accessor.MagicBAccessorImpl;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static it.fescacom.lambra.utils.UsefulMethods.writeToFile;

/**
 * Created by scanufe on 11/09/16.
 */
public class DayByDayTeamStatisticsExtractor extends GenericExtractor {
    private static final Logger LOGGER = Logger.getLogger(DayByDayTeamStatisticsExtractor.class);
    private static final String URL = "http://magicb.gazzetta.it/statistiche-serieb-calcio";
    private static final String EMAIL = "federico.scanu@gmail.com";
    private static final String PASSWORD = "f1r3w1r3";

    public void export(String fileName, int round) {

        MagicBAccessorImpl accessor = new MagicBAccessorImpl();
        WebDriver driver = accessor.accessStatistichePage();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Magic B Votes");


        try {
            writeToFile(workbook, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
