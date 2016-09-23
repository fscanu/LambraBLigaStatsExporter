package it.fescacom.lambra.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by scanufe on 11/09/16.
 */
public class UsefulMethods {

    public static void waitForIdElement(WebDriver driver, int seconds, String elementId) {
        new WebDriverWait(driver, seconds)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
    }

    public static void waitForXpathElement(WebDriver driver, int seconds, String xpath) {
        new WebDriverWait(driver, seconds)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public static void writeToFile(HSSFWorkbook workbook, String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
    }

}