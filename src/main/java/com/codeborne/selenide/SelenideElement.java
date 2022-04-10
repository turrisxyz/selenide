package com.codeborne.selenide;

import com.codeborne.selenide.files.FileFilter;
import com.codeborne.selenide.impl.WebElementSource;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

/**
 * Wrapper around {@link WebElement} with additional methods like
 * {@link #shouldBe(Condition...)} and {@link #shouldHave(Condition...)}
 */
@ParametersAreNonnullByDefault
public interface SelenideElement extends WebElement, WrapsDriver, WrapsElement, Locatable, TakesScreenshot {
  /**
   * <b>Implementation details:</b>
   *
   * <p>If Configuration.fastSetValue is true, sets value by javascript instead of using Selenium built-in "sendKey" function
   * and trigger "focus", "keydown", "keypress", "input", "keyup", "change" events.
   *
   * <p>In other case behavior will be:
   * <pre>
   * 1. WebElement.clear()
   * 2. WebElement.sendKeys(text)
   * 3. Trigger change event
   * </pre>
   *
   * @param text Any text to enter into the text field or set by value for select/radio.
   * @see com.codeborne.selenide.commands.SetValue
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement setValue(@Nullable CharSequence text);

  /**
   * Same as {@link #setValue(java.lang.CharSequence)}
   *
   * @see com.codeborne.selenide.commands.Val
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement val(@Nullable CharSequence text);

  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement setValue(SetValueOptions text);

  /**
   * Append given text to the text field and trigger "change" event.
   * <p>
   * Implementation details:
   * This is the same as
   * <pre>
   *   1. WebElement.sendKeys(text)
   *   2. Trigger change event
   * </pre>
   *
   * @param text Any text to append into the text field.
   * @see com.codeborne.selenide.commands.Append
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement append(String text);

  /**
   * Press ENTER. Useful for input field and textareas: <pre>
   *  $("query").val("Aikido techniques").pressEnter();</pre>
   * <p>
   * Implementation details:
   * Check that element is displayed and execute <pre>
   *  WebElement.sendKeys(Keys.ENTER)</pre>
   *
   * @see com.codeborne.selenide.commands.PressEnter
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement pressEnter();

  /**
   * Press TAB. Useful for input field and textareas: <pre>
   *  $("#to").val("stiven@seagal.com").pressTab();</pre>
   * <p>
   * Implementation details:
   * Check that element is displayed and execute <pre>
   *  WebElement.sendKeys(Keys.TAB)</pre>
   *
   * @see com.codeborne.selenide.commands.PressTab
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement pressTab();

  /**
   * Press ESCAPE. Useful for input field and textareas: <pre>
   *  $(".edit").click().pressEscape();</pre>
   * <p>
   * Implementation details:
   * Check that element is displayed and execute <pre>
   *  WebElement.sendKeys(Keys.ESCAPE)</pre>
   *
   * @see com.codeborne.selenide.commands.PressEscape
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement pressEscape();

  /**
   * Get the visible text of this element, including sub-elements without leading/trailing whitespace.
   * NB! For "select", returns text(s) of selected option(s).
   *
   * @return The innerText of this element
   * @see com.codeborne.selenide.commands.GetText
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  @Override
  String getText();

  /**
   * Element alias, which can be set with {@link #as(String text)}
   *
   * @return Alias of this element or null, if element alias is not set
   * @see com.codeborne.selenide.commands.GetAlias
   * @since 5.20.0
   */
  @CheckReturnValue
  @Nullable
  String getAlias();

  /**
   * Short form of {@link #getText()}
   *
   * @see WebElement#getText()
   * @see com.codeborne.selenide.commands.GetText
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  String text();

  /**
   * Get the text of the element WITHOUT children.
   *
   * @see com.codeborne.selenide.commands.GetOwnText
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  String getOwnText();

  /**
   * Get the text code of the element with children.
   * <p>
   * It can be used to get the text of a hidden element.
   * <p>
   * Short form of getAttribute("textContent") or getAttribute("innerText") depending on browser.
   * <p>
   * @see com.codeborne.selenide.commands.GetInnerText
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  String innerText();

  /**
   * Get the HTML code of the element with children.
   * <p>
   * It can be used to get the html of a hidden element.
   * <p>
   * Short form of getAttribute("innerHTML")
   * <p>
   * @see com.codeborne.selenide.commands.GetInnerHtml
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  String innerHtml();

  /**
   * Get the attribute of the element. Synonym for {@link #getAttribute(String)}
   *
   * @return null if attribute is missing
   * @see com.codeborne.selenide.commands.GetAttribute
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nullable
  String attr(String attributeName);

  /**
   * Get the "name" attribute of the element
   *
   * @return attribute "name" value or null if attribute is missing
   * @see com.codeborne.selenide.commands.GetName
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nullable
  String name();

  /**
   * Get the "value" attribute of the element
   * Same as {@link #getValue()}
   *
   * @return attribute "value" value or null if attribute is missing
   * @see com.codeborne.selenide.commands.Val
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nullable
  String val();

  /**
   * Get the "value" attribute of the element
   *
   * @return attribute "value" value or null if attribute is missing
   * @see com.codeborne.selenide.commands.GetValue
   * @since 3.1
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nullable
  String getValue();

  /**
   * Get the property value of the pseudo-element
   *
   * @param pseudoElementName pseudo-element name of the element,
   *                          ":before", ":after", ":first-letter", ":first-line", ":selection"
   * @param propertyName      property name of the pseudo-element
   * @return the property value or "" if the property is missing
   * @see com.codeborne.selenide.commands.GetPseudoValue
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  String pseudo(String pseudoElementName, String propertyName);

  /**
   * Get content of the pseudo-element
   *
   * @param pseudoElementName pseudo-element name of the element, ":before", ":after"
   * @return the content value or "none" if the content is missing
   * @see com.codeborne.selenide.commands.GetPseudoValue
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  String pseudo(String pseudoElementName);

  /**
   * Select radio button
   *
   * @param value value of radio button to select
   * @return selected "input type=radio" element
   * @see com.codeborne.selenide.commands.SelectRadio
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement selectRadio(String value);

  /**
   * Get value of attribute "data-<i>dataAttributeName</i>"
   *
   * @see com.codeborne.selenide.commands.GetDataAttribute
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nullable
  String data(String dataAttributeName);

  /**
   * {@inheritDoc}
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @Override
  @Nullable
  @CheckReturnValue
  String getAttribute(String name);

  /**
   * {@inheritDoc}
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @Override
  @Nonnull
  @CheckReturnValue
  String getCssValue(String propertyName);

  /**
   * Checks if element exists true on the current page.
   *
   * @return false if element is not found, browser is closed or any WebDriver exception happened
   * @see com.codeborne.selenide.commands.Exists
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  boolean exists();

  /**
   * Check if this element exists and visible.
   *
   * @return false if element does not exists, is invisible, browser is closed or any WebDriver exception happened.
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @Override
  @CheckReturnValue
  boolean isDisplayed();

  /**
   * immediately returns true if element matches given condition
   * Method doesn't wait!
   *
   * WARNING: This method can help implementing crooks, but it is not needed for typical ui tests.
   *
   * @see #has
   * @see com.codeborne.selenide.commands.Matches
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  boolean is(Condition condition);

  /**
   * immediately returns true if element matches given condition
   * Method doesn't wait!
   * WARNING: This method can help implementing crooks, but it is not needed for typical ui tests.
   *
   * @see #is
   * @see com.codeborne.selenide.commands.Matches
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  boolean has(Condition condition);

  /**
   * Set checkbox state to CHECKED or UNCHECKED.
   *
   * @param selected true for checked and false for unchecked
   * @see com.codeborne.selenide.commands.SetSelected
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement setSelected(boolean selected);

  /**
   * <p>Checks that given element meets all of given conditions.</p>
   *
   * <p>
   * IMPORTANT: If element does not match then conditions immediately, waits up to
   * 4 seconds until element meets the conditions. It's extremely useful for dynamic content.
   * </p>
   *
   * <p>Timeout is configurable via {@link com.codeborne.selenide.Configuration#timeout}</p>
   *
   * <p>For example: {@code
   * $("#errorMessage").should(appear);
   * }</p>
   *
   * @return Given element, useful for chaining:
   * {@code $("#errorMessage").should(appear).shouldBe(enabled);}
   * @see com.codeborne.selenide.Config#timeout
   * @see com.codeborne.selenide.commands.Should
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement should(Condition... condition);

  /**
   * Wait until given element meets given condition (with given timeout)
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement should(Condition condition, Duration timeout);

  /**
   * <p>Synonym for {@link #should(com.codeborne.selenide.Condition...)}. Useful for better readability.</p>
   * <p>For example: {@code
   * $("#errorMessage").shouldHave(text("Hello"), text("World"));
   * }</p>
   *
   * @see SelenideElement#should(com.codeborne.selenide.Condition...)
   * @see com.codeborne.selenide.commands.ShouldHave
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldHave(Condition... condition);

  /**
   * Wait until given element meets given condition (with given timeout)
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldHave(Condition condition, Duration timeout);

  /**
   * <p>Synonym for {@link #should(com.codeborne.selenide.Condition...)}. Useful for better readability.</p>
   * <p>For example: {@code
   * $("#errorMessage").shouldBe(visible, enabled);
   * }</p>
   *
   * @see SelenideElement#should(com.codeborne.selenide.Condition...)
   * @see com.codeborne.selenide.commands.ShouldBe
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldBe(Condition... condition);

  /**
   * Wait until given element meets given condition (with given timeout)
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldBe(Condition condition, Duration timeout);

  /**
   * <p>Checks that given element does not meet given conditions.</p>
   *
   * <p>
   * IMPORTANT: If element does match the conditions, waits up to
   * 4 seconds until element does not meet the conditions. It's extremely useful for dynamic content.
   * </p>
   *
   * <p>Timeout is configurable via {@link com.codeborne.selenide.Configuration#timeout}</p>
   *
   * <p>For example: {@code
   * $("#errorMessage").should(exist);
   * }</p>
   *
   * @see com.codeborne.selenide.Config#timeout
   * @see com.codeborne.selenide.commands.ShouldNot
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldNot(Condition... condition);

  /**
   * Wait until given element meets given condition (with given timeout)
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldNot(Condition condition, Duration timeout);

  /**
   * <p>Synonym for {@link #shouldNot(com.codeborne.selenide.Condition...)}. Useful for better readability.</p>
   * <p>For example: {@code
   * $("#errorMessage").shouldNotHave(text("Exception"), text("Error"));
   * }</p>
   *
   * @see SelenideElement#shouldNot(com.codeborne.selenide.Condition...)
   * @see com.codeborne.selenide.commands.ShouldNotHave
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldNotHave(Condition... condition);

  /**
   * Wait until given element does NOT meet given condition (with given timeout)
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldNotHave(Condition condition, Duration timeout);

  /**
   * <p>Synonym for {@link #shouldNot(com.codeborne.selenide.Condition...)}. Useful for better readability.</p>
   * <p>For example: {@code
   * $("#errorMessage").shouldNotBe(visible, enabled);
   * }</p>
   *
   * @see SelenideElement#shouldNot(com.codeborne.selenide.Condition...)
   * @see com.codeborne.selenide.commands.ShouldNotBe
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldNotBe(Condition... condition);

  /**
   * Wait until given element does NOT meet given condition (with given timeout)
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement shouldNotBe(Condition condition, Duration timeout);

  /**
   * Displays WebElement in human-readable format.
   * Useful for logging and debugging.
   * Not recommended to use for test verifications.
   *
   * @return e.g. <strong id=orderConfirmedStatus class=>Order has been confirmed</strong>
   * @see com.codeborne.selenide.commands.ToString
   */
  @Override
  @CheckReturnValue
  @Nonnull
  String toString();

  /**
   * Give this element a human-readable name
   *
   * Caution: you probably don't need this method.
   * It's always a good idea to have the actual selector instead of "nice" description (which might be misleading or even lying).
   *
   * @param alias a human-readable name of this element (null or empty string not allowed)
   * @return this element
   * @since 5.17.0
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement as(String alias);

  /**
   * Get parent element of this element (lazy evaluation)
   *
   * For example, $("td").parent() could give some "tr".
   *
   * @return Parent element
   * @see com.codeborne.selenide.commands.GetParent
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement parent();

  /**
   * Get the following sibling element of this element
   *
   * For example, $("td").sibling(0) will give the first following sibling element of "td"
   *
   * @param index the index of sibling element
   * @return Sibling element by index
   * @see com.codeborne.selenide.commands.GetSibling
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement sibling(int index);

  /**
   * Get the preceding sibling element of this element
   *
   * For example, $("td").preceding(0) will give the first preceding sibling element of "td"
   *
   * @param index the index of sibling element
   * @return Sibling element by index
   * @see com.codeborne.selenide.commands.GetPreceding
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement preceding(int index);

  /**
   * Get last child element of this element
   *
   * For example, $("tr").lastChild(); could give the last "td".
   *
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement lastChild();

  /**
   * Locates the closest ancestor element matching given criteria.
   * <br/>
   * For example, $("td").ancestor("table") returns the closest "table" element above "td".
   * <br/>
   * Same as {@code closest("selector", 0)} or {@code closest("selector")}.
   *
   * Examples:
   * <br>
   * {@code $("td").ancestor("table")} will find the closest ancestor with tag {@code table}
   * <br>
   * {@code $("td").ancestor(".container")} will find the closest ancestor with CSS class {@code .container}
   * <br>
   * {@code $("td").ancestor("[data-testid]")} will find the closest ancestor with attribute {@code data-testid}
   * <br>
   * {@code $("td").ancestor("[data-testid=test-value]")} will find the closest ancestor with attribute and
   * attribute's value {@code data-testid=test-value}
   *<br>
   * @param selector Either HTML tag, CSS class, attribute or attribute with value.<br>
   *                 E.g. {@code form}, {@code .active}, {@code [data-testid]}, {@code [data-testid=test-value]}
   * @return Matching ancestor element
   * @see com.codeborne.selenide.commands.Ancestor
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement ancestor(String selector);

  /**
   * Locates the Nth ancestor element matching given criteria.
   * <br/>
   *
   * Examples:
   * <br>
   * {@code $("td").ancestor("table", 1)} will find the 2nd ancestor with tag {@code table}
   * <br>
   * {@code $("td").ancestor(".container", 1)} will find the 2nd ancestor with CSS class {@code .container}
   * <br>
   * {@code $("td").ancestor("[data-testid]", 1)} will find the 2nd ancestor with attribute {@code data-testid}
   * <br>
   * {@code $("td").ancestor("[data-testid=test-value]", 1)} will find the 2nd ancestor with attribute and
   * attribute's value {@code data-testid=test-value}
   *<br>
   * @param selector Either HTML tag, CSS class, attribute or attribute with value.<br>
   *                 E.g. {@code form}, {@code .active}, {@code [data-testid]}, {@code [data-testid=test-value]}
   * @param index    0...N index of the ancestor. 0 is the closest, 1 is higher up the hierarchy, etc...
   * @return Matching ancestor element
   * @see com.codeborne.selenide.commands.Ancestor
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement ancestor(String selector, int index);

  /**
   * Same as {@link #ancestor(String)}.
   *
   * Locates the closest ancestor element matching given criteria.
   * <br/>
   * For example, $("td").closest("table") returns the closest "table" element above "td".
   * <br/>
   * Same as {@code ancestor("selector", 0)}.
   *
   * Examples:
   * <br>
   * {@code $("td").closest("table")} will find the closest ancestor with tag {@code table}
   * <br>
   * {@code $("td").closest(".container")} will find the closest ancestor with CSS class {@code .container}
   * <br>
   * {@code $("td").closest("[data-testid]")} will find the closest ancestor with attribute {@code data-testid}
   * <br>
   * {@code $("td").closest("[data-testid=test-value]")} will find the closest ancestor with attribute and
   * attribute's value {@code data-testid=test-value}
   *<br>
   * @param selector Either HTML tag, CSS class, attribute or attribute with value.<br>
   *                 E.g. {@code form}, {@code .active}, {@code [data-testid]}, {@code [data-testid=test-value]}
   * @return Matching ancestor element
   * @see com.codeborne.selenide.commands.Ancestor
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement closest(String selector);

  /**
   * <p>Locates the first matching element inside given element</p>
   *
   * <p>Short form of {@code webElement.findElement(By.cssSelector(cssSelector))}</p>
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement find(String cssSelector);

  /**
   * <p>Locates the Nth matching element inside given element</p>
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement find(String cssSelector, int index);

  /**
   * Same as {@link #find(String)}
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement find(By selector);

  /**
   * Same as {@link #find(String, int)}
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement find(By selector, int index);

  /**
   * Same as {@link #find(String)}
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement $(String cssSelector);

  /**
   * Same as {@link #find(String, int)}
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement $(String cssSelector, int index);

  /**
   * Same as {@link #find(String)}
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement $(By selector);

  /**
   * Same as {@link #find(String, int)}
   *
   * @see com.codeborne.selenide.commands.Find
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement $(By selector, int index);

  /**
   * <p>Locates the first matching element inside given element using xpath locator</p>
   *
   * <p>Short form of {@code webElement.findElement(By.xpath(xpathLocator))}</p>
   *
   * @see com.codeborne.selenide.commands.FindByXpath
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement $x(String xpath);

  /**
   * <p>Locates the Nth matching element inside given element using xpath locator</p>
   *
   * @see com.codeborne.selenide.commands.FindByXpath
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement $x(String xpath, int index);

  /**
   * <p>
   * Short form of {@code webDriver.findElements(thisElement, By.cssSelector(cssSelector))}
   * </p>
   *
   * <p>
   * For example, {@code $("#multirowTable").findAll("tr.active").shouldHave(size(2));}
   * </p>
   *
   * @return list of elements inside given element matching given CSS selector
   * @see com.codeborne.selenide.commands.FindAll
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  ElementsCollection findAll(String cssSelector);

  /**
   * <p>
   * Short form of {@code webDriver.findElements(thisElement, selector)}
   * </p>
   *
   * <p>
   * For example, {@code $("#multirowTable").findAll(By.className("active")).shouldHave(size(2));}
   * </p>
   *
   * @return list of elements inside given element matching given criteria
   * @see com.codeborne.selenide.commands.FindAll
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  ElementsCollection findAll(By selector);

  /**
   * Same as {@link #findAll(String)}
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  ElementsCollection $$(String cssSelector);

  /**
   * Same as {@link #findAll(By)}
   */
  @CheckReturnValue
  @Nonnull
  ElementsCollection $$(By selector);

  /**
   * <p>
   * Short form of {@code webDriver.findElements(thisElement, By.xpath(xpath))}
   * </p>
   *
   * <p>
   * For example, {@code $("#multirowTable").$$x("./input").shouldHave(size(2));}
   * </p>
   *
   * @return list of elements inside given element matching given xpath locator
   * @see com.codeborne.selenide.commands.FindAllByXpath
   * @see <a href="https://github.com/selenide/selenide/wiki/lazy-loading">Lazy loading</a>
   */
  @CheckReturnValue
  @Nonnull
  ElementsCollection $$x(String xpath);

  /**
   * <p>Upload file into file upload field. File is searched from classpath.</p>
   *
   * <p>Multiple file upload is also supported. Just pass as many file names as you wish.</p>
   *
   * @param fileName name of the file or the relative path in classpath e.g. "files/1.pfd"
   * @return the object of the first file uploaded
   * @throws IllegalArgumentException if any of the files is not found
   * @see com.codeborne.selenide.commands.UploadFileFromClasspath
   */
  @Nonnull
  @CanIgnoreReturnValue
  File uploadFromClasspath(String... fileName);

  /**
   * <p>Upload file into file upload field.</p>
   *
   * <p>Multiple file upload is also supported. Just pass as many files as you wish.</p>
   *
   * @param file file object(s)
   * @return the object of the first file uploaded
   * @throws IllegalArgumentException if any of the files is not found, or other errors
   * @see com.codeborne.selenide.commands.UploadFile
   */
  @Nonnull
  @CanIgnoreReturnValue
  File uploadFile(File... file);

  /**
   * Select an option from dropdown list (by index)
   *
   * @param index 0..N (0 means first option)
   * @see com.codeborne.selenide.commands.SelectOptionByTextOrIndex
   */
  void selectOption(int... index);

  /**
   * Select an option from dropdown list (by text)
   *
   * @param text visible text of option
   * @see com.codeborne.selenide.commands.SelectOptionByTextOrIndex
   */
  void selectOption(String... text);

  /**
   * Select an option from dropdown list that contains given text
   *
   * @param text substring of visible text of option
   * @see com.codeborne.selenide.commands.SelectOptionContainingText
   */
  void selectOptionContainingText(String text);

  /**
   * Select an option from dropdown list (by value)
   *
   * @param value "value" attribute of option
   * @see com.codeborne.selenide.commands.SelectOptionByValue
   */
  void selectOptionByValue(String... value);

  /**
   * Find (first) selected option from this select field
   *
   * @return WebElement for selected &lt;option&gt; element
   * @throws NoSuchElementException if no options are selected
   * @see com.codeborne.selenide.commands.GetSelectedOption
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  SelenideElement getSelectedOption() throws NoSuchElementException;

  /**
   * Find all selected options from this select field
   *
   * @return ElementsCollection for selected &lt;option&gt; elements (empty list if no options are selected)
   * @see com.codeborne.selenide.commands.GetSelectedOptions
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  ElementsCollection getSelectedOptions();

  /**
   * Get value of selected option in select field
   *
   * @see com.codeborne.selenide.commands.GetSelectedValue
   * @return null if the selected option doesn't have "value" attribute
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nullable
  String getSelectedValue();

  /**
   * Get text of selected option in select field
   *
   * @see com.codeborne.selenide.commands.GetSelectedText
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  @CheckReturnValue
  @Nonnull
  String getSelectedText();

  /**
   * Ask browser to scroll to this element
   *
   * @see com.codeborne.selenide.commands.ScrollTo
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement scrollTo();

  /**
   * Ask browser to scrolls the element on which it's called into the visible area of the browser window.
   * <p>
   * If <b>alignToTop</b> boolean value is <i>true</i> - the top of the element will be aligned to the top.
   * <p>
   * If <b>alignToTop</b> boolean value is <i>false</i> - the bottom of the element will be aligned to the bottom.
   * Usage:
   * <pre>
   *     element.scrollIntoView(true);
   *     // Corresponds to scrollIntoViewOptions: {block: "start", inline: "nearest"}
   *
   *     element.scrollIntoView(false);
   *     // Corresponds to scrollIntoViewOptions: {block: "end", inline: "nearest"}
   * </pre>
   *
   * @param alignToTop boolean value that indicate how element will be aligned to the visible area of the scrollable ancestor.
   * @see com.codeborne.selenide.commands.ScrollIntoView
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/Element/scrollIntoView">Web API reference</a>
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement scrollIntoView(boolean alignToTop);

  /**
   * Ask browser to scrolls the element on which it's called into the visible area of the browser window.
   * <pre>
   * scrollIntoViewOptions:
   *  * behavior (optional) - Defines the transition animation
   *    1. auto (default)
   *    2. instant
   *    3. smooth
   *  * block (optional)
   *    1. start
   *    2. center (default)
   *    3. end
   *    4. nearest
   *  * inline
   *    1. start
   *    2. center
   *    3. end
   *    4. nearest (default)
   * </pre>
   * <p>
   * Usage:
   * <pre>
   *     element.scrollIntoView("{block: \"end\"}");
   *     element.scrollIntoView("{behavior: \"instant\", block: \"end\", inline: \"nearest\"}");
   * </pre>
   *
   * @param scrollIntoViewOptions is an object with the align properties: behavior, block and inline.
   * @see com.codeborne.selenide.commands.ScrollIntoView
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/Element/scrollIntoView">Web API reference</a>
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement scrollIntoView(String scrollIntoViewOptions);

  /**
   * Download file by clicking this element. Algorithm depends on {@code @{@link Config#fileDownload() }}.
   *
   * @throws RuntimeException      if 50x status code was returned from server
   * @throws FileNotFoundException if 40x status code was returned from server
   * @see FileDownloadMode
   * @see com.codeborne.selenide.commands.DownloadFile
   */
  @CheckReturnValue
  @Nonnull
  File download() throws FileNotFoundException;

  /**
   * Download file by clicking this element. Algorithm depends on {@code @{@link Config#fileDownload() }}.
   *
   * @param timeout download operations timeout.
   * @throws RuntimeException      if 50x status code was returned from server
   * @throws FileNotFoundException if 40x status code was returned from server
   * @see com.codeborne.selenide.commands.DownloadFile
   */
  @CheckReturnValue
  @Nonnull
  File download(long timeout) throws FileNotFoundException;

  /**
   * Download file by clicking this element. Algorithm depends on {@code @{@link Config#fileDownload() }}.
   *
   * @param fileFilter Criteria for defining which file is expected (
   *                   {@link com.codeborne.selenide.files.FileFilters#withName(String)},
   *                   {@link com.codeborne.selenide.files.FileFilters#withNameMatching(String)},
   *                   {@link com.codeborne.selenide.files.FileFilters#withName(String)}
   *                   ).
   * @throws RuntimeException      if 50x status code was returned from server
   * @throws FileNotFoundException if 40x status code was returned from server, or the downloaded file didn't match given filter.
   * @see com.codeborne.selenide.files.FileFilters
   * @see com.codeborne.selenide.commands.DownloadFile
   */
  @CheckReturnValue
  @Nonnull
  File download(FileFilter fileFilter) throws FileNotFoundException;

  /**
   * Download file by clicking this element. Algorithm depends on {@code @{@link Config#fileDownload() }}.
   *
   * @param timeout    download operations timeout.
   * @param fileFilter Criteria for defining which file is expected (
   *                   {@link com.codeborne.selenide.files.FileFilters#withName(String)},
   *                   {@link com.codeborne.selenide.files.FileFilters#withNameMatching(String)},
   *                   {@link com.codeborne.selenide.files.FileFilters#withName(String)}
   *                   ).
   * @throws RuntimeException      if 50x status code was returned from server
   * @throws FileNotFoundException if 40x status code was returned from server, or the downloaded file didn't match given filter.
   * @see com.codeborne.selenide.files.FileFilters
   * @see com.codeborne.selenide.commands.DownloadFile
   */
  @CheckReturnValue
  @Nonnull
  File download(long timeout, FileFilter fileFilter) throws FileNotFoundException;

  @CheckReturnValue
  @Nonnull
  File download(DownloadOptions options) throws FileNotFoundException;

  /**
   * Return criteria by which this element is located
   *
   * @return e.g. "#multirowTable.findBy(text 'INVALID-TEXT')/valid-selector"
   * @see com.codeborne.selenide.commands.GetSearchCriteria
   */
  @CheckReturnValue
  @Nonnull
  String getSearchCriteria();

  /**
   * @return the original Selenium {@link WebElement} wrapped by this object
   * @throws org.openqa.selenium.NoSuchElementException if element does not exist (without waiting for the element)
   * @see com.codeborne.selenide.commands.ToWebElement
   */
  @CheckReturnValue
  @Nonnull
  WebElement toWebElement();

  /**
   * @return Underlying {@link WebElement}
   * @throws com.codeborne.selenide.ex.ElementNotFound if element does not exist (after waiting for N seconds)
   * @see com.codeborne.selenide.commands.GetWrappedElement
   */
  @Override
  @CheckReturnValue
  @Nonnull
  WebElement getWrappedElement();

  /**
   * Click the element using {@link ClickOptions}: {@code $("#username").click(ClickOptions.usingJavaScript())}
   *
   * <p>
   * You can specify a relative offset from the center of the element inside ClickOptions:
   * e.g. {@code $("#username").click(usingJavaScript().offset(123, 222))}
   * </p>
   *
   * @see com.codeborne.selenide.commands.Click
   */
  void click(ClickOptions clickOption);

  /**
   * Click the element
   *
   * <p>
   * By default it uses default Selenium method click.
   * </p>
   * <p>
   * But it uses JavaScript method to click if {@code com.codeborne.selenide.Configuration#clickViaJs} is defined.
   * It may be helpful for testing in Internet Explorer where native click doesn't always work correctly.
   * </p>
   *
   * @see com.codeborne.selenide.commands.Click
   */
  @Override
  void click();

  /**
   * Click with right mouse button on this element
   *
   * @return this element
   * @see com.codeborne.selenide.commands.ContextClick
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement contextClick();

  /**
   * Double click the element
   *
   * @return this element
   * @see com.codeborne.selenide.commands.DoubleClick
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement doubleClick();

  /**
   * Emulate "mouseOver" event. In other words, move mouse cursor over this element (without clicking it).
   *
   * @return this element
   * @see com.codeborne.selenide.commands.Hover
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement hover();

  /**
   * Emulate "mouseOver" event. In other words, move mouse cursor over this element (without clicking it).
   *
   * @param options optional hover parameters (offset etc)
   * @return this element
   * @see com.codeborne.selenide.commands.Hover
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement hover(HoverOptions options);

  /**
   * Drag and drop this element to the target
   * <p>
   * Before dropping, waits until target element gets visible.
   *
   * @param targetCssSelector CSS selector defining target element
   * @return this element
   * @see com.codeborne.selenide.commands.DragAndDropTo
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement dragAndDropTo(String targetCssSelector);

  /**
   * Drag and drop this element to the target
   * <p>
   * Before dropping, waits until target element gets visible.
   *
   * @param target target element
   * @return this element
   * @see com.codeborne.selenide.commands.DragAndDropTo
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement dragAndDropTo(WebElement target);

  /**
   Drag and drop this element to the target via JS script
   * see resources/drag_and_drop_script
   *
   * <p>
   * Before dropping, waits until target element gets visible.
   *
   * @param targetCssSelector target css selector
   * @param options drag and drop options to define which way it will be executed
   *
   * @return this element
   * @see com.codeborne.selenide.commands.DragAndDropTo
   */
  @Nonnull
  @CanIgnoreReturnValue
  SelenideElement dragAndDropTo(String targetCssSelector, DragAndDropOptions options);

  /**
   * Execute custom implemented command (this command will not receive
   * any arguments through {@link Command#execute(SelenideElement, WebElementSource, Object[])}
   * when executed).
   *
   * @param command custom command
   * @return whatever the command returns (incl. null)
   * @see com.codeborne.selenide.commands.Execute
   * @see com.codeborne.selenide.Command
   */
  <ReturnType> ReturnType execute(Command<ReturnType> command);

  /**
   * Execute custom implemented command with given timeout (this command will not receive
   * any arguments through {@link Command#execute(SelenideElement, WebElementSource, Object[])}
   * when executed).
   *
   * @param command custom command
   * @param timeout given timeout
   * @return whatever the command returns (incl. null)
   * @see com.codeborne.selenide.commands.Execute
   * @see com.codeborne.selenide.Command
   * @since 5.24.0
   */
  <ReturnType> ReturnType execute(Command<ReturnType> command, Duration timeout);

  /**
   * Check if image is properly loaded.
   *
   * @throws IllegalArgumentException if argument is not an "img" element
   * @see com.codeborne.selenide.commands.IsImage
   * @since 2.13
   * @see <a href="https://github.com/selenide/selenide/wiki/do-not-use-getters-in-tests">NOT RECOMMENDED</a>
   */
  boolean isImage();

  /**
   * Take screenshot of this element
   *
   * @return file with screenshot (*.png)
   * or null if Selenide failed to take a screenshot (due to some technical problem)
   * @see com.codeborne.selenide.commands.TakeScreenshot
   */
  @CheckReturnValue
  @Nullable
  File screenshot();

  /**
   * Take screenshot of this element
   *
   * @return buffered image with screenshot
   * @see com.codeborne.selenide.commands.TakeScreenshotAsImage
   */
  @CheckReturnValue
  @Nullable
  BufferedImage screenshotAsImage();
}
