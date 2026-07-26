package pageobjects;

import locators.BaseLocators;
import locators.SearchOrderLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;

/*--ЗДЕСЬ: Методы для поиска заказа по номеру

* клик по кнопке Статус заказа
* ввод текста в поле для номера
* клик по кнопке Go
* поиск видимой картинки (нет заказа) по локатору

*/

public class SearchOrderPage extends OrderAllFormsPage {

    public static final By btnOrderStatus = BaseLocators.HEADER_BTN_ORDER_STATUS;
    public static final By inputOrderSearch = BaseLocators.HEADER_INPUT_ORDER_STATUS;
    public static final By btnGo = BaseLocators.HEADER_BTN_GO;
    public static final By notFoundPage = SearchOrderLocators.NOT_FOUND_ORDER_PAGE;

    public SearchOrderPage (WebDriver driver) {
        super (driver);
    }

    // Клик по кнопке Статус заказа
    public SearchOrderPage clickBtnOrderStatus () {
        wait.until(ExpectedConditions.elementToBeClickable(btnOrderStatus))
                .click();
        return this;
    }

    // Ввод с поле очисткой текста, конструктор в BasePage
    public SearchOrderPage inputOrderNumber (String input) {
        type(inputOrderSearch, input);
        return this;
    }

    // Клик Go
    public SearchOrderPage clickGo () {
        wait.until(ExpectedConditions.elementToBeClickable(btnGo))
                .click();
        return this;
    }

    // Поиск видимой картинки для несуществующего номера заказа
    public boolean isOrderNotFoundMessageVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(notFoundPage))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

}
