package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logoutButton")
    WebElement logoutButton;

    @FindBy(xpath = "//*[@id=\"addNote\"]")
    WebElement addNoteButton;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logOut(){
        logoutButton.click();
    }

    public void clickAddNote(){ addNoteButton.click();}
}
