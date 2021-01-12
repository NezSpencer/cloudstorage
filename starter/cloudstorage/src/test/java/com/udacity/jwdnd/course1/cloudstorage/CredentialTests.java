package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.CredentialDialogPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.LoginPage;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

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
    public void verifyCredentialCanBeCreated() throws InterruptedException{
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

        CredentialDialogPage credentialDialogPage = new CredentialDialogPage(driver);
        assertEquals("Home", driver.getTitle());
        String credentialUsername = "user1";
        String credentialUrl = "http://url.com";
        String credentialPwd = "1234";
        credentialDialogPage.saveCredentials(credentialUsername, credentialUrl, credentialPwd);
        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("here"))).click();
        Thread.sleep(3000);
        String savedUsername = credentialDialogPage.getItemUsername();
        String savedUrl = credentialDialogPage.getItemUrl();
        String savedPassword = credentialDialogPage.getItemPassword();
        assertEquals(credentialUsername, savedUsername);
        assertEquals(credentialUrl, savedUrl);

        //check that password is not stored plain
        assertNotEquals(credentialPwd, savedPassword);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void verifyPasswordIsShownUnencrypted() throws InterruptedException {
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

        CredentialDialogPage credentialDialogPage = new CredentialDialogPage(driver);
        assertEquals("Home", driver.getTitle());
        String credentialUsername = "user1";
        String credentialUrl = "http://url.com";
        String credentialPwd = "1234";
        credentialDialogPage.saveCredentials(credentialUsername, credentialUrl, credentialPwd);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("goHome"))).click();

        Thread.sleep(3000);
        String shownPassword = credentialDialogPage.getDisplayedPassword();
        assertEquals(credentialPwd, shownPassword);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void  verifyCredentialCanBeEdited() throws InterruptedException {
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

        CredentialDialogPage credentialDialogPage = new CredentialDialogPage(driver);
        assertEquals("Home", driver.getTitle());
        String credentialUsername = "user1";
        String credentialUrl = "http://url.com";
        String credentialPwd = "1234";
        credentialDialogPage.saveCredentials(credentialUsername, credentialUrl, credentialPwd);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("goHome"))).click();

        String newUsername = "user22";
        String newUrl = "http://google.com";
        Thread.sleep(2000);
        credentialDialogPage.editUsernameAndUrl(newUsername, newUrl);
        Thread.sleep(2000);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.id("goHome"))).click();
        Thread.sleep(2000);
        String savedUrl = credentialDialogPage.getItemUrl();
        String savedUsername = credentialDialogPage.getItemUsername();
        assertEquals(newUsername, savedUsername);
        assertEquals(newUrl, savedUrl);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void verifyCredentialCanBeDeleted() throws InterruptedException{
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

        CredentialDialogPage credentialDialogPage = new CredentialDialogPage(driver);
        assertEquals("Home", driver.getTitle());
        String credentialUsername = "user1";
        String credentialUrl = "http://url.com";
        String credentialPwd = "1234";
        credentialDialogPage.saveCredentials(credentialUsername, credentialUrl, credentialPwd);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("goHome"))).click();
        Thread.sleep(1000);
        credentialDialogPage.deleteCredential();
        Thread.sleep(1000);
        assertTrue(credentialDialogPage.isCredentialDeleted());
    }


}
