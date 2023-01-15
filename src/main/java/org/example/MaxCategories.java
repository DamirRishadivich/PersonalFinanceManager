package org.example;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MaxCategories {
    /**
     * Метод проходит по файлу data.bin, превращает каждую строку в json объект
     * В карту помещает "ключ - категория" и "значение - сумма", прибавляет если категория повторяется то,
     * возвращает содержащую категорию с максимальным значением.
     */
    public static ArrayList<Map.Entry<String,Long>> max ()  { // 0 - год 1 - месяц 2 - день
        ArrayList<Map.Entry<String, Long>> list = new ArrayList<>();
        HashMap<String, Long> day = new HashMap<>();
        HashMap<String, Long> month = new HashMap<>();
        HashMap<String, Long> year = new HashMap<>();

        JSONParser parser = new JSONParser();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        try (BufferedReader reader = new BufferedReader(new FileReader("data.bin"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                LocalDate dateForLine = LocalDate.parse((String) jsonObject.get("date"), formatters);
                String cat = CategoriesGetter.getCategories((String) jsonObject.get("title"));
                long amount = (long) jsonObject.get("sum");
                if (currentDate.getYear() == dateForLine.getYear()) {
                    countingMap(year, cat, amount);
                    if (currentDate.getMonth() == dateForLine.getMonth()) {
                        countingMap(month, cat, amount);
                        if (currentDate.getDayOfMonth() == dateForLine.getDayOfMonth()) {
                            countingMap(day, cat, amount);
                        }
                    }
                }
            }
            list.add(maximumCalc(year));
            list.add(maximumCalc(month));
            list.add(maximumCalc(day));

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }

    public static Map.Entry<String,Long> maxCategories() {
        HashMap<String, Long> map = new HashMap<>();
        JSONParser parser = new JSONParser();
        try (BufferedReader reader = new BufferedReader(new FileReader("data.bin"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                    String cat = CategoriesGetter.getCategories((String) jsonObject.get("title"));
                    long amount = (long) jsonObject.get("sum");
                    countingMap(map, cat, amount);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return maximumCalc(map);
    }

    public static void countingMap(HashMap<String, Long> map, String cat, Long amount) {
        if (map.containsKey(cat)) {
            map.put(cat, map.get(cat) + amount);
        } else {
            map.put(cat, amount);
        }
    }
    public static Map.Entry<String, Long> maximumCalc(HashMap<String, Long> map) {
        Map.Entry<String, Long> maxEntry = null;
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

}
