package dev.sobue.workshop.webapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnabledIf(
    value = "dev.sobue.workshop.webapi.ContainerConfiguration#isEnabledDockerEnvironment",
    disabledReason = "If no Docker environment, this test doesn't work."
)
@SpringBootTest
@Import(ContainerConfiguration.class)
@DisplayName("Verify application context loading")
class ApiApplicationTests {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    assertNotNull(applicationContext);
  }
}
