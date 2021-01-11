package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.*;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
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
class CloudStorageApplicationTests {

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

	@Test
	public void getLoginPage() {
		driver.get(baseUrl + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	@Test
	public void unauthorizedUser_accessRestrictedToLoginAndSignup() throws InterruptedException{
		//verify that unauthorized user cant access home
		driver.get(baseUrl+"/home");
		Thread.sleep(10);
		assertEquals(baseUrl+"/login", driver.getCurrentUrl());

		//verify that unauthorized user can access login
		driver.get(baseUrl+"/login");
		Thread.sleep(10);
		assertEquals(baseUrl+"/login", driver.getCurrentUrl());

		//verify that unauthorized user can access signup
		driver.get(baseUrl+"/signup");
		Thread.sleep(10);
		assertEquals(baseUrl+"/signup", driver.getCurrentUrl());
	}

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	@Test
	public void authorizedUserCanAccessHome_and_whenLoggedOutCantAccessHome() throws InterruptedException{
		driver.get(baseUrl+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		HomePage homePage = new HomePage(driver);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String firstname = "John";
		String lastname = "Doe";
		String username = "johnedoe@gmail.com";
		String password = "1234";
		signupPage.inputDetails(firstname, lastname, username, password);
		signupPage.signup();
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
		Thread.sleep(2000);
		loginPage.login(username, password);
		Thread.sleep(2000);
		assertEquals("Home", driver.getTitle());

		homePage.logOut();
		webDriverWait.until(ExpectedConditions.titleIs("Login"));
		assertNotEquals("Home", driver.getTitle());

		driver.get(baseUrl+"/home");
		Thread.sleep(2000);
		assertEquals("Login", driver.getTitle());

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
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
		Thread.sleep(2000);


		loginPage.login(username, password);

		NoteDialogPage noteDialogPage = new NoteDialogPage(driver);
		assertEquals("Home", driver.getTitle());
		String noteTitle = "test note";
		String noteDescription = "test note description";
		noteDialogPage.createNote(noteTitle, noteDescription);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("here"))).click();
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
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
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
		String saveNoteDescription = noteDialogPage.getItemDescriptionText();
		assertEquals(savedNoteTitle, noteTitle);
		assertEquals(saveNoteDescription, noteDescription);

		String newTitle = "ToDo note";
		String newDescription = "this is an edited note";
		noteDialogPage.editNote(newTitle, newDescription);
		new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.linkText("here"))).click();
		Thread.sleep(4000);
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
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
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
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
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
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
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
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
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
		Thread.sleep(5000);
		credentialDialogPage.editUsernameAndUrl(newUsername, newUrl);
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.linkText("here"))).click();
		Thread.sleep(5000);
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
		Thread.sleep(1000);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-message")).getText());
		signupPage.moveToLogin();
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
