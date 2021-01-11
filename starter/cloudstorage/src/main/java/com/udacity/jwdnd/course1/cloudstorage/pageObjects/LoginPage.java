package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    WebElement usernameField;

    @FindBy(id = "inputPassword")
    WebElement passwordField;

    @FindBy(css = "body > div > form > button")
    WebElement loginButton;

    @FindBy(id = "error-div")
    WebElement errorDiv;

    @FindBy(id = "signup-success")
    WebElement signupSuccessDiv;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
