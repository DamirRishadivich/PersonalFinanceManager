package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestService {

    /** Метод сохраняет полученные запросы от клиента в файл data.bin в виде строки */
    public static void saveToLogFile (String request) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data.bin", true))) {
            writer.println(request);
        } catch (IOException e) {
            e.getMessage();
        }
    }
    /**
     * Метод возвращает строку с максимальной категорией, взаимодействует с классом MaxCategories
     */
    public static JSONObject JSONResponse() { // Это ужас, а не код!!!!
        JSONObject obj = new JSONObject();

        JSONArray list = new JSONArray();
        JSONArray listDay = new JSONArray();
        JSONArray listMonth = new JSONArray();
        JSONArray listYear = new JSONArray();

        try {
            list.add("category: " + MaxCategories.maxCategories().getKey());
            list.add("sum: " + MaxCategories.maxCategories().getValue());
        } catch (Exception ex) {
            list.add("За весь период нет покупок");
        }

        try {
            listYear.add("category: " + MaxCategories.max().get(0).getKey());
            listYear.add("sum: " + MaxCategories.max().get(0).getValue());
        } catch (Exception ex) {
            listYear.add("За текущий год нет покупок");
        }

        try {
            listMonth.add("category: " + MaxCategories.max().get(1).getKey());
            listMonth.add("sum: " + MaxCategories.max().get(1).getValue());
        } catch (Exception ex) {
            listMonth.add("За текущий месяц нет покупок");
        }

        try {
            listDay.add("category: " + MaxCategories.max().get(2).getKey());
            listDay.add("sum: " + MaxCategories.max().get(2).getValue());
        } catch (Exception ex) {
            listDay.add("За текущий день нет покупок");
        }

        obj.put("maxCategory", list);
        obj.put("MaxDayCategory", listDay);
        obj.put("MaxMonthCategory", listMonth);
        obj.put("MaxYearCategory",listYear);
        return obj;
    }
}
