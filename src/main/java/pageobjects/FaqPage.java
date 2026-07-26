package pageobjects;

import locators.BaseLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.*;

/*--ЗДЕСЬ: Логика проверки FAQ

* методы ожидания (взят у родителя)
* логика:
 - сбор фактических вопросов и ответов в пары
 - логика проверки на соответствие эталону

*/

//extends BasePage: возьмем нужные методы для реализации логики:
public class FaqPage extends BasePage {

    private static final By questionsLocator = BaseLocators.QUESTIONS_FAQ_LOCATOR;

//__________Применение конструктора с временем ожидания (общее, управляется для всех тестов из BasePage)________________

    public FaqPage(WebDriver driver) {
        super(driver);
    }

//_________________________________________________ЛОГИКА_______________________________________________________________

    //_______________Собираем вопросы и ответы в HashMap в виде ключ-значение___________________________________________
    public Map<String, String> collectAllFaqPairs() {

        //Создали пустой мап для <вопрос, ответ>
        Map<String, String> result = new HashMap<>();

        //Список всех вопросов по общей части хедеров (ориентируется на DOM, ничего не кликает)
        List<WebElement> headers = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(questionsLocator));

        /* Формирование ответов на вопросы, совмещаются по общему id:
            Здесь id заголовка становится id панели ответов,
            accordion__heading-0 для вопроса преобразуется в accordion__panel-0 для ответа.
            Если у элемента нет id — пропускаем его (подсказки от идеи).
            Сделано для того, чтобы ответы совмещались с вопросами
             - получить просто список ответов было бы недостаточно для проверки именно пар
            */
        for (WebElement header : headers) {
            String headerId = header.getAttribute("id");
            if (headerId == null) {
                continue;
            }
            // создание локатора для ответа:
            String panelId = headerId.replace("heading", "panel");
            By panelLocator = By.id(panelId);

            String question = header.getText().trim();
            scrollElementToCenter(header);
            header.click();

            /* Создаем список параграфов-ответов (все еще в цикле, для каждого выпадающего списка)
              и собираем все <p> в список. Если ответа нет, он будет пустым.
              Сначала ждем видимости панели ответов:  */
            WebElement panel = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(panelLocator));

            /* Ищем и перебираем все пустые и непустые <p>:
            Получаем текст для параграфа, игнорируя случайные пробелы
            Собираем все тексты вложенных <p> */
            List<WebElement> paragraphs = panel.findElements(By.tagName("p"));

            StringBuilder answerBuilder = new StringBuilder();
            for (WebElement p : paragraphs) {
                String text = p.getText().trim();
                if (!text.isEmpty()) {
                    if (answerBuilder.length() > 0) answerBuilder.append(" ");
                    answerBuilder.append(text);
                }
            }
            String answer = answerBuilder.toString();

            //Если получили не пустой ответ, формируем из листа вопросов и листа ответа пары в Map
            if (!answer.isEmpty()) {
                result.put(question, answer);
            } else {
                System.out.println("Ответ пустой для вопроса: '" + question + "'");
            }
        }
        return result;
    }

//____________________________ЛОГИКА ДЛЯ ПРОВЕРКИ СООТВЕТСТВИЯ ВОПРОСОВ И ОТВЕТОВ В FAQ_________________________________

    //Метод принимает ожидаемые пары json в тесте
    public Map<String, Object> validateAgainst(Map<String, String> expectedPairs) {

        //Вызываем предыдущий метод, который собрал фактические пары вопрос-ответ
        Map<String, String> actualPairs = collectAllFaqPairs();

        StringBuilder report = new StringBuilder();
        boolean success = true;

        // Нормализуем ожидаемые ключи
        Map<String, String> normalizedExpected = new HashMap<>();
        for (Map.Entry<String, String> e : expectedPairs.entrySet()) {
            normalizedExpected.put(normalizeText(e.getKey()), e.getValue());
        }

        //Логика сравнения:
        //Перебираем из json эталоны, применяем нормализацию
        for (Map.Entry<String, String> e : normalizedExpected.entrySet()) {
            String normExpectedQ = e.getKey();
            String expectedAnswer = e.getValue();
            String normExpectedA = normalizeText(expectedAnswer);

            // Ищем совпадение среди фактических вопросов
            String actualQuestion = null;
            String actualAnswer = null;

            for (Map.Entry<String, String> ae : actualPairs.entrySet()) {
                if (normalizeText(ae.getKey()).equals(normExpectedQ)) {
                    actualQuestion = ae.getKey();
                    actualAnswer = ae.getValue();
                    break;
                }
            }

            /* Если циклом не нашли эталонный вопрос в списке фактических, то
            добавляем вопрос в список ненайденных среди фактических */
            if (actualQuestion == null) {
                success = false;
                report
                        .append("\nНе найден вопрос на странице: ").append(e.getKey());
                continue;
            }

            //Если нормализованные ответы эталон-факт не совпали, поймаем запись:
            String normActualA = normalizeText(actualAnswer);
            if (!normActualA.equals(normExpectedA)) {
                success = false;
                report
                        .append("\nНесовпадение ответа для вопроса: ").append(actualQuestion)
                        .append("\n Ожидаемый: ").append(normExpectedA)
                        .append("\n Фактический: ").append(normActualA);
            }
        }

        //Ищем лишние вопросы
        Set<String> expectedKeysNorm = normalizedExpected.keySet();
        for (String actualQ : actualPairs.keySet()) {
            if (!expectedKeysNorm.contains(normalizeText(actualQ))) {
                success = false;
                report
                        .append("\nЛишний вопрос на странице (нет в эталоне): ").append(actualQ);
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", success);
        result.put("report", report.toString());
        return result;
    }

    @Override
    protected String normalizeText(String text) {
        if (text == null) return "";
        return text.trim().toLowerCase();
    }
}
