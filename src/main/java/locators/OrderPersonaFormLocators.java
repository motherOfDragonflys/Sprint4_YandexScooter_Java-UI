package locators;

import org.openqa.selenium.By;

/*--ЗДЕСЬ: Локаторы полей формы ПОЛЬЗОВАТЕЛЬСКИХ ДАННЫХ:

* локатор для проверки появления формы
* локаторы для полей ввода:
 - имени
 - фамилии
 - адреса
 - станции метро
   > для выпадающего списка станций метро
   > для поиска по элементам списка на соответствие вводимому тексту (методом)
 - адреса
 - телефона
* локатор для кнопки Далее
* локаторы ошибок для полей ввода:
 - имени
 - фамилии
 - адреса
 - станции метро
 - телефона
*/

public final class OrderPersonaFormLocators {

    // Для проверки появления формы ввода пользовательских данных
    public static final By ORDER_RECIPIENT_HEADER_LOCATOR =
            By.xpath("//div[contains(@class, 'Order_Header') and contains(normalize-space(), 'Для кого')]");

//______________________ЛОКАТОРЫ ДЛЯ ПОЛЕЙ ФОРМЫ________________________________________________________________________

    // Поле ввода имени
    public static final By  INPUT_FIRST_NAME_LOCATOR =
            By.cssSelector("input[placeholder*='Имя' i]");

    // Поле ввода фамилии
    public static final By  INPUT_LAST_NAME_LOCATOR =
            By.cssSelector("input[placeholder*='Фамилия' i]");

    // Поле ввода адреса
    public static final By  INPUT_ADDRESS_LOCATOR =
            By.cssSelector("input[placeholder*='Адрес' i]");

    // Поле ввода названия станции метро
    public static final By  FIELD_METRO_LOCATOR =
            By.cssSelector("input[placeholder*='метро' i]");
        // Выпадющий список:
        public static final By  METRO_DROPDOWN_LIST_LOCATOR =
            By.cssSelector("div[class*='list'], ul[class*='options'], div[class*='dropdown'], div[class*='select-search']");
        // Конкретный элемент списка
        public static By getMetroStationLocator(String stationName) {
             return By.xpath(String.format(
                "//button[contains(@class, 'select-search__option')]//div[contains(@class, 'Order_Text') and contains(normalize-space(), '%s')]",
                stationName)
             );
        }

    // Поле ввода номера телефона
    public static final By  INPUT_PHONE_LOCATOR =
            By.cssSelector("input[placeholder*='Телефон' i]");

//______________________ЛОКАТОР КНОПКИ ДАЛЕЕ____________________________________________________________________________

    public static final By BTN_NEXT_STEP_LOCATOR =
            By.xpath(
            "//div[contains(@class,'Order_NextButton')]//button[normalize-space() = 'Далее']");

//______________________ЛОКАТОРЫ ДЛЯ ОШИБОК ПОЛЕЙ_______________________________________________________________________

    // Ошибка для поля ввода имени
    public static final By ERROR_INPUT_FIRST_NAME =
            By.xpath("//input[contains(@placeholder, 'Имя')]/following-sibling::div[contains(@class, 'Input_ErrorMessage')]");

    // Ошибка для поля ввода фамилии
    public static final By ERROR_INPUT_LAST_NAME =
            By.xpath("//input[contains(@placeholder, 'Фамилия')]/following-sibling::div[contains(@class, 'Input_ErrorMessage')]");

    // Ошибка для поля ввода адреса
    public static final By ERROR_INPUT_ADDRESS =
            By.xpath("//input[contains(@placeholder, 'Адрес')]/following-sibling::div[contains(@class, 'Input_ErrorMessage')]");

    // Ошибка для поля ввода названия станции метро
    public static final By ERROR_INPUT_METRO =
            By.xpath("//div[contains(text(), 'Выберите станцию')]");

    // Ошибка для поля ввода номера телефона
    public static final By ERROR_INPUT_PHONE =
            By.xpath("//input[contains(@placeholder, 'Телефон')]/following-sibling::div[contains(@class, 'Input_ErrorMessage')]");

}

