package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    public static Scenario currentScenario;

    @Before
    public void beforeScenario(Scenario scenario) {
        currentScenario = scenario;
        System.out.println("Running Scenario: " + scenario.getName());
    }
}