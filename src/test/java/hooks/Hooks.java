package hooks;

import factory.DriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {

        // ✅ FORCE GRID ONLY (default = grid-chrome)
        String browser = System.getProperty("browser", "grid-chrome");

        System.out.println("\n========================================");
        System.out.println("🚀 Starting Scenario: " + scenario.getName());
        System.out.println("🌐 Execution Mode: " + browser);
        System.out.println("========================================");

        DriverFactory.initDriver(browser);
    }

    @After
    public void tearDown(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();

        try {

            if (scenario.isFailed()) {

                System.out.println("❌ Scenario Failed: " + scenario.getName());

                // ✅ Screenshot only if driver exists
                if (driver != null) {
                    byte[] screenshot = ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

                    scenario.attach(screenshot, "image/png", scenario.getName());
                }

            } else {
                System.out.println("✅ Scenario Passed: " + scenario.getName());
            }

        } catch (Exception e) {
            System.out.println("⚠️ Screenshot capture failed");
            e.printStackTrace();
        }

        finally {

            // ✅ Always close driver
            DriverFactory.quitDriver();

            System.out.println("🧹 Browser closed");
            System.out.println("========================================\n");
        }
    }
}