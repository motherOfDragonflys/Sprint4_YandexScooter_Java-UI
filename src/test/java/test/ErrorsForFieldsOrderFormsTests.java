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
        driver.get("https://qa-scooter.praktikum-services.ru/order");
    }

    //Тест ошибки поля имя (при снятии фокуса)
    @Test
    public void visibilityErrorFirstNameFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorFirstName("12345");
        System.out.println(
                "[SUCCESS visibilityErrorFirstNameField]: При вводе невалидных данных появляется ошибка"
        );
    }

    //Тест ошибки поля фамилия (при снятии фокуса)
    @Test
    public void visibilityErrorLastNameFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorLastName("12345");
        System.out.println(
                "[SUCCESS visibilityErrorLastNameField]: При вводе невалидных данных появляется ошибка"
        );
    }

    //Тест ошибки поля адрес (при снятии фокуса)
    @Test
    public void visibilityErrorAddressFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorAddress("нет");
        System.out.println(
                "[SUCCESS visibilityErrorAddressField]: При вводе невалидных данных появляется ошибка"
        );
    }

    //Тест ошибки поля станция метро (при нажатии Далее)
    @Test
    public void visibilityErrorMetroStationFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.closeCookieBanner();
        page.visiblyErrorStationMetro();
        System.out.println(
                "[SUCCESS visibilityErrorMetroStationField]: При пустом поле появляется ошибка"
        );
    }

    //Тест ошибки поля телефон (при снятии фокуса)
    @Test
    public void visibilityErrorPhoneFieldTest () {
        OrderPersonaFormPage page = new OrderPersonaFormPage(driver);
        page.visiblyErrorPhone("нет");
        System.out.println(
                "[SUCCESS visibilityErrorPhoneField]: При вводе невалидных данных появляется ошибка"
        );
    }
}
