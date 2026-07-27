package utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* Утилита для загрузки тестовых данных.
Загружает список кейсов и сразу валидирует обязательные ключи.
Если что-то не так — падает с понятной ошибкой ДО запуска браузера. */

public final class TestDataLoader {

    //ДЛЯ FAQ
    public static Map<String, String> loadAndValidateFaqPairs(String path) throws IOException {
        Map<String, String> pairs = JsonReader.readFaqAsMap(path);

        if (pairs.isEmpty()) {
            throw new IllegalStateException("Файл '" + path + "' пустой или не содержит пар вопрос-ответ");
        }

        int index = 0;
        for (Map.Entry<String, String> entry : pairs.entrySet()) {
            String q = entry.getKey();
            String a = entry.getValue();

            if (q == null || q.trim().isEmpty()) {
                throw new IllegalStateException(
                        String.format("В файле '%s' найден пустой вопрос (индекс: %d)", path, index)
                );
            }
            if (a == null || a.trim().isEmpty()) {
                throw new IllegalStateException(
                        String.format("В файле '%s' для вопроса '%s' пустой ответ (индекс: %d)",
                                path, q, index)
                );
            }
            index++;
        }

        return pairs;
    }

    //ДЛЯ ФОРМ ЗАКАЗА

    //Для первой формы - массивы с проверяемыми полями (изменять, если тестируем заполнение не всех полей н-р)
    public static List<Map<String, String>> loadAndValidatePersonaCases(String path) throws IOException {
        List<Map<String, String>> cases = JsonReader.loadPersonaDataList(path);

        String[] requiredKeys = {"firstName", "lastName", "address", "stationName", "phone"};
        //Если что-то не так с данными, тест упадет сразу
        validateCases(cases, path, "persona", requiredKeys);

        return cases;
    }

    //Для второй формы
    public static List<Map<String, String>> loadAndValidateOptionsCases(String path) throws IOException {
        List<Map<String, String>> cases = JsonReader.loadOptionsDataList(path);

        String[] requiredKeys = {"deliveryDate", "rentalPeriod", "colourId", "comment"};
        validateCases(cases, path, "options", requiredKeys);

        return cases;
    }

    // Общая логика валидации
    private static void validateCases(List<Map<String, String>> cases, String filePath, String typeName, String[] requiredKeys) {
        if (cases == null || cases.isEmpty()) {
            throw new IllegalStateException("Файл '" + filePath + "' пустой");
        }

        for (int i = 0; i < cases.size(); i++) {
            Map<String, String> data = cases.get(i);
            String contextName = data.getOrDefault("testName", typeName + "_case_" + (i + 1));

            for (String key : requiredKeys) {
                if (!data.containsKey(key)) {
                    throw new IllegalStateException(
                            String.format("В кейсе '%s' (файл: %s, индекс: %d) отсутствует ключ: '%s'",
                                    contextName, filePath, i, key)
                    );
                }
                String val = data.get(key);
                if (val == null || val.trim().isEmpty()) {
                    throw new IllegalStateException(
                            String.format("В кейсе '%s' (файл: %s) ключ '%s' пуст!",
                                    contextName, filePath, key)
                    );
                }
            }
        }
    }
}
