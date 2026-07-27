package pageobjects;

import locators.OrderConfirmationFormLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*--ЗДЕСЬ: Форма ПОДТВЕРЖДЕНИЯ ЗАКАЗА:

* метод чека появления формы (у родителя)
* метод клика на кнопку "Да"

*/

public class OrderConfirmationFormPage extends OrderAllFormsPage{

    private static final By visiblyConfirmForm = OrderConfirmationFormLocators.ORDER_CONFIRM_HEADER_LOCATOR;
    private static final By btnYes = OrderConfirmationFormLocators.BUTTON_ORDER_CONFIRMATION_LOCATOR;

    public OrderConfirmationFormPage (WebDriver driver) {
        super(driver);
    }

//_________________________ПРОВЕРКА ПОЯВЛЕНИЯ ФОРМЫ_____________________________________________________________________

    public OrderConfirmationFormPage waitForConfirmFormLoaded () {
        waitForFormLoad(visiblyConfirmForm);
        return this;
    }

//________________________КЛИК ПО КНОПКЕ "Да" (переход к окну готового заказа)____________________________________

    //Переход к следующей форме
    public OrderCreatedFormPage confirmOrder() {
        return clickAndGoToNextPage(
                btnYes,
                visiblyConfirmForm,
                () -> {
                    OrderCreatedFormPage page = new OrderCreatedFormPage(driver);
                    page.waitForCreatedOrderFormLoaded();
                    return page;
                }
        );
    }



}
