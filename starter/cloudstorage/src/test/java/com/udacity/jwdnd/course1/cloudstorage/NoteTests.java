package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.NoteDialogPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private String baseUrl;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        baseUrl = "http://localhost:"+port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void createNoteAndDisplayNote() throws InterruptedException{
        driver.get(baseUrl+"/signup");
        SignupPage signupPage = new SignupPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        String firstname = "John";
        String lastname = "Doe";
        String username = "johnedoe@gmail.com";
        String password = "1234";
        signupPage.inputDetails(firstname,lastname,username,password);
        signupPage.signup();
        Thread.sleep(2000);


        loginPage.login(username, password);

        NoteDialogPage noteDialogPage = new NoteDialogPage(driver);
        assertEquals("Home", driver.getTitle());
        String noteTitle = "test note";
        String noteDescription = "test note description";
        noteDialogPage.createNote(noteTitle, noteDescription);
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("goHome"))).click();
        Thread.sleep(2000);
        String savedNoteTitle = noteDialogPage.getItemTitleText();
        String saveNoteDescription = noteDialogPage.getItemDescriptionText();
        assertEquals(savedNoteTitle, noteTitle);
        assertEquals(saveNoteDescription, noteDescription);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void verifyNoteCanBeEdited() throws InterruptedException {
        driver.get(baseUrl+"/signup");
        SignupPage signupPage = new SignupPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        String firstname = "John";
        String lastname = "Doe";
        String username = "johnedoe@gmail.com";
        String password = "1234";
        signupPage.inputDetails(firstname,lastname,username,password);
        signupPage.signup();
        Thread.sleep(2000);

        loginPage.login(username, password);

        NoteDialogPage noteDialogPage = new NoteDialogPage(driver);
        assertEquals("Home", driver.getTitle());
        String noteTitle = "test note";
        String noteDescription = "test note description";
        Thread.sleep(2000);
        noteDialogPage.createNote(noteTitle, noteDescription);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("here"))).click();
        Thread.sleep(2000);
        String savedNoteTitle = noteDialogPage.getItemTitleText();
        String saveNoteDescription = noteDialogPage.getItemDescriptionText();
        assertEquals(savedNoteTitle, noteTitle);
        assertEquals(saveNoteDescription, noteDescription);

        String newTitle = "ToDo note";
        String newDescription = "this is an edited note";
        noteDialogPage.editNote(newTitle, newDescription);
        Thread.sleep(2000);
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.id("goHome"))).click();
        Thread.sleep(2000);
        String editedTitle = noteDialogPage.getItemTitleText();
        String editedDescription = noteDialogPage.getItemDescriptionText();
        assertEquals(newTitle, editedTitle);
        assertEquals(newDescription, editedDescription);

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void verifyDeletedNoteIsNotShownOnNoteList() throws InterruptedException {
        driver.get(baseUrl+"/signup");
        SignupPage signupPage = new SignupPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        String firstname = "John";
        String lastname = "Doe";
        String username = "johnedoe@gmail.com";
        String password = "1234";
        signupPage.inputDetails(firstname,lastname,username,password);
        signupPage.signup();
        Thread.sleep(2000);


        loginPage.login(username, password);

        NoteDialogPage noteDialogPage = new NoteDialogPage(driver);
        assertEquals("Home", driver.getTitle());
        String noteTitle = "test note";
        String noteDescription = "test note description";
        noteDialogPage.createNote(noteTitle, noteDescription);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("here"))).click();
        Thread.sleep(2000);
        String savedNoteTitle = noteDialogPage.getItemTitleText();
        assertEquals(savedNoteTitle, noteTitle);
        noteDialogPage.deleteNote();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("here"))).click();
        boolean isDeleted = noteDialogPage.isNoteDeleted();
        assertTrue(isDeleted);
    }
}
