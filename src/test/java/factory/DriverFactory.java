package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browser) {

        try {

            System.out.println("========= GRID EXECUTION =========");

            // ✅ FORCE GRID ONLY
            if (!browser.equalsIgnoreCase("grid-chrome")) {
                throw new RuntimeException("❌ Only GRID execution allowed. Use: -Dbrowser=grid-chrome");
            }

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            driver.set(new RemoteWebDriver(
                    new URL("http://localhost:4444"),
                    options
            ));

            System.out.println("✅ Running on Selenium Grid");

            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Driver initialization failed!");
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}