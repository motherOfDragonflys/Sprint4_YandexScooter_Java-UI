package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;

/*--ЗДЕСЬ - ОБЩИЕ МЕТОДЫ ДЛЯ ВСЕХ ФОРМ ОФОРМЛЕНИЯ ЗАКАЗА:

* общий конструктор для времени ожидания взят у BasePage
* метод заполнения полей текстом
* метод проверки появления ошибки под полями
* метод проверки заполнения текстовых полей (соответствие ожидаемому)

*/

public class OrderAllFormsPage extends BasePage{

    public OrderAllFormsPage(WebDriver driver) {
        super(driver);
    }

//__Общий метод проверки появления формы

    protected void waitForFormLoad (By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

//__Общий метод для заполнения полей форм текстом

    protected void type(By locator, String text) {
        WebElement element =
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

//__Общий метод проверки заполнения простых текстовых полей ожидаемым текстом

    protected void assertFieldValue(By locator, String expectedValue) {
        String actualValue =
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getAttribute("value");
        Assert.assertEquals(
                String.format("Поле по локатору '%s': ожидалось '%s', но получено '%s'",
                        locator.toString(), expectedValue, actualValue),
                expectedValue,
                actualValue);
    }

//__Общий метод проверки появления ошибки для простых текстовых полей: снимает фокус с поля, ждет ошибку
    protected OrderAllFormsPage assertErrorForField(WebElement element, By errorLocator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", element);
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
        return this;
    }

}
