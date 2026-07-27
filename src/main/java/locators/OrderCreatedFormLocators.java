package locators;

import org.openqa.selenium.By;

//--ЗДЕСЬ: Локатор формы СОЗДАННОГО ЗАКАЗА


public final class OrderCreatedFormLocators {

//__Локатор окна с данными уже созданного заказа, для отслеживания подтверждения
    public static final By ORDER_CREATED_LOCATOR =
            By.xpath("//div[contains(@class, 'Order_ModalHeader') and contains(normalize-space(), 'Заказ оформлен')]");

}
