package com.example.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class LoginUiTest {

    WebDriver driver;
    String baseUrl;

    @BeforeEach
    void setup() throws Exception {
        baseUrl = System.getProperty("baseUrl", "http://app:8080");
        String seleniumUrl = System.getProperty("seleniumUrl", "http://selenium:4444/wd/hub");

        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(seleniumUrl), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    private WebDriverWait wait10() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void successfulLogin_redirectsToDashboard() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.cssSelector("[data-testid='username']")).sendKeys("qa");
        driver.findElement(By.cssSelector("[data-testid='password']")).sendKeys("secret");
        driver.findElement(By.cssSelector("[data-testid='login-btn']")).click();

        wait10().until(d -> d.getCurrentUrl().contains("/dashboard"));
        wait10().until(d -> d.findElement(By.cssSelector("[data-testid='dashboard-title']")).isDisplayed());
    }

    @Test
    void wrongPassword_showsError() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.cssSelector("[data-testid='username']")).sendKeys("qa");
        driver.findElement(By.cssSelector("[data-testid='password']")).sendKeys("wrong");
        driver.findElement(By.cssSelector("[data-testid='login-btn']")).click();

        wait10().until(d -> d.getCurrentUrl().contains("/login"));
        wait10().until(d -> d.findElement(By.cssSelector("[data-testid='login-error']")).isDisplayed());
    }

    @Test
    void logout_returnsToHome() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.cssSelector("[data-testid='username']")).sendKeys("qa");
        driver.findElement(By.cssSelector("[data-testid='password']")).sendKeys("secret");
        driver.findElement(By.cssSelector("[data-testid='login-btn']")).click();

        wait10().until(d -> d.getCurrentUrl().contains("/dashboard"));
        wait10().until(d -> d.findElement(By.cssSelector("[data-testid='logout-btn']")).isDisplayed());

        driver.findElement(By.cssSelector("[data-testid='logout-btn']")).click();

        driver.get(baseUrl + "/");
        wait10().until(d -> d.findElement(By.id("welcome-message")).isDisplayed());
        wait10().until(d -> d.findElement(By.id("build-text")).isDisplayed());
    }
}
