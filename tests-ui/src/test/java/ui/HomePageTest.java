package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomePageTest {

    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new"); // enable later if you want
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void shouldShowBuildText() {
        String appUrl = System.getProperty("appUrl", "http://localhost:8080");
        driver.get(appUrl);

        String text = driver.findElement(By.id("build-text")).getText();
        Assert.assertTrue(text.contains("Build"), "Expected Build text, got: " + text);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        if (driver != null) driver.quit();
    }
}

