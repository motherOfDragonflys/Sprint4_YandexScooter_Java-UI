package pageobjects;

import locators.OrderOptionsFormLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Map;

/*--ЗДЕСЬ: Форма ОПЦИЙ ЗАКАЗА:

* проверка появления формы (шаблон у родителя)
* методы текстового заполнения полей формы опций заказа
   > проверки на сложные поля (выпадающий список и чек-бокс)
* итоговый метод заполнения формы опций заказа
* метод проверки заполненного текста в полях-инпутах
* клик по кнопке перехода к подтверждению заказа

*/

public class OrderOptionsFormPage extends OrderAllFormsPage{

    private final By visiblyOptionsForm = OrderOptionsFormLocators.ORDER_OPTIONS_HEADER_LOCATOR;

    private final By deliveryDateInput = OrderOptionsFormLocators.INPUT_DELIVERY_DATE_LOCATOR;
    private final By rentalPeriodField = OrderOptionsFormLocators.FIELD_RENTAL_PERIOD_LOCATOR;
    private final By commentInput = OrderOptionsFormLocators.INPUT_COMMENT_LOCATOR;
    private final By btnOrderClick = OrderOptionsFormLocators.BUTTON_ORDER_IN_FORM;

    public OrderOptionsFormPage (WebDriver driver) {
        super(driver);
    }

//_________________________ПРОВЕРКА ПОЯВЛЕНИЯ ФОРМЫ_____________________________________________________________________

    public OrderOptionsFormPage waitForOptionsFormLoaded () {
        waitForFormLoad(visiblyOptionsForm);
        return this;
    }

//______________________________ЗАПОЛНЕНИЕ ПОЛЕЙ________________________________________________________________________

    // Метод заполнения поля даты доставки (текстовое заполнение)
    public OrderOptionsFormPage sendDeliveryDate (String deliveryDate) {
        type(deliveryDateInput, deliveryDate);
        closeDatepickerIfOpen();
        return this;
    }
    // Закрываем календарь:
    private void closeDatepickerIfOpen() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

  //__Методы заполнения и проверки поля срока аренды из выпадающего списка

    public OrderOptionsFormPage selectRentalPeriod (String rentalPeriod) {
        //клик на поле, чтобы увидеть список
        WebElement filed =
                wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        filed.click();

        //работа с выпадающим списком:
        By rentalPeriodListLocator =
                OrderOptionsFormLocators.getDropdownRentalPeriod(rentalPeriod);
        WebElement waitingElement =
                wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodListLocator));
        //скроллим, используя метод из BasePage
        scrollElementToCenter(waitingElement);
        //и кликаем
        waitingElement.click();
        return this;
    }
    //Проверяем, что в поле попало то, что надо
    public OrderOptionsFormPage setRentalPeriod (String rentalPeriod) {
        selectRentalPeriod(rentalPeriod);

        //здесь ждем появления текста в поле и сравниваем выбранным нами
        wait.until(driver -> {
            WebElement field = driver.findElement(rentalPeriodField);
            String text = field.getText().trim();
            return !text.isEmpty() && rentalPeriod.equals(text);
        });

        return this;
    }

  //__Методы выбора цвета в чек-боксе и проверки отметки

    // Выбор по colourId
    public OrderOptionsFormPage selectScooterColour(String colourId) {

        By labelLocator =
                OrderOptionsFormLocators.getColorLabelLocator(colourId);
        WebElement label =
                wait.until(ExpectedConditions.elementToBeClickable(labelLocator));
        label.click();

        return this;
    }

    // Проверка, что цвет выбран
    public OrderOptionsFormPage setScooterColor(String colourId) {
        selectScooterColour(colourId);
        // Ждём, пока input с этим ID станет selected
        By inputLocator = By.id(colourId);
        wait.until(ExpectedConditions.elementToBeSelected(inputLocator));

        return this;
    }

    //__Метод заполнения поля для комментариев курьеру
    public OrderOptionsFormPage sendComment (String comment) {
        type(commentInput, comment);
        return this;
    }

//_________________________________ЗАПОЛНЕНИЕ ФОРМЫ_____________________________________________________________________

    public OrderOptionsFormPage fillOrderForm (Map<String, String> data) {
        return sendDeliveryDate(data.get("deliveryDate"))
                .setRentalPeriod(data.get("rentalPeriod"))
                .setScooterColor(data.get("colourId"))
                .sendComment(data.get("comment"));
    }

//________________________ПРОВЕРКА ЗАПОЛНЕНИЯ ТЕКСТОВЫХ ПОЛЕЙ___________________________________________________________

    public void verifyOptionsFormFilled(Map<String, String> data) {
        assertFieldValue(deliveryDateInput, data.get("deliveryDate"));
        //Срок аренды проверили в заполнении
        //Чек бокс проверили при заполнении
        assertFieldValue(commentInput, data.get("comment"));
    }

//________________КЛИК ПО КНОПКЕ "Заказать" (переход к подтверждению заказа)____________________________________________

    //Переход к следующей форме
    public OrderConfirmationFormPage submitAndGoToNextPage() {
        return clickAndGoToNextPageIgnoredOldPage(
                btnOrderClick,
                () -> {
                    OrderConfirmationFormPage page = new OrderConfirmationFormPage(driver);
                    page.waitForConfirmFormLoaded();
                    return page;
                }
        );
    }

//________________ПРОВЕРКА ВИДИМОСТИ ОШИБОК ДЛЯ ПОЛЕЙ___________________________________________________________________

    /*

    Нет реализаций для полей дата, срок аренды и выбор цвета.
    Для поля комментарий есть локатор с текстом ошибки, но в JS нет условий для ее появления

     */
}
