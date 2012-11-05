package org.sharkness.logging.support;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.sharkness.business.factory.PropertiesFactory;

public class LoggerFactory {

	private static Logger logger;

	public static Logger getLogger() {

		if (logger == null) {

			try {

				boolean loggerFileEnabled = PropertiesFactory.getLoggerFileEnabled();
				
				String appName = PropertiesFactory.getApplicationName();

				logger = Logger.getLogger("Sharkness Framework");

				BasicConfigurator.configure();
				
				if (loggerFileEnabled) {
					
					Appender fileAppender = new FileAppender(
						new PatternLayout(new StringBuilder("(").append(appName).append(" - %F:%L) %p %t %c - %d{yyyy/MM/dd hh:mm:ss} - %m%n").toString()), 
						new StringBuilder(System.getProperty("user.home")).append("/sharkness-framework.log").toString()
					);

					logger.addAppender(fileAppender);

				}

				Appender consoleAppender = new ConsoleAppender(new PatternLayout("%5p [%t] (%F:%L) - %d{yyyy/MM/dd hh:mm:ss} - %m%n"));

				logger.addAppender(consoleAppender);

				String lvl = PropertiesFactory.getLoggerLevel();

				Level level = Level.ERROR;

				if (lvl.equalsIgnoreCase("DEBUG")) level = Level.DEBUG;
				else if (lvl.equalsIgnoreCase("ALL")) level = Level.ALL;
				else if (lvl.equalsIgnoreCase("FATAL")) level = Level.FATAL;
				else if (lvl.equalsIgnoreCase("INFO")) level = Level.INFO;
				else if (lvl.equalsIgnoreCase("WARN")) level = Level.WARN;
				else if (lvl.equalsIgnoreCase("TRACE")) level = Level.TRACE;
				else if (lvl.equalsIgnoreCase("OFF")) level = Level.OFF;

				logger.setLevel(level);

			} catch (Exception e) {

				logger.setLevel(Level.ERROR);

			}

		}

		return logger;

	}

}