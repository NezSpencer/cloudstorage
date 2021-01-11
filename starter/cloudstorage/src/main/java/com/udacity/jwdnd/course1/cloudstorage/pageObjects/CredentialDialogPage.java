package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialDialogPage {

    @FindBy(id = "nav-credentials-tab")
    WebElement credentialsTab;

    @FindBy(id = "addNewCredential")
    WebElement addNewCredentialsButton;

    @FindBy(id = "deleteCredentialBtn")
    WebElement deleteCredentialButton;

    @FindBy(id = "editCredential")
    WebElement editCredentialButton;

    @FindBy(id = "credUrl")
    WebElement urlItemField;

    @FindBy(id = "credUsername")
    WebElement usernameItemField;

    @FindBy(id = "credPwd")
    WebElement passwordItemField;

    @FindBy(id = "credential-url")
    WebElement urlInputField;

    @FindBy(id = "credential-username")
    WebElement usernameInputField;

    @FindBy(id = "credential-password")
    WebElement passwordInputField;

    @FindBy(id = "saveCredential")
    WebElement saveCredentialBtn;

    private WebDriver webDriver;
    private JavascriptExecutor javascriptExecutor;

    public CredentialDialogPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
        javascriptExecutor = (JavascriptExecutor)webDriver;
    }

    public void saveCredentials(String username, String url, String password){
        javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.elementToBeClickable(addNewCredentialsButton)).click();
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(usernameInputField)).sendKeys(username);
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(urlInputField)).sendKeys(url);
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(passwordInputField)).sendKeys(password);
        saveCredentialBtn.click();
    }

    public String getItemUsername(){
        javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
        return new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(usernameItemField)).getText();
    }

    public String getItemUrl(){
        javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
        return new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(urlItemField)).getText();
    }

    public String getItemPassword(){
        javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
        return new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(passwordItemField)).getText();
    }

    public void editUsernameAndUrl(String username, String url){
        javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(usernameInputField)).clear();
        usernameInputField.sendKeys(username);
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(urlInputField)).clear();
        urlInputField.sendKeys(url);
        saveCredentialBtn.click();
    }

    public void deleteCredential(){
        javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(deleteCredentialButton)).click();
    }

    public String getDisplayedPassword() throws InterruptedException{
        javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        return new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(passwordInputField)).getAttribute("value");
    }

    public boolean isCredentialDeleted() throws InterruptedException {
        try {
            Thread.sleep(1000);
            javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
            Thread.sleep(1000);
            return !usernameItemField.isDisplayed();
        } catch (NoSuchElementException ex) {
            return true;
        }
    }
}
