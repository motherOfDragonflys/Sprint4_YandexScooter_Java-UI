package test;

import basetest.BaseTest;
import org.junit.Before;
import org.junit.Test;
import pageobjects.OrderPersonaFormPage;

/*
--ЗДЕСЬ: Тесты на появление ошибки для полей формы ввода пользовательских данных заказа.
*/

public class ErrorsForFieldsOrderFormsTests extends BaseTest {

    @Before
    public void startPage () {
        driver.get(ORDER_URL);
    }

    //Тест ошибки поля имя (при снятии фокуса)
    @Test
    public void visibilityErrorFirstNameFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorFirstName("12345");
    }

    //Тест ошибки поля фамилия (при снятии фокуса)
    @Test
    public void visibilityErrorLastNameFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorLastName("12345");
    }

    //Тест ошибки поля адрес (при снятии фокуса)
    @Test
    public void visibilityErrorAddressFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorAddress("нет");
    }

    //Тест ошибки поля станция метро (при нажатии Далее)
    @Test
    public void visibilityErrorMetroStationFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.closeCookieBanner();
        page.visiblyErrorStationMetro();
    }

    //Тест ошибки поля телефон (при снятии фокуса)
    @Test
    public void visibilityErrorPhoneFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorPhone("нет");
    }
}
