package locators;

import org.openqa.selenium.By;

/*--ЗДЕСЬ: Локаторы стартовой страницы

* локаторы стартовой страницы:
 - кнопка закрытия баннера куки
 - маркер стартовой страницы
 - для заголовков Faq
* локаторы заголовка:
 - кнопка "Самокат" в шапке
 - кнопка "Яндекс" в шапке
 - кнопка проверки статуса заказа
 - поле ввода номера заказа
 - кнопка Go
* локаторы кнопок для заказа самоката:
 - кнопка в шапке
 - кнопка внизу страницы

*/

public final class BaseLocators {

//__________________СТАРТОВАЯ СТРАНИЦА__________________________________________________________________________________

    // Кнопка закрытия баннера, локатор для метода
    public static final By CLOSE_COOKIE_BANNER_LOCATOR = By.id("rcc-confirm-button");

    // Локатор стартовой страницы
    public static final By START_PAGE_CONTAINER = By.cssSelector("div[class*='HomePage']");

    // Локатор для Faq - общая часть всех заголовков выпадающих списков (для сбора вопросов Faq в лист)
    public static final By QUESTIONS_FAQ_LOCATOR = By.cssSelector("[id^='accordion__heading-']");

//__________________ССЫЛКИ ЗАГОЛОВКА____________________________________________________________________________________

    // Ссылка-кнопка "Самокат" в шапке
    public static final By LOGO_SCOOTER = By.cssSelector("[class*='Header_LogoScooter']");

    // Ссылка-кнопка "Яндекс" в шапке
    public static final By LOGO_YANDEX = By.cssSelector("[class*='Header_LogoYandex']");

    // Кнопка проверки статуса заказа
    public static final By HEADER_BTN_ORDER_STATUS = By.cssSelector("[class*='Header_Link']");

    // Поле ввода номера заказа
    public static final By HEADER_INPUT_ORDER_STATUS = By.cssSelector("[class*='Header_Input']");

    // Кнопка Go
    public static final By HEADER_BTN_GO =
            By.xpath("//div[contains(@class, 'Header_SearchInput')]//button[normalize-space()='Go!']");

//__________________КНОПКИ ЗАКАЗА САМОКАТА______________________________________________________________________________

    // Кнопка для начала заказа в заголовке
    public static final By HEADER_ORDER_BUTTON =
            By.xpath("//div[contains(@class, 'Header_Nav')]/button[normalize-space() = 'Заказать']");

    // Кнопка для начала заказа в конце страницы
    public static final By LOWER_ORDER_BUTTON =
            By.xpath("//div[contains(@class, 'Home_FinishButton')]/button[normalize-space() = 'Заказать']");

}
