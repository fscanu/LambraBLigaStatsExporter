package it.fescacom.lambra.service.extractors.round;

import it.fescacom.lambra.domain.CoachStats;
import it.fescacom.lambra.domain.PlayersStats;
import it.fescacom.lambra.domain.TeamStats;
import it.fescacom.lambra.domain.service.ExportService;
import it.fescacom.lambra.domain.service.RoundByRoundLeagueStatsCollector;
import it.fescacom.lambra.service.extractors.GenericExtractor;
import it.fescacom.lambra.web.accessor.MagicBAccessorImpl;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;

import static it.fescacom.lambra.utils.UsefulMethods.writeToFile;

/**
 * Created by scanufe on 11/09/16.
 */
public class RoundByRoundExporter extends GenericExtractor {
    private static final Logger LOGGER = Logger.getLogger(RoundByRoundExporter.class);

    public void export(String fileName, int round) {

        MagicBAccessorImpl accessor = new MagicBAccessorImpl();
        WebDriver driver = accessor.accessStatistichePage();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Magic B Votes");
        int rowCount = 0;
        Row row = sheet.createRow(++rowCount);
        writeHeader(row);
        ExportService exporter = new RoundByRoundLeagueStatsCollector();
        List<TeamStats> teamStatses = exporter.exportTeamStats(round);
        for (TeamStats teamStats : teamStatses) {
            List<PlayersStats> players = teamStats.getPlayers();
            for (PlayersStats player : players) {
                row = sheet.createRow(++rowCount);
                writePlayerRow(player, row);
            }
            //separate player from coach
            ++rowCount;
            row = sheet.createRow(++rowCount);
            writeCoachRow(teamStats.getCoach(), row);
            //separate teams
            ++rowCount;
            ++rowCount;
        }
        try {
            writeToFile(workbook, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private void writeHeader(Row row) {


        Cell cell = row.createCell(1);
        cell.setCellValue("NOME");

        cell = row.createCell(2);
        cell.setCellValue("RUOLO");

        cell = row.createCell(3);
        cell.setCellValue("SQUADRA");

        cell = row.createCell(4);
        cell.setCellValue("VOTO");

        cell = row.createCell(5);
        cell.setCellValue("BONUS GOALS");

        cell = row.createCell(6);
        cell.setCellValue("MALUS AMMONIZIONE");

        cell = row.createCell(7);
        cell.setCellValue("MALUS ESPULSIONE");

        cell = row.createCell(8);
        cell.setCellValue("BONUS RIG. PARATO");

        cell = row.createCell(9);
        cell.setCellValue("MALUS RIG. SBAGLIATO");

        cell = row.createCell(10);
        cell.setCellValue("MALUS AUTOGOAL");
    }

    private void writePlayerRow(PlayersStats player, Row row) {

        Cell cell = row.createCell(1);
        cell.setCellValue(player.getName());

        cell = row.createCell(2);
        cell.setCellValue(player.getRole());

        cell = row.createCell(3);
        cell.setCellValue(player.getTeamName());

        cell = row.createCell(4);
        cell.setCellValue(player.getVote());

        cell = row.createCell(5);
        cell.setCellValue(player.getGoalBonus());

        cell = row.createCell(6);
        cell.setCellValue(player.getYellowCardMalus());

        cell = row.createCell(7);
        cell.setCellValue(player.getRedCardMalus());

        cell = row.createCell(8);
        cell.setCellValue(player.getPenaltyDefendedBonus());

        cell = row.createCell(9);
        cell.setCellValue(player.getPenaltyDefendedMalus());

        cell = row.createCell(10);
        cell.setCellValue(player.getAutoGoalMalus());
    }

    private void writeCoachRow(CoachStats coachStats, Row row) {

        Cell cell = row.createCell(1);
        cell.setCellValue(coachStats.getName());

        cell = row.createCell(2);
        cell.setCellValue(coachStats.getRole());

        cell = row.createCell(3);
        cell.setCellValue(coachStats.getTeamName());

        cell = row.createCell(4);
        cell.setCellValue(coachStats.getVote());

        cell = row.createCell(5);
        cell.setCellValue("");

        cell = row.createCell(6);
        cell.setCellValue("");

        cell = row.createCell(7);
        cell.setCellValue(coachStats.getRedCardMalus());

        cell = row.createCell(8);
        cell.setCellValue("");

        cell = row.createCell(9);
        cell.setCellValue("");

        cell = row.createCell(10);
        cell.setCellValue("");
    }
}
