package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.HashMap;

public class MaxCategoriesTest {

    @Test
    public void max() {
    }

    /** По факту, в этом методе возвращаются данные из метода maximumCalc, а его мы тестируем отдельно.
     * Но всё же сделаю!
     */
    @Test
    public void maxCategories() throws ParseException, CsvValidationException, IOException {
        HashMap<String, Long> map = new HashMap<>();
        JSONParser parser = new JSONParser();
        String[] data = {"{\"title\": \"шапка\", \"date\": \"2022.12.04\", \"sum\": 250}",
                "{\"title\": \"булка\", \"date\": \"2022.12.04\", \"sum\": 200}",
                "{\"title\": \"шапка\", \"date\": \"2022.12.04\", \"sum\": 200}",
                "{\"title\": \"курица\", \"date\": \"2022.12.04\", \"sum\": 200}",
                "{\"title\": \"мыло\", \"date\": \"2022.12.04\", \"sum\": 200}"};
        for (int i = 0; i < data.length; i++) {
            Object object = parser.parse(data[i]);
            JSONObject jsonObject = (JSONObject) object;
            String cat = CategoriesGetter.getCategories((String) jsonObject.get("title"));
            Long amount = (long) jsonObject.get("sum");
            MaxCategories.countingMap(map, cat, amount);

        }
        String check = MaxCategories.maximumCalc(map).getKey(); // Ожидаем одежду, ее больше всех
        Assertions.assertEquals("одежда", check);
    }

    @Test
    public void countingMap_Test() {
        String[] cat = {"Еда","Одежда", "Другое", "Одежда", "Одежда"};
        Long[] amount = {100L,200L,300L,400L,500L};
        HashMap<String,Long> map = new HashMap<>();
        for (int i = 0; i < cat.length; i++) {
            MaxCategories.countingMap(map, cat[i],amount[i]);
        }
        Long firstCat = map.get("Еда");
        Long secondCat = map.get("Одежда");
        Long thirdCat = map.get("Другое");
        int size = map.size();

        Assertions.assertEquals(100L, firstCat);
        Assertions.assertEquals(1100L, secondCat);
        Assertions.assertEquals(300L, thirdCat);
        Assertions.assertEquals(3,size);

    }

    @Test
    public void maximumCalc_Test() {
        HashMap<String,Long> map = new HashMap<>();
        map.put("Еда", 100L);
        map.put("Одежда", 200L);
        map.put("Другое", 300L);

        String check = MaxCategories.maximumCalc(map).getKey();
        Long amount = MaxCategories.maximumCalc(map).getValue();
        Assertions.assertEquals("Другое", check);
        Assertions.assertEquals(300L, amount);
    }
}