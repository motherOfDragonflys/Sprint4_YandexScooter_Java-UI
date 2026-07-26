package pageobjects;

import locators.BaseLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.function.Supplier;

/*--ЗДЕСЬ: Общие методы для всех страниц

* Конструктор для удобного применения времени ожидания
* Метод закрытия баннера куки
* Метод скролла до элемента
* Метод клика с переходом к следующей форме/странице
* Метод клика с переходом к следующей форме/странице с игнором видимости предыдущей
(в реализации несколько форм, появляющихся поверх предыдущей)
* Метод перехода в новую вкладку браузера
* Метод переключения на исходную вкладку
* Метод нормализации текста

*/

public abstract class BasePage {

    private static final By closeCookie = BaseLocators.CLOSE_COOKIE_BANNER_LOCATOR;

//_______________ТАЙМЕР НА ОЖИДАНИЕ (видимости, кликабельности)_________________________________________________________
    final WebDriver driver;
    final WebDriverWait wait;

    //Время ожидания, сохраним в секундах
    private static final int WAIT_TIME_SECONDS = 5;
    private static final Duration WAIT_TIME = Duration.ofSeconds(WAIT_TIME_SECONDS);

    //Конструктор для применения времени ожидания через wait
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIME);
    }

//_______________ОБЩИЕ МЕТОДЫ___________________________________________________________________________________________

    // Закрытие баннера куки
    public void closeCookieBanner() {
        if (!driver.findElements(closeCookie).isEmpty()) {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(closeCookie));
            btn.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(closeCookie));
        } //если не дождались баннера, не переживаем, продолжаем
    }

    // Скролл до элемента
    protected void scrollElementToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", //элемент - в центр
                element
        );
    }

    // Общий метод клика и перехода на следующую форму
    protected <T extends BasePage> T clickAndGoToNextPage(
            By btnLocator,
            By oldPageVisibleLocator,
            Supplier<T> nextPageSupplier) {
        // Клик по кнопке (с ожиданием кликабельности + скролл)
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));
        scrollElementToCenter(button);
        button.click();

        // Ждём исчезновения старого экрана (синхронизация перехода)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(oldPageVisibleLocator));

        // Создаём следующую страницу через лямбду
        return nextPageSupplier.get();
    }

    // Общий метод клика и перехода на следующую форму
    // (игнорируя видимую старую страницу, для форм, открывающихся поверх старых, а не вместо них)
    protected <T extends BasePage> T clickAndGoToNextPageIgnoredOldPage(
            By btnLocator,
            Supplier<T> nextPageSupplier) {
        // Клик по кнопке (с ожиданием кликабельности + скролл)
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));
        scrollElementToCenter(button);
        button.click();

        // Создаём следующую страницу через лямбду
        return nextPageSupplier.get();
    }

    /*
    Метод ожидания новой вкладки и переключения на нее,
    возвращает заголовок исходной вкладки для возврата
    */
    public String switchToNewTabAndReturnOriginalHandle() {
        String originalHandle = driver.getWindowHandle();
        //Ждем кол-ва вкладок больше одной
        wait.until(d -> d.getWindowHandles().size() > 1);

        //Хватаем первую вкладку != оригинальной, переходим на нее, получаем ее заголовок
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                return originalHandle;
            }
        }
        throw new RuntimeException("Не удалось найти новую вкладку");
    }

    // Метод переключения на исходную вкладку
    public void switchBackTo(String originalHandle) {
        driver.switchTo().window(originalHandle);
    }

    /* Нормализация текста (поясняю сама себе):
        Заменяем через \\s+ (класс пробельных символов):
обычный пробел,
табуляцию \t,
перевод строки \n,
возврат каретки \r,
неразрывный пробел &nbsp;
      на один обычный пробел.
      Убираем через trim пробелы в начале и конце строки.
*/
    protected String normalizeText(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

}
