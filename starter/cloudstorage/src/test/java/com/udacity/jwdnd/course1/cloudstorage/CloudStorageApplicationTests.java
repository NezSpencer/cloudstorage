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

}
