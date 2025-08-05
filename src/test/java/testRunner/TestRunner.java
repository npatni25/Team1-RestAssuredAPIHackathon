package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/FeatureFiles",
               
    glue = {"stepdefination"},
    //tags = "@sweet",
    plugin = {"pretty", "html:target/cucumber-reports.html",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
    		//"json:target/cucumber-reports/cucumber.json",
    		//"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
    		//"com.aventstack.chaintest.plugins.ChainTestCucumberListener:"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
	
	
}
