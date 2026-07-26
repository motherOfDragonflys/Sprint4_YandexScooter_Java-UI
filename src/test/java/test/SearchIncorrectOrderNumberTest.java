package test;

import basetest.BaseTest;
import org.junit.Test;
import pageobjects.SearchOrderPage;
import static org.junit.Assert.assertTrue;

/*
--ЗДЕСЬ: Тест поиска по неправильному номеру заказа

Если ввести неправильный номер заказа, попадёшь на страницу статуса заказа.
На ней должно быть написано, что такого заказа нет.
 */
public class SearchIncorrectOrderNumberTest extends BaseTest {

    @Test
    public void shouldShowErrorWhenOrderNotFound () {

        driver.get("https://qa-scooter.praktikum-services.ru");

        SearchOrderPage page = new SearchOrderPage(driver);

        page
                .clickBtnOrderStatus()
                .inputOrderNumber("mrmrmr")
                .clickGo();

        //Если не появится искомая картинка, получим ошибку
        assertTrue(page.isOrderNotFoundMessageVisible());

        System.out.println(
                "[SUCCESS SearchIncorrectOrderNumberTest]: При вводе некорректного номера заказа появилась надпись 'Такого заказа не существует'"
        );
    }
}
