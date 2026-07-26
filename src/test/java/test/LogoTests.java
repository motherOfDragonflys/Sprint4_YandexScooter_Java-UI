package test;

import basetest.BaseTest;
import org.junit.Test;
import pageobjects.HeaderLogoPage;
import utils.UrlUtils;
import java.util.Objects;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/*
--ЗДЕСЬ: Тесты логотипов Яндекс и Самокат

1. Если нажать на логотип Яндекса, в новом окне откроется главная страница Яндекса
2. Если нажать на логотип Самоката, попадёшь на главную страницу Самоката
*/

public class LogoTests extends BaseTest {

    //Лого Яндекс
    @Test
    public void logoYandexClickMustReturnYandexHomePage () {

        driver.get("https://qa-scooter.praktikum-services.ru");

        HeaderLogoPage headerYandex = new HeaderLogoPage(driver);

        headerYandex.clickLogoYandex();

        //Переход на открывшуюся вкладку
        String originalWindowHandle = headerYandex
                .switchToNewTabAndReturnOriginalHandle();
        System.out.println("Фактический заголовок страницы: " + driver.getTitle());

        //Ждем открытия вкладки с доменом ya.ru (спойлер: не дождемся)
        headerYandex.getWait()
                .until(d -> UrlUtils.hasDomain(d, "ya.ru"));

        /*
        Не поймаем ошибку, если:
        - попадаем на https://ya.ru/ или https://ya.ru без продолжений
        - после https://ya.ru/ только UTM‑метки
         */
        String url = driver.getCurrentUrl();
        if (url == null) {
            fail("getCurrentUrl() вернул null");
        } // в этом if избавляемся от ругательств идеи, на случай url==null
        if (url.startsWith("https://ya.ru/")) {
            //считываем длину полученного url
            /* Пояснение: т.к. нумерация с 0, последний символ (15-й по факту счета с 1) будет с индексом 14.
            .substring начинает счет с следующего индекса (15), который как раз равен факту длины Url-а. */
            String pathAndQuery = url.substring("https://ya.ru/".length());
            assertThat(pathAndQuery, either(is("")).or(startsWith("?")));
        } else if (url.equals("https://ya.ru")) {
            //исключаем ошибку без слэша в конце адреса, тут нет действий, пусть идея ругается
        } else {
            fail("URL не соответствует ожидаемому: " + url);
        }

        //Возвращаемся в исходное окно
        headerYandex.switchBackTo(originalWindowHandle);

        //Если успех:
        System.out.println("[SUCCESS LogoYandexTest]: Клик по лого Яндекс ведет на главную страницу Яндекса");
    }

    //Лого Самокат
    @Test
    public void logoScooterClickMustReturnScooterHomePage () {

        driver.get("https://qa-scooter.praktikum-services.ru/order");

        HeaderLogoPage headerScooter = new HeaderLogoPage(driver);

        //Получаем Url до клика на заголовок
        String urlBeforeClick = driver.getCurrentUrl();
        System.out.println("URL ДО клика: " + urlBeforeClick);

        headerScooter.clickLogoScooter();

        //Ждем, что Url действительно стал другим
        headerScooter
                .getWait().until(d -> !Objects.equals(urlBeforeClick, d.getCurrentUrl()));

        //Записываем новый Url
        String urlAfterClick = driver.getCurrentUrl();
        System.out.println("URL ПОСЛЕ клика: " + urlAfterClick);

        //Получим ошибки, если не на главной странице
        assertThat(urlAfterClick, containsString("qa-scooter.praktikum-services.ru"));
        //Убедимся, что не попали на заказ с параметрами
        assertThat(urlAfterClick, not(containsString("/order?")));

        //Если успех:
        System.out.println("[SUCCESS LogoScooterTest]: Клик по лого Самокат ведет на главную страницу Самоката");
    }
}
