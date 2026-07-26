package pageobjects;

import locators.OrderPersonaFormLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Map;

/*--ЗДЕСЬ: Форма ПОЛЬЗОВАТЕЛЬСКИХ ДАННЫХ:

* проверка появления формы (шаблон у родителя)
* методы заполнения полей формы пользовательских данных
 > метод проверки заполнения поля метро
* итоговый метод заполнения формы пользовательских данных
* метод проверки заполненного текста в простых полях-инпутах
* клик по кнопке перехода к следующей форме (опций заказа)
* методы проверки видимости ошибки для каждого поля

*/

public class OrderPersonaFormPage extends OrderAllFormsPage {

    private final By visiblyPersonaForm = OrderPersonaFormLocators.ORDER_RECIPIENT_HEADER_LOCATOR;
    // для заполнения полей
    private final By firstNameInput = OrderPersonaFormLocators.INPUT_FIRST_NAME_LOCATOR;
    private final By lastNameInput = OrderPersonaFormLocators.INPUT_LAST_NAME_LOCATOR;
    private final By addressInput = OrderPersonaFormLocators.INPUT_ADDRESS_LOCATOR;
    private final By stationInputClick = OrderPersonaFormLocators.FIELD_METRO_LOCATOR;
    private final By metroDropdownList = OrderPersonaFormLocators.METRO_DROPDOWN_LIST_LOCATOR;
    private final By phoneInput = OrderPersonaFormLocators.INPUT_PHONE_LOCATOR;
    private final By btnNextForm = OrderPersonaFormLocators.BTN_NEXT_STEP_LOCATOR;
    // для ошибок полей
    private final By errorFirstNameInput = OrderPersonaFormLocators.ERROR_INPUT_FIRST_NAME;
    private final By errorLastNameInput = OrderPersonaFormLocators.ERROR_INPUT_LAST_NAME;
    private final By errorAddressInput = OrderPersonaFormLocators.ERROR_INPUT_ADDRESS;
    private final By errorStationInput = OrderPersonaFormLocators.ERROR_INPUT_METRO;
    private final By errorPhoneInput = OrderPersonaFormLocators.ERROR_INPUT_PHONE;

    public OrderPersonaFormPage(WebDriver driver) {
        super(driver);
    }

//_________________________ПРОВЕРКА ПОЯВЛЕНИЯ ФОРМЫ_____________________________________________________________________

    public OrderPersonaFormPage waitForPersonaFormLoaded () {
        waitForFormLoad(visiblyPersonaForm);
        return this;
    }

//_________________________ЗАПОЛНЕНИЕ ПОЛЕЙ_____________________________________________________________________________

    // Метод заполнения поля Имя
    public OrderPersonaFormPage sendFirstName(String firstName) {
        type(firstNameInput, firstName);
        return this;
    }

    // Метод заполнения поля Фамилия
    public OrderPersonaFormPage sendLastName(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    // Метод заполнения поля Адрес
    public OrderPersonaFormPage sendAddress(String address) {
        type(addressInput, address);
        return this;
    }

  //__Методы заполнения поля станции метро (с выпадающим списком)

    //Метод выбора из выпадающего списка текста:
    public OrderPersonaFormPage selectMetroStation (String stationName) {
        WebElement input =
                wait.until(ExpectedConditions.elementToBeClickable(stationInputClick));
        input.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(metroDropdownList));

        input.clear();
        input.sendKeys(stationName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(metroDropdownList));

        By optionLocator = OrderPersonaFormLocators.getMetroStationLocator(stationName);
        WebElement option =
                wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
        return this;
    }

    //Метод запускает заполнение поля и проверяет итоговые заполненные данные уже в поле
    public OrderPersonaFormPage setMetroStation (String stationName) {
        return selectMetroStation(stationName);
    }

    // Метод заполнения поля Телефон
    public OrderPersonaFormPage sendPhone(String phone) {
        type(phoneInput, phone);
        return this;
    }

//_________________________ЗАПОЛНЕНИЕ ВСЕЙ ФОРМЫ________________________________________________________________________

    public OrderPersonaFormPage fillPersonaForm (Map<String, String> data) {
        return sendFirstName(data.get("firstName"))
                .sendLastName(data.get("lastName"))
                .sendAddress(data.get("address"))
                .setMetroStation(data.get("stationName"))
                .sendPhone(data.get("phone"));
    }

//_________________________ПРОВЕРКА ЗАПОЛНЕНИЯ ПРОСТЫХ ТЕКСТОВЫХ ПОЛЕЙ__________________________________________________

    public void verifyPersonaFormFilled(Map<String, String> data) {
        assertFieldValue(firstNameInput, data.get("firstName"));
        assertFieldValue(lastNameInput, data.get("lastName"));
        assertFieldValue(addressInput, data.get("address"));
        // Метро уже проверили в setMetroStation
        assertFieldValue(phoneInput, data.get("phone"));
    }

//_________________________КЛИК ПО КНОПКЕ "Далее"_______________________________________________________________________

    //Переход к следующей форме
    public OrderOptionsFormPage submitAndGoToNextPage() {
        return clickAndGoToNextPage(
                btnNextForm,
                visiblyPersonaForm,
                () -> {
                    OrderOptionsFormPage page = new OrderOptionsFormPage(driver);
                    page.waitForOptionsFormLoaded();
                    return page;
                }
        );
    }

//________________ПРОВЕРКА ВИДИМОСТИ ОШИБОК ДЛЯ ПОЛЕЙ___________________________________________________________________

    // Ошибка для поля имя
    public OrderPersonaFormPage visiblyErrorFirstName (String invalidFirstName) {
        sendFirstName(invalidFirstName);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(firstNameInput));
        assertErrorForField(element, errorFirstNameInput);
        return this;
    }

    // Ошибка для поля фамилия
    public OrderPersonaFormPage visiblyErrorLastName (String invalidLastName) {
        sendLastName(invalidLastName);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(lastNameInput));
        assertErrorForField(element, errorLastNameInput);
        return this;
    }

    // Ошибка для поля адрес
    public OrderPersonaFormPage visiblyErrorAddress (String invalidAddress) {
        sendAddress(invalidAddress);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addressInput));
        assertErrorForField(element, errorAddressInput);
        return this;
    }
    // Ошибка для поля станции метро (rewrite)
    public OrderPersonaFormPage visiblyErrorStationMetro () {
        WebElement btn =
                wait.until(ExpectedConditions.elementToBeClickable(btnNextForm));
        btn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorStationInput));
        return this;
    }

    // Ошибка для поля телефон
    public OrderPersonaFormPage visiblyErrorPhone (String invalidPhone) {
        sendPhone(invalidPhone);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(phoneInput));
        assertErrorForField(element, errorPhoneInput);
        return this;
    }
}
