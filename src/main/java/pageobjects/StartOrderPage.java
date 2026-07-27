package pageobjects;

import locators.BaseLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*--ЗДЕСЬ: Методы для главной страницы

* клик по кнопкам Заказать
- в шапке
- внизу страницы

*/

public class StartOrderPage extends BasePage {

    private static final By headerOrderBtn = BaseLocators.HEADER_ORDER_BUTTON;
    private static final By lowerOrderBtn = BaseLocators.LOWER_ORDER_BUTTON;
    private static final By startPageVisibleLocator = BaseLocators.START_PAGE_CONTAINER;

    public StartOrderPage(WebDriver driver) {
        super(driver);
    }

//_______________КЛИК ПО КНОПКЕ "ЗАКАЗАТЬ" (с ожиданием следующей страницы)_____________________________________________

    // Для кнопки в заголовке: вызов проверки появления следующей формы
    public OrderPersonaFormPage submitFromHeader() {
        return clickAndGoToNextPage(
                headerOrderBtn,                 // Кнопка
                startPageVisibleLocator,        // Что должно исчезнуть
                () -> {                         // Лямбда: создаём и ждём следующую страницу
                    OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
                    page.waitForPersonaFormLoaded();
                    return page;
                }
        );
    }

    // Для кнопки внизу страницы
    public OrderPersonaFormPage submitFromLower() {
        return clickAndGoToNextPage(
                lowerOrderBtn,
                startPageVisibleLocator,
                () -> {
                    OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
                    page.waitForPersonaFormLoaded();
                    return page;
                }
        );
    }
}
