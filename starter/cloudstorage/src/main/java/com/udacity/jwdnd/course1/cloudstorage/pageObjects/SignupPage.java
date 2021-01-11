package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    WebElement firstNameField;

    @FindBy(id = "inputLastName")
    WebElement lastNameField;

    @FindBy(id = "inputUsername")
    WebElement usernameField;

    @FindBy(id = "inputPassword")
    WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"login-link\"]")
    WebElement loginLink;

    @FindBy(id = "submit-button")
    WebElement submitButton;

    private WebDriver webDriver;
    private JavascriptExecutor javascriptExecutor;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
        javascriptExecutor = (JavascriptExecutor) webDriver;
    }

    public void inputDetails(String firstName, String lastname, String username, String password) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastname);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
    }

    public void moveToLogin(){
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(loginLink)).click();
    }

    public void signup(){
        submitButton.click();
    }
}
