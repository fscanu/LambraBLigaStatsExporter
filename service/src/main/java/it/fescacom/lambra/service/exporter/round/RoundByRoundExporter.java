package it.fescacom.lambra.service.exporter.round;

import it.fescacom.lambra.domain.stats.CoachStats;
import it.fescacom.lambra.domain.stats.PlayersStats;
import it.fescacom.lambra.domain.stats.TeamStats;
import it.fescacom.lambra.service.exporter.Exporter;
import it.fescacom.lambra.service.exporter.collector.CollectorStatsStatsServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static it.fescacom.lambra.common.UsefulMethods.writeToFile;

/**
 * Created by scanufe on 11/09/16.
 */
@Service
public class RoundByRoundExporter implements Exporter {

    private final CollectorStatsStatsServiceImpl collStatsService;

    @Autowired
    public RoundByRoundExporter(CollectorStatsStatsServiceImpl collectorStatsService) {
        this.collStatsService = collectorStatsService;
    }

    public void export(String fileName, int... rounds) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (int round : rounds) {

            HSSFSheet sheet = workbook.createSheet(round + "a Giornata");
            int rowCount = 0;
            Row row = sheet.createRow(++rowCount);

            writeHeader(row);

            Map<String, TeamStats> teamStatses = collStatsService.collectTeamStatsByRound(round);

            createSheetFromTeamStatses(sheet, rowCount, teamStatses);
        }
        try {
            writeToFile(workbook, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createSheetFromTeamStatses(HSSFSheet sheet, int rowCount, Map<String, TeamStats> teamStatses) {
        Row row;
        final Set<String> teamNames = teamStatses.keySet();
        for (String teamName : teamNames) {
            HashSet<PlayersStats> players = teamStatses.get(teamName).getPlayers();
            for (PlayersStats player : players) {
                row = sheet.createRow(++rowCount);
                writePlayerRow(player, row);
            }
            //separate player from coach
            ++rowCount;
            row = sheet.createRow(++rowCount);
            writeCoachRow(teamStatses.get(teamName).getCoach(), row);
            //separate teams
            ++rowCount;
            ++rowCount;
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
