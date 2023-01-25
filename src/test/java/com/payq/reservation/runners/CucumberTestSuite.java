package com.payq.reservation.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(glue = "com.payq.reservation.stepdefs", features = "src/test/resources/features", tags = "@regression")

/**
 * @author shilpi chandra
 *
 * Main class to run Cucumber tests.
 */
public class CucumberTestSuite {
}
