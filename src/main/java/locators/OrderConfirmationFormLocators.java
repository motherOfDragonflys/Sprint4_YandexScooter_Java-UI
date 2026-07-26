package locators;

import org.openqa.selenium.By;

/*--ЗДЕСЬ: Локаторы формы ПОДТВЕРЖДЕНИЯ ЗАКАЗА:

* локатор для проверки появления формы
* локатор для кнопки подтверждения

*/

public final class OrderConfirmationFormLocators {

//__Для проверки появления формы подтверждения заказа___________________________________________________________________
    public static final By ORDER_CONFIRM_HEADER_LOCATOR =
            By.xpath("//div[contains(@class, 'Order_ModalHeader') and contains(normalize-space(), 'Хотите оформить')]");

//__Локатор для кнопки подтверждения____________________________________________________________________________________
    public static final By BUTTON_ORDER_CONFIRMATION_LOCATOR =
            By.xpath("//div[contains(@class, 'Order_Buttons')]//button[contains(normalize-space(), 'Да')]");

}
