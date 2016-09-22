package it.fescacom.lambra.service.extractors.totals;

import it.fescacom.lambra.service.extractors.GenericExtractor;
import it.fescacom.lambra.web.accessor.MagicBAccessorImpl;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static it.fescacom.lambra.service.extractors.constants.ServiceConstants.*;
import static it.fescacom.lambra.utils.UsefulMethods.waitForIdElement;
import static it.fescacom.lambra.utils.UsefulMethods.writeToFile;

/**
 * Created by scanufe on 07/09/16.
 */
public class SerieBTotalStatisticsExtractor extends GenericExtractor {

    private static final Logger LOGGER = Logger.getLogger(SerieBTotalStatisticsExtractor.class);
    private static final String URL = "http://magicb.gazzetta.it/statistiche-serieb-calcio";
    private static final String EMAIL = "federico.scanu@gmail.com";
    private static final String PASSWORD = "f1r3w1r3";

    public void export(String fileName, int numeroGiornata) {

    }

    public void exportTotalStats(String fileName) {
        MagicBAccessorImpl accessor = new MagicBAccessorImpl();
        WebDriver driver = accessor.accessStatistichePage();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Magic B Votes");

        processPagesAndWrite(driver, sheet);
        try {
            writeToFile(workbook, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();

    }

    private void processPagesAndWrite(WebDriver driver, HSSFSheet sheet) {

        String[] strings = getStringPager(driver);
        final int totNoPages = Integer.valueOf(strings[strings.length - 1]);
        int curNoPage = Integer.valueOf(strings[0]);

        WebElement tablePlayers = driver.findElement(By.id(ID_TABLE_PLAYERS));
        int rowsProcessed = 0;
        while (curNoPage <= totNoPages) {
            int emptyRows = 0;
            boolean alreadySetHeader = rowsProcessed == 0;
//            rowsProcessed = processPageData(tablePlayers, null);

            if (curNoPage < totNoPages) {
                WebElement pageFwd = driver.findElement(By.xpath("//li[@id='table_players_next']/a"));
                pageFwd.click();
                waitForIdElement(driver, SECONDS_DEFAULT, ID_TABLE_PLAYERS);

                curNoPage = Integer.valueOf(getStringPager(driver)[0]);
                LOGGER.info(curNoPage + " - " + totNoPages);
            } else {
                break;
            }
        }
    }

    private String[] getStringPager(WebDriver driver) {
        WebElement table_players_info = driver.findElement(By.id(ID_TABLE_PLAYERS_INFO));
        String table_players_infoText = table_players_info.getText();
        return table_players_infoText.split(" ");
    }
}