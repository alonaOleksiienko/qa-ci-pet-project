package com.example.ui;

import java.net.URL;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HomePageTest {

    private WebDriver driver;

    @Test
    void shouldShowBuildText() throws Exception {

        String gridUrl = System.getenv().getOrDefault(
                "SELENIUM_REMOTE_URL",
                "http://localhost:4444"
        );

        String appUrl = System.getenv().getOrDefault(
                "BASE_URL",
                "http://localhost:8080"
        );

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");

        driver = new RemoteWebDriver(new URL(gridUrl), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get(appUrl);

        String text = driver.findElement(By.id("build-text")).getText();
        Assertions.assertTrue(text.contains("Build"), "Expected 'Build' text but got: " + text);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
