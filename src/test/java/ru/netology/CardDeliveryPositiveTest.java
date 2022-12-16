package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryPositiveTest {

    private String currentDate (){
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String Date = currentDate();

    @Test
    void shouldPositiveCardDeliveryOrder() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(Date);
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+79163131737");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(exactText("Встреча успешно забронирована на " + Date));
    }
}