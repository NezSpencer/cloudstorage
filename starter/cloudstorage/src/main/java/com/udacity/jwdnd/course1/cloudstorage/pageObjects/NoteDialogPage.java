package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NoteDialogPage {

    @FindBy(xpath = "//*[@id=\"note-title\"]")
    WebElement noteTitleField;

    @FindBy(xpath = "//*[@id=\"note-description\"]")
    WebElement noteDescriptionField;

    @FindBy(xpath = "//*[@id=\"noteModal\"]/div/div/div[3]/button[2]")
    WebElement noteSubmitButton;

    @FindBy(id = "addNewNote")
    WebElement addNewNoteButton;

    @FindBy(id = "nav-notes-tab")
    WebElement notesTab;

    @FindBy(id = "noteItemTitle")
    WebElement noteItemTitle;

    @FindBy(id = "noteItemDesc")
    WebElement noteItemDescriptionField;

    @FindBy(id = "editNote")
    WebElement editNoteBtn;

    @FindBy(id = "deleteNoteBtn")
    WebElement deleteNoteButton;

    private JavascriptExecutor jse;
    private WebDriver driver;

    public NoteDialogPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        driver = webDriver;
        jse = (JavascriptExecutor) driver;
    }

    public void createNote(String noteTitle, String noteDescription) {
        jse.executeScript("arguments[0].click()", notesTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addNewNoteButton)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteTitleField)).sendKeys(noteTitle);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescriptionField)).sendKeys(noteDescription);
        noteSubmitButton.click();
    }

    public String getItemTitleText() {
        jse.executeScript("arguments[0].click()", notesTab);
        return new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteItemTitle)).getText();
    }

    public String getItemDescriptionText() {
        jse.executeScript("arguments[0].click()", notesTab);
        return new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteItemDescriptionField)).getText();
    }

    public boolean isNoteDeleted() throws InterruptedException {
        try {
            Thread.sleep(2000);
            return !noteTitleField.isDisplayed();
        } catch (NoSuchElementException ex) {
            return true;
        }
    }

    public void deleteNote() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
    }

    public void editNote(String noteTitle, String noteDescription){
        jse.executeScript("arguments[0].click()", notesTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editNoteBtn)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteTitleField)).clear();
        noteTitleField.sendKeys(noteTitle);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescriptionField)).clear();
        noteDescriptionField.sendKeys(noteDescription);
        noteSubmitButton.click();
    }
}
