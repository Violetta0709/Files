package org.veta;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class FileTest {

    ClassLoader cl = FileTest.class.getClassLoader();
    String zipFile = "testedFiles.zip";

    @DisplayName("ZipPDF test")
    @Test
    void zipPDFTest() throws Exception {
        try (ZipFile zip = new ZipFile(new File("src/test/resources/" + zipFile))) {
            ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(zipFile)));
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    try (InputStream inputStream = zip.getInputStream(entry)) {
                        PDF pdf = new PDF(inputStream);
                        assertThat(pdf.text).contains("Условия маркетинговой акции");
                    }
                }
            }
        }
    }

    @DisplayName("ZipXLSX test")
    @Test
    void zipXlSXTest() throws Exception {
        try (ZipFile zip = new ZipFile(new File("src/test/resources/" + zipFile))) {
            ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(zipFile)));
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().contains(".xlsx")) {
                    try (InputStream inputStream = zip.getInputStream(entry)) {
                        XLS xls = new XLS(inputStream);
                        assertThat(
                                xls.excel.getSheetAt(0)
                                        .getRow(3)
                                        .getCell(1)
                                        .getStringCellValue()
                        ).isEqualTo("MarkoPolo");
                    }
                }
            }
        }
    }

    @DisplayName("ZipCSV test")
    @Test
    void zipCSVTest() throws Exception {
        try (ZipFile zip = new ZipFile(new File("src/test/resources/" + zipFile))) {
            ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(zipFile)));
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    try (InputStream inputStream = zip.getInputStream(entry)) {
                        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                        List<String[]> content = reader.readAll();
                        String[] row = content.get(1);
                        assertThat(row[1]).isEqualTo(" Iuzykhovich");
                    }
                }
            }
        }
    }

}
