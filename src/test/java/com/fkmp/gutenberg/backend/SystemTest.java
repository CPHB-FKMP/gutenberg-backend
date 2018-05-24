package com.fkmp.gutenberg.backend;

import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.runners.Parameterized;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
    }

    @Before
    public void beforeEach(){
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }

    @After
    public void afterEach() {
        this.driver.quit();
    }

    @Parameterized.Parameters(name = "{index}: testEquals({1} == {2})")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"neo4j", "city", "London", 5},
                {"postgres", "city", "London", 5},
                {"neo4j", "title", "The Return of Peter Grimm Novelised From the Play", 2},
                {"postgres", "title", "The Return of Peter Grimm Novelised From the Play", 2},
                {"neo4j", "author", "Lewis Carroll", 3},
                {"postgres", "author", "Lewis Carroll", 3}
        });
    }


    @Test
    public void givenCityNameTest() {
        WebElement textField = driver.findElement(By.id("searchField"));
        textField.sendKeys(search);
        textField.getText();
        System.out.println("Search : " + search + " - Actual : "+ textField.getText());

        Select dbDropdown = new Select(driver.findElement(By.id("dbEndpoint")));
        dbDropdown.selectByValue(db);
        System.out.println("db : " + db + " - Actual : "+ dbDropdown.getAllSelectedOptions().get(0).getText());

        Select actionDropdown = new Select(driver.findElement(By.id("action")));
        actionDropdown.selectByValue(action);
        System.out.println("action : " + action + " - Actual : "+ actionDropdown.getAllSelectedOptions().get(0).getText());

        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
        System.out.println("Click");

        WebDriverWait wait = new WebDriverWait(driver, new Long(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultTable")));

        WebElement tBodyBooks = driver.findElement(By.id("results"));
        System.out.println(tBodyBooks.getText());

        List<WebElement> rows = tBodyBooks.findElements(By.tagName("tr"));

        WebElement map = driver.findElement(By.id("map"));

        Assertions.assertAll("Checking we get data on the site", () -> {
            Assert.assertEquals(resultSize, rows.size());
            Assert.assertTrue(map.isDisplayed());

        });

    }
}
