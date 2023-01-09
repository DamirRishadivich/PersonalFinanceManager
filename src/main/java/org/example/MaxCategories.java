package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MaxCategories {

    public static Map.Entry<String, Long> maxCategoriesCounting() {
        HashMap<String, Long> map = new HashMap<>();
        JSONParser parser = new JSONParser();
        try (BufferedReader reader = new BufferedReader(new FileReader("logger.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                String cat = CategoriesGetter.getCategories((String) jsonObject.get("title"));
                long amount = (long) jsonObject.get("sum");

                if (map.containsKey(cat)) {
                    map.put(cat, map.get(cat) + amount);
                } else {
                    map.put(cat,amount);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        Map.Entry<String, Long> maxEntry = null;
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

}
