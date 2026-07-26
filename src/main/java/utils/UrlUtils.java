package utils;

import org.openqa.selenium.WebDriver;
import java.net.URI;
import java.net.URISyntaxException;

/*
--ЗДЕСЬ: Утилита для проверки Url

использую в проверке клика на лого Яндекс
*/

public final class UrlUtils {
    private UrlUtils() {}

    //Проверка, совпадает ли текущий домен страницы с ожидаемым
    public static boolean hasDomain(WebDriver driver, String expectedDomain) {
        //если нет url, считай домен не совпадает
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl == null) {
            return false;
        }
        try {
            URI uri = new URI(driver.getCurrentUrl());
            //Сравниваем хост с ожидаемым
            return expectedDomain.equals(uri.getHost());
        } catch (URISyntaxException e) {
            //url есть, но некорректный, считаем, что домен не совпал
            return false;
        }
    }
}
