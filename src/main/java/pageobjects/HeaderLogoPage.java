package pageobjects;

import locators.BaseLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*--ЗДЕСЬ: Методы для логотипов в шапке

* клик по Яндекс
* клик по Самокат
* метод для применения времени ожидания в тестах

*/

public class HeaderLogoPage extends BasePage {

    private static final By logoYandex = BaseLocators.LOGO_YANDEX;
    private static final By logoScooter = BaseLocators.LOGO_SCOOTER;

    public HeaderLogoPage(WebDriver driver) {
        super(driver);
    }

    // Клик по лого Яндекс
    public void clickLogoYandex () {
        WebElement logo =
                wait.until(ExpectedConditions.elementToBeClickable(logoYandex));
        logo.click();
    }

    // Клик по лого Самокат
    public void clickLogoScooter () {
        WebElement logo =
                wait.until(ExpectedConditions.elementToBeClickable(logoScooter));
        logo.click();
    }

    // Для ожидания в тестах заголовков
    public WebDriverWait getWait() {
        return wait;
    }
}
