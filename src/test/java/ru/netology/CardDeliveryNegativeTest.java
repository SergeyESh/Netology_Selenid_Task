package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryNegativeTest {

    private String currentDate (){
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String Date = currentDate();

    @Test
    void shouldInvalidCity() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Лондон");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(Date);
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+79163131737");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='city'] span").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldInvalidDate() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys("03.12.2022");
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+79163131737");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='date'] span").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldInvalidName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(Date);
        $("[data-test-id='name'] input").sendKeys("Ivanov Ivan");
        $("[data-test-id='phone'] input").sendKeys("+79163131737");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='name'] span").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldInvalidPhone() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(Date);
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+7916313173");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='phone'] span").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldInvalidCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(Date);
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+79163131737");
        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='agreement'] .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}