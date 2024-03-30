package pt.andremartins.workshop.wiremock.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/functionalTest/resources/features"},
        plugin = {"pretty", "html:build/cucumber/index.html"}
)
public class CucumberTest {
}