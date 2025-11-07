package org.example.factory;

import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class LogFactory {

    public static final String MDC_RUN_ID_KEY = "runId";
    public static final String LOGBACK_FILE_NAME_KEY = "LOG_FULL_FILE_PATH";
    private static final String BASE_DIR_PROPERTY_KEY = "app.log.baseDir";


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    public static String loggingContext() {

        String DEFAULT_BASE_DIR = "reports";
        String baseDir = System.getProperty(BASE_DIR_PROPERTY_KEY, DEFAULT_BASE_DIR);
        String runId = UUID.randomUUID().toString().substring(0, 8);

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DATE_FORMATTER);
        String time = now.format(TIME_FORMATTER);

        Path reportDirPath = Paths.get(baseDir);

        String logFileName = String.format("%s.json", date);
        Path logFilePath = reportDirPath.resolve(logFileName);
        String absolutePath = logFilePath.toAbsolutePath().toString();

        try {
            Files.createDirectories(reportDirPath);

            System.setProperty(LOGBACK_FILE_NAME_KEY, absolutePath);
            MDC.put(MDC_RUN_ID_KEY, runId);

            Logger factoryLogger = LoggerFactory.getLogger(LogFactory.class);

            factoryLogger.info("Logging initialized. Reports will be saved to: {}", reportDirPath);

        } catch (IOException e) {
            Logger errorLogger = LoggerFactory.getLogger(LogFactory.class);

            errorLogger.error("FATAL: Failed to create report directory structure: {}", reportDirPath, e);
            throw new RuntimeException("Cannot initialize logging file path.", e);
        }

        return runId;
    }

    public static void cleanupLoggingContext() {
        MDC.remove(MDC_RUN_ID_KEY);
    }
}

