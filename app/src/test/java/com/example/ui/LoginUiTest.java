package com.example.ui;

import java.net.URL;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginUiTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    private static final By USERNAME = By.cssSelector("[data-testid='username']");
    private static final By PASSWORD = By.cssSelector("[data-testid='password']");
    private static final By LOGIN_BTN = By.cssSelector("[data-testid='login-btn']");
    private static final By LOGIN_ERROR = By.cssSelector("[data-testid='login-error']");
    private static final By DASHBOARD_TITLE = By.cssSelector("[data-testid='dashboard-title']");
    private static final By LOGOUT_BTN = By.cssSelector("[data-testid='logout-btn']");
    private static final By BUILD_TEXT = By.id("build-text");

    @BeforeEach
    void setup() throws Exception {
        baseUrl = System.getProperty("baseUrl", "http://app:8080");
        String seleniumUrl = System.getProperty("seleniumUrl", "http://selenium:4444/wd/hub");

        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(seleniumUrl), options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (driver != null) driver.quit();
    }

    private void waitForDocumentReady() {
        try {
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {}
    }

    private void openLoginPage() {
        driver.get(baseUrl + "/login");
        waitForDocumentReady();
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
    }

    private void loginAs(String user, String pass) {
        openLoginPage();
        driver.findElement(USERNAME).sendKeys(user);
        driver.findElement(PASSWORD).sendKeys(pass);
        driver.findElement(LOGIN_BTN).click();
    }

    @Test
    void successfulLogin_redirectsToDashboard() {
        loginAs("qa", "secret");

        wait.until(ExpectedConditions.urlContains("/dashboard"));
        waitForDocumentReady();
        wait.until(ExpectedConditions.visibilityOfElementLocated(DASHBOARD_TITLE));
    }

    @Test
    void wrongPassword_showsError() {
        loginAs("qa", "wrong");

        wait.until(ExpectedConditions.urlContains("/login"));
        waitForDocumentReady();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_ERROR));
    }

    @Test
    void logout_returnsToHome() {
        loginAs("qa", "secret");

        wait.until(ExpectedConditions.urlContains("/dashboard"));
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BTN)).click();

        driver.get(baseUrl + "/");
        waitForDocumentReady();

        // Stable home page anchor that MUST exist (used by HomePageTest)
        wait.until(ExpectedConditions.visibilityOfElementLocated(BUILD_TEXT));
    }
}
