package pages;

import factory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage() {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By username = By.id("username");
    private By password = By.id("password");
    private By loginBtn = By.cssSelector("button[type='submit']");
    private By message = By.id("flash");
    private By logoutBtn = By.cssSelector("a.button.secondary.radius");

    public void openLoginPage() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    public void enterUsername(String user) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(username));
        userField.clear();
        userField.sendKeys(user);
    }

    public void enterPassword(String pass) {
        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(password));
        passField.clear();
        passField.sendKeys(pass);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    public String getLoginMessage() {
        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(message));
            return msg.getText();
        } catch (Exception e) {
            return "NO_MESSAGE_FOUND";
        }
    }

    public void logoutIfLoggedIn() {
        try {
            WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutBtn));
            logout.click();
        } catch (Exception e) {
            // ignore
        }
    }
}