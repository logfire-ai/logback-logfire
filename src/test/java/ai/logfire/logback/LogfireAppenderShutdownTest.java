package ai.logfire.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * ! LOGFIRE_SOURCE_TOKEN must be set as an environment variable before
 * launching the test !
 *
 * @author tomas@logfire.ai
 */
public class LogfireAppenderShutdownTest {

  private Logger logger = (Logger) LoggerFactory.getLogger(LogfireAppenderShutdownTest.class);

  private LogfireAppenderDecorator appender;

  @Before
  public void init() throws JoranException {
    LoggerContext loggerContext = ((LoggerContext) LoggerFactory.getILoggerFactory());
    loggerContext.reset();

    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(loggerContext);
    configurator.doConfigure("src/test/resources/logback-shutdown-test.xml");

    Logger rootLogger = (Logger) LoggerFactory.getLogger("ROOT");
    appender = (LogfireAppenderDecorator) rootLogger.getAppender("Logfire");
    assertEquals(10, appender.getBatchSize());
  }

  @Test
  public void testAppend() throws Exception {
    // This is to easily identify and diagnose messages coming from the same test
    // run
    String batchRunId = UUID.randomUUID().toString().toLowerCase().replace("-", "");

    for (int i = 0; i < 5; i++) {
      MDC.put("requestId", "testErrorLog");
      MDC.put("requestTime", i + "");
      this.logger.info(batchRunId + " Shutdown hook test" + i);

    }
    Thread.sleep(2000);

  }
}