package org.example;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Метод предназначен для поиска категории по categories.tsv
 */
public class CategoriesGetter {
    public static String getCategories(String title) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("categories.tsv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String[] parts = nextLine[0].split("\t");
            if (title.equalsIgnoreCase(parts[0])) {
                return parts[1];
            }
        }
        return "Другое";
    }
}
