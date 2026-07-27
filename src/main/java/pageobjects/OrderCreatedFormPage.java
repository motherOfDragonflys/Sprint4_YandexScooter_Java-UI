package pageobjects;

import locators.OrderCreatedFormLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*--ЗДЕСЬ: Форма СОЗДАННОГО ЗАКАЗА:

* проверка появления формы (шаблон у родителя)

*/

public class OrderCreatedFormPage extends OrderAllFormsPage {

    private static final By visiblyCreatedOrderForm = OrderCreatedFormLocators.ORDER_CREATED_LOCATOR;

    public OrderCreatedFormPage(WebDriver driver) {
        super(driver);
    }

    // Проверка появления формы готового заказа
    public OrderCreatedFormPage waitForCreatedOrderFormLoaded() {
        waitForFormLoad(visiblyCreatedOrderForm);
        return this;
    }
}
