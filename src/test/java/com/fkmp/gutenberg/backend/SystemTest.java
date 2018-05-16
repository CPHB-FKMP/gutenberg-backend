package com.fkmp.gutenberg.backend;

import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.ActiveProfiles;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
@ActiveProfiles(profiles = "frontend", inheritProfiles = false)
public class SystemTest {

    static WebDriver driver;
    String db;
    String action;
    String search;
    int resultSize;

    public SystemTest(String db, String action, String search, int resultSize) {
        this.db = db;
        this.action = action;
        this.search = search;
        this.resultSize = resultSize;
    }



    @BeforeClass
    public static void beforeAll(){
        System.out.println("BeforeClass");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
    }

    @Before
    public void beforeEach(){
        System.out.println("Before");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }

    @After
    public void afterEach() {
        System.out.println("After");
        this.driver.quit();
    }

    @Parameterized.Parameters(name = "{index}: testEquals({1} == {2})")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"Neo4j", "City", "Copenhagen", 3},
                {"PostGres", "City", "Copenhagen", 3}
        });
    }


    @Test
    public void givenCityNameTest() {
        WebElement textField = driver.findElement(By.id("searchField"));
        textField.sendKeys(search);

        Select dbDropdown = new Select(driver.findElement(By.id("dbEndpoint")));
        dbDropdown.selectByVisibleText(db);

        Select actionDropdown = new Select(driver.findElement(By.id("action")));
        actionDropdown.selectByVisibleText(action);

        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();

        WebElement tBodyBooks = driver.findElement(By.id("XXX"));

        List<WebElement> rows = tBodyBooks.findElements(By.tagName("tr"));

        Assertions.assertAll("Checking we get data on the site", () -> {
            Assert.assertEquals(resultSize, rows.size());
        });
    }
}
