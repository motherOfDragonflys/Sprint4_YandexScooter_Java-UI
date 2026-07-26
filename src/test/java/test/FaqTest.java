package test;

import basetest.BaseTest;
import org.junit.*;
import pageobjects.FaqPage;
import utils.TestDataLoader;
import java.io.IOException;
import java.util.Map;

/*
--ЗДЕСЬ: Тест блока Faq на соответствие ответов вопросам
*/

/// На сайте опечатка, в json как эталон взят вопрос без опечатки.

public class FaqTest extends BaseTest {

    @Test
    public void shouldCollectAndValidateFaqAnswers()  throws IOException {

        //Подкачиваем эталоны из json
        Map<String, String> expectedPairs =
                TestDataLoader.loadAndValidateFaqPairs("/faq-questions.json");

        driver.get("https://qa-scooter.praktikum-services.ru");

        FaqPage faq = new FaqPage(driver);
        //Закрыли баннер куки
        faq.closeCookieBanner();
        //Включаем основную логику теста: собираем пары и сравниваем с эталоном в json
        Map<String, Object> validationResult =
                faq.validateAgainst(expectedPairs);

        boolean success = (Boolean) validationResult.get("success");
        String report = (String) validationResult.get("report");

        // Если валидация не прошла — тест падает с отчётом об ошибках
        Assert.assertTrue("Валидация FAQ не пройдена:\n" + report, success);

        //Если все этапы теста пройдены успешно:
        System.out.println("[SUCCESS FaqTest]: Все вопросы найдены, ответы совпадают");
    }
}
