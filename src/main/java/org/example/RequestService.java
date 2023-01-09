package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestService {

    public static void saveToLogFile (String request) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("logger.json", true))) {
            writer.println(request);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static String JSONResponse() {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();

        String cat = MaxCategories.maxCategoriesCounting().getKey();
        Long sum = MaxCategories.maxCategoriesCounting().getValue();

        list.add("category: " + cat);
        list.add("sum: " + sum);
        obj.put("maxCategory", list);
        return obj.toJSONString();
    }


}
