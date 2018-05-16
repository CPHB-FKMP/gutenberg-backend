package com.fkmp.gutenberg.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "performance", inheritProfiles = false)
public class PerformanceTest {

    @Test
    @Description("Time test")
    public void timerTest(){

    }
}
