package com.example.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void successfulLogin_redirectsToDashboard() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.cssSelector("[data-testid='username']")).sendKeys("qa");
        driver.findElement(By.cssSelector("[data-testid='password']")).sendKeys("secret");
        driver.findElement(By.cssSelector("[data-testid='login-btn']")).click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("/dashboard"));
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-testid='dashboard-title']")).isDisplayed());
    }

    @Test
    void wrongPassword_showsError() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.cssSelector("[data-testid='username']")).sendKeys("qa");
        driver.findElement(By.cssSelector("[data-testid='password']")).sendKeys("wrong");
        driver.findElement(By.cssSelector("[data-testid='login-btn']")).click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-testid='login-error']")).isDisplayed());
    }

    @Test
    void logout_returnsToHome() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.cssSelector("[data-testid='username']")).sendKeys("qa");
        driver.findElement(By.cssSelector("[data-testid='password']")).sendKeys("secret");
        driver.findElement(By.cssSelector("[data-testid='login-btn']")).click();

        driver.findElement(By.cssSelector("[data-testid='logout-btn']")).click();

        Assertions.assertTrue(
            driver.getCurrentUrl().equals(baseUrl + "/") ||
            driver.getCurrentUrl().equals(baseUrl)
        );
        Assertions.assertTrue(driver.findElement(By.cssSelector("#welcome-message")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector("#build-text")).isDisplayed());
    }
}
