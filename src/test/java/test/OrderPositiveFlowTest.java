package test;

import basetest.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageobjects.OrderPersonaFormPage;
import pageobjects.StartOrderPage;
import utils.TestDataLoader;
import java.io.IOException;
import java.util.*;

/*
--ЗДЕСЬ: Тест позитивного сценария заказа самоката

- Нажать кнопку «Заказать». На странице две кнопки заказа.
- Заполнить форму заказа.
- Проверить, что появилось всплывающее окно с сообщением об успешном создании заказа.
- Провести тесты с разными данными: минимум два набора.
*/

@RunWith(Parameterized.class)
public class OrderPositiveFlowTest extends BaseTest {

    private final String buttonType; // "header" или "lower"
    private final Map<String, String> personaData; // данные первой формы
    private final Map<String, String> optionsData; // данные второй формы

    public OrderPositiveFlowTest(
            String buttonType,
            Map<String, String> personaData,
            Map<String, String> optionsData) {
        this.buttonType = buttonType;
        this.personaData = personaData;
        this.optionsData = optionsData;
    }

    @Parameterized.Parameters(name = "Test with button={0}")
    public static Collection<Object[]> data() throws IOException {

        //Читаем файл для формы персональных данных
        List<Map<String, String>> personaCases =
                TestDataLoader.loadAndValidatePersonaCases("/positive-order-scooter-persona-form.json");

        //Читаем файл для формы опций заказа
        List<Map<String, String>> optionsCases =
                TestDataLoader.loadAndValidateOptionsCases("/positive-order-scooter-options-form.json");

        var result = new ArrayList<Object[]>();

        String[] buttons = {"header", "lower"};

        //Сбор комбинаций
        for (String button : buttons) {
            for (Map<String, String> personaCase : personaCases) {
                for (Map<String, String> optionsCase : optionsCases) {
                    result.add(new Object[]
                           {button,
                            personaCase,
                            optionsCase}
                    );
                }
            }
        }

        System.out.println("Тестовых комбинаций: " + result.size());
        return result;
    }

//Для проверки без фейла - раскоммент Override для FireFox и применить импорты: тут тест проходит
/*
       @Override
        public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru");
    }
 */

    @Test
    //В тесте для наглядности логирую шаги перед действиями
    public void shouldCompletePositiveOrderFlow() {

        driver.get("https://qa-scooter.praktikum-services.ru");

        System.out.println("Запуск теста. Кнопка: " + buttonType);

        StartOrderPage startPage = new StartOrderPage(driver);
        OrderPersonaFormPage personaPage;

        //Закрываем головную боль в виде куки баннера (для хрома: ломал клики)
        startPage.closeCookieBanner();

        //Выбор кнопки для старта, клик, переход к 1й форме:
        if ("header".equals(buttonType)) {
            System.out.println("Клик по кнопке в шапке >>>");
            personaPage = startPage.submitFromHeader();
        } else {
            System.out.println("Клик по кнопке внизу >>>");
            personaPage = startPage.submitFromLower();
        }
        //Ждем появления формы
        personaPage.waitForPersonaFormLoaded();

        System.out.println("Заполнение формы персональных данных >>>");
        personaPage
                .fillPersonaForm(personaData)
                .verifyPersonaFormFilled(personaData);

        System.out.println("Переход на форму заполнения опций >>>");
        var optionsPage = personaPage.submitAndGoToNextPage();
        optionsPage.waitForOptionsFormLoaded();

        System.out.println("Заполнение формы опций заказа >>>");
        optionsPage
                .fillOrderForm(optionsData)
                .verifyOptionsFormFilled(optionsData);

        System.out.println("Переход на форму подтверждения >>>");
        var confirmPage = optionsPage.submitAndGoToNextPage();
        confirmPage.waitForConfirmFormLoaded();

        //Хром тут стоит на переходе, безуспешно пытаясь кликать
        System.out.println("Переход на форму готового заказа >>>");
        var createdOrderForm = confirmPage.confirmOrder();
        createdOrderForm.waitForCreatedOrderFormLoaded();

        System.out.println("[SUCCESS PositiveOrderTest]: Заказ создан для кнопки: " + buttonType);
    }
}
