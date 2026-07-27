package locators;

import org.openqa.selenium.By;

/*--ЗДЕСЬ: Локаторы полей формы ОПЦИЙ ЗАКАЗА:

* локатор для проверки появления формы
* локаторы для полей:
 - ввода даты
 - выбора срока аренды
   > для поиска по элементам выпадающего списка (метод)
 - чек-бокса с подстановкой поиска по цвету (метод)
 - ввода комментариев курьеру
* локатор для кнопки Заказать

*/

public final class OrderOptionsFormLocators {

    //Проверка появления формы опций заказа
    public static final By ORDER_OPTIONS_HEADER_LOCATOR =
            By.xpath("//div[contains(@class, 'Order_Header') and contains(normalize-space(), 'Про аренду')]");

//______________________ЛОКАТОРЫ ДЛЯ ПОЛЕЙ ФОРМЫ________________________________________________________________________

    //Поле ввода даты доставки, пока остановимся на ручном вводе в формате 12.12.12 с валидными данными
    public static final By  INPUT_DELIVERY_DATE_LOCATOR =
            By.xpath("//div[contains(@class,'react-datepicker')]//input");

    //Для клика на поле срока аренды
    public static final By  FIELD_RENTAL_PERIOD_LOCATOR =
            By.cssSelector("div.Dropdown-control[aria-haspopup='listbox']");

    //Поиск в выпадающем списке нужного значения
        public static By getDropdownRentalPeriod (String rentalPeriod) {
            return By.xpath(String.format(
               "//div[contains(@class,'Dropdown-menu')]//div[@role='option' and normalize-space()='%s']",
               rentalPeriod));
        }

    //Чек-бокс цвета самоката: для поиска по Id = black/grey
    public static By getColorLabelLocator(String colourId) {
        return By.cssSelector(String.format("label[for='%s']", colourId));
    }

    //Поле комментария курьеру
    public static final By  INPUT_COMMENT_LOCATOR =
            By.cssSelector("input[placeholder*='курьер' i]");

//______________________ЛОКАТОР КНОПКИ ЗАКАЗАТЬ (В ФОРМЕ, ПЕРЕХОД К СЛЕД. ФОРМЕ)________________________________________

    public static final By BUTTON_ORDER_IN_FORM =
            By.xpath("//div[contains(@class,'Order_Buttons')]//button[normalize-space()='Заказать']");

}
