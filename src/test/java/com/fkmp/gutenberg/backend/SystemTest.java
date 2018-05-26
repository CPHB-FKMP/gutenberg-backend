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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
@ActiveProfiles(profiles = "frontend", inheritProfiles = false)
public class SystemTest extends AbstractResourceTests{

    static WebDriver driver;
    String db;
    String action;
    String search;
    int resultSize;
    List<String> results;

    public SystemTest(String db, String action, String search, int resultSize, List<String> results) {
        this.db = db;
        this.action = action;
        this.search = search;
        this.resultSize = resultSize;
        this.results = results;
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
                {"neo4j", "city", "London", 5, Arrays.asList(
                        "",
                        "My Lady of the Chinese Courtyard Elizabeth Cooper",
                        "History of the Reformation in the Sixteenth Century, Vol 2 J. H. Merle D’Aubigne",
                        "The Return of Peter Grimm Novelised From the Play David Belasco",
                        "Morals and the Evolution of Man Max Simon Nordau"
                )},
                {"postgres", "city", "London", 5, Arrays.asList(
                        "",
                        "My Lady of the Chinese Courtyard Elizabeth Cooper",
                        "History of the Reformation in the Sixteenth Century, Vol 2 J. H. Merle D’Aubigne",
                        "The Return of Peter Grimm Novelised From the Play David Belasco",
                        "Morals and the Evolution of Man Max Simon Nordau"
                )},
                {"neo4j", "title", "The Return of Peter Grimm Novelised From the Play", 2, Arrays.asList(
                        "",
                        "The Return of Peter Grimm Novelised From the Play",
                        "The Return of Peter Grimm Novelised From the Play"
                )},
                {"postgres", "title", "The Return of Peter Grimm Novelised From the Play", 2, Arrays.asList(
                        "",
                        "The Return of Peter Grimm Novelised From the Play",
                        "The Return of Peter Grimm Novelised From the Play"
                )},
                {"neo4j", "author", "Lewis Carroll", 2, Arrays.asList(
                        "",
                        "Songs From Alice in Wonderland and Through the Looking-Glass",
                        "Songs From Alice in Wonderland and Through the Looking-Glass"
                )},
                {"postgres", "author", "Lewis Carroll", 2, Arrays.asList(
                        "",
                        "Songs From Alice in Wonderland and Through the Looking-Glass",
                        "Songs From Alice in Wonderland and Through the Looking-Glass"
                )}
        });
    }


    @Test
    public void givenCityNameTest() {

        inputDataFrontend(driver, search, db, action);

        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();

        WebDriverWait wait = new WebDriverWait(driver, new Long(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultTable")));

        WebElement tBodyBooks = driver.findElement(By.id("results"));

        List<WebElement> rows = tBodyBooks.findElements(By.tagName("tr"));

        WebElement map = driver.findElement(By.id("map"));

        Assertions.assertAll("Checking we get data on the site", () -> {
            Assertions.assertEquals(resultSize, rows.size());
            Assertions.assertTrue(map.isDisplayed());
        });

    }
}
