package com.logfire.logback;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * @author tomas@logfire.ai
 */
public class LogfireAppenderXmlEmptyConfigTest {

    @Before
    public void init() throws JoranException {
        LoggerContext loggerContext = ((LoggerContext) LoggerFactory.getILoggerFactory());
        loggerContext.reset();

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(loggerContext);
        configurator.doConfigure("src/test/resources/logback-disabled.xml");
    }

    @Test
    public void testLogfireAppenderConfiguration() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger("ROOT");

        LogfireAppender appender = (LogfireAppender) rootLogger.getAppender("Logfire");
        assertNotNull(appender);
        
        rootLogger.info("I am Groot");

        assertTrue(appender.isDisabled());
    }

}