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

@RunWith(Parameterized.class)
@ActiveProfiles(profiles = "performance", inheritProfiles = false)
public class PerformanceSystemTest extends AbstractResourceTests {

    static WebDriver driver;
    String db;
    String action;
    String search;
    double resultTime;

    public PerformanceSystemTest(String db, String action, String search, double resultTime) {
        this.db = db;
        this.action = action;
        this.search = search;
        this.resultTime = resultTime;
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

    @Parameterized.Parameters(name = "{index} - {0} : testEquals({1} == {2})")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"neo4j", "city", "London", 60},
                {"postgres", "city", "London", 120},
                {"neo4j", "title", "The Return of Peter Grimm Novelised From the Play", 60},
                {"postgres", "title", "The Return of Peter Grimm Novelised From the Play", 60},
                {"neo4j", "author", "Lewis Carroll", 60},
                {"postgres", "author", "Lewis Carroll", 60}
        });
    }


    @Test
    public void givenCityNameTest() {

        inputDataFrontend(driver, search, db, action);

        WebElement submit = driver.findElement(By.id("submit"));
        WebDriverWait wait = new WebDriverWait(driver, new Long(120));

        long startTime = System.nanoTime();
        submit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultTable")));

        long endTime = System.nanoTime() - startTime;
        double totalTime = ((double) endTime / 1000000000.0);
        System.out.println(totalTime);

        WebElement tBodyBooks = driver.findElement(By.id("results"));
        //System.out.println(tBodyBooks.findElements(By.tagName("tr")).size());

        Assertions.assertAll("Checking we get data on the site", () -> {
            Assertions.assertTrue(tBodyBooks.findElements(By.tagName("tr")).size() > 2);
            Assertions.assertTrue(totalTime < resultTime);
        });

    }
}

