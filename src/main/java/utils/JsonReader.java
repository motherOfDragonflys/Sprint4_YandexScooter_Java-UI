package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
Общая утилита чтения JSON файлов:
*/

public final class JsonReader {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // ДЛЯ FAQ
    public static Map<String, String> readFaqAsMap(String filePath) throws IOException {
        try (InputStream inputStream = JsonReader.class.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Файл не найден: " + filePath);
            }

            List<Map<String, String>> list = MAPPER.readValue(
                    inputStream,
                    new TypeReference<>() {}
            );

            //Преобразование в словарь вопрос-ответ для сравнения пар
            Map<String, String> result = new LinkedHashMap<>();
            for (var item : list) {
                String q = item.get("question");
                String a = item.get("answer");
                if (q != null && a != null) {
                    result.put(q, a);
                }
            }
            return result;
        }
    }

    // ДЛЯ ФОРМ
    private static List<Map<String, String>> loadList(String filePath) throws IOException {
        InputStream inputStream = JsonReader.class.getResourceAsStream(filePath);
        if (inputStream == null) throw new IllegalStateException("Файл не найден: " + filePath);

        return MAPPER.readValue(
                inputStream,
                new TypeReference<>() {}
        );
    }

    // Чтение списка персональных данных в формате [ {...}, {...} ]
    public static List<Map<String, String>> loadPersonaDataList(String filePath) throws IOException {
        return loadList(filePath);
    }

    // Чтение списка опций заказа в формате [ {...}, {...} ]
    public static List<Map<String, String>> loadOptionsDataList(String filePath) throws IOException {
        return loadList(filePath);
    }

}
