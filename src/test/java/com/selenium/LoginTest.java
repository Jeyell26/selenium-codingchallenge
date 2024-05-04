package com.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
    WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.operadriver().setup();
        OperaOptions options = new OperaOptions();
        options.setBinary("C:\\Users\\Jeyell\\AppData\\Local\\Programs\\Opera\\opera.exe");
        driver = new OperaDriver(options);
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testLoginWithStandardUser() {
        WebDriverWait wait = new WebDriverWait(driver, 10); // wait up to 10 seconds

        // Log in
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify navigation to home page
        Assert.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());

        // Log out
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();

        // Verify navigation back to login page
        Assert.assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
    }

    @Test
    public void testLoginWithLockedOutUser() {
        // Log in
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify error message
        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertEquals("Epic sadface: Sorry, this user has been locked out.", errorMessage.getText());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}