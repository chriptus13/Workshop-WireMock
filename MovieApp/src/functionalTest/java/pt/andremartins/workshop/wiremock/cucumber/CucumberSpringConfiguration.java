package pt.andremartins.workshop.wiremock.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@CucumberContextConfiguration
@SpringBootTest(classes = CucumberSpringConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ComponentScan(basePackages = "pt.andremartins.workshop.wiremock.cucumber")
@ConfigurationPropertiesScan(basePackages = "pt.andremartins.workshop.wiremock.cucumber")
public class CucumberSpringConfiguration {
}
