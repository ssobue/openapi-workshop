package dev.sobue.workshop.webapi;

import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

/**
 * Testcontainers configuration.
 *
 * @author Sho SOBUE
 */
@TestConfiguration(proxyBeanMethods = false)
public class ContainerConfiguration {

  /**
   * Docker Socket Path
   */
  private static final Path DOCKER_DEFAULT_SOCKET_PATH =
      Path.of("/var/run/docker.sock");

  /**
   * MySQL Container
   *
   * @return MySQLContainer
   * @see <a href="https://docs.spring.io/spring-boot/reference/testing/testcontainers.html">Spring
   * Boot Document</a>
   */
  @Bean(destroyMethod = "stop")
  @ServiceConnection(name = "jdbc")
  public MySQLContainer<?> mySqlContainer() {
    var log = LoggerFactory.getLogger(MySQLContainer.class);
    return new MySQLContainer<>("mysql:8.0")
        .withLogConsumer(new Slf4jLogConsumer(log))
        .withReuse(false);
  }

  /**
   * Test docker environment is enabled.
   *
   * @return docker environment is enabled/disabled.
   */
  public static boolean isEnabledDockerEnvironment() {
    if (StringUtils.hasLength(System.getenv("DOCKER_HOST"))) {
      return true;
    }

    return Files.exists(DOCKER_DEFAULT_SOCKET_PATH)
        && Files.isReadable(DOCKER_DEFAULT_SOCKET_PATH);
  }
}
