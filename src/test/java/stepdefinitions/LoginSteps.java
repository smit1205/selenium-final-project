package stepdefinitions;

import pages.LoginPage;
import utils.ExcelReader;

import io.cucumber.java.en.*;
import org.junit.Assert;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    String path = System.getProperty("user.dir") + "/testdata/LoginData.xlsx";

    @Given("user launches login page")
    public void user_launches_login_page() {
        loginPage.openLoginPage();
    }

    @When("user enters credentials from excel row {int}")
    public void user_enters_credentials(int rowNum) {

        String username = ExcelReader.getCellData(path, rowNum, 1);
        String password = ExcelReader.getCellData(path, rowNum, 2);
        String expected = ExcelReader.getCellData(path, rowNum, 3); // PASS / FAIL

        System.out.println("========== Running Row: " + rowNum + " ==========");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Expected: " + expected);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        String msg = loginPage.getLoginMessage();
        System.out.println("Actual Message: " + msg);

        // ✅ CORRECT TEST VALIDATION
        if (expected.equalsIgnoreCase("PASS")) {

            Assert.assertTrue("Expected SUCCESS but login FAILED at row: " + rowNum,
                    msg.contains("You logged into a secure area!"));

            loginPage.logoutIfLoggedIn();

        } else if (expected.equalsIgnoreCase("FAIL")) {

            Assert.assertTrue("Expected FAILURE but login PASSED at row: " + rowNum,
                    msg.contains("Your username is invalid!") ||
                            msg.contains("Your password is invalid!"));

        } else {
            Assert.fail("Invalid expected value in Excel at row: " + rowNum);
        }
    }

    @Then("user should login successfully")
    public void verify_login() {
        System.out.println("All test cases executed");
    }
}