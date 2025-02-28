package com.codeborne.selenide.commands;

import com.codeborne.selenide.DriverStub;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.WebElementSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

final class SetValueCommandTest {
  private final SelenideElement proxy = mock(SelenideElement.class);
  private final WebElementSource locator = mock(WebElementSource.class);
  private final SetValue command = new SetValue();
  private final WebElement mockedFoundElement = mock(WebElement.class);

  @BeforeEach
  void setup() {
    when(locator.findAndAssertElementIsInteractable()).thenReturn(mockedFoundElement);
    when(locator.driver()).thenReturn(new DriverStub());
  }

  @AfterEach
  void noMoreInteractions() {
    verify(locator).driver();
    verify(locator).findAndAssertElementIsInteractable();
    verifyNoMoreInteractions(proxy, locator, mockedFoundElement);
  }

  @Test
  void clearsTheInputIfArgsTextIsEmpty() {
    WebElement returnedElement = command.execute(proxy, locator, new Object[]{""});
    assertThat(returnedElement).isEqualTo(proxy);
    verify(mockedFoundElement).clear();
    verify(locator).description();
  }

  @Test
  void typesGivenTextIntoInputField() {
    WebElement returnedElement = command.execute(proxy, locator, new Object[]{"Stalker"});
    assertThat(returnedElement).isEqualTo(proxy);
    verify(mockedFoundElement).clear();
    verify(mockedFoundElement).sendKeys("Stalker");
    verify(locator).description();
  }
}
