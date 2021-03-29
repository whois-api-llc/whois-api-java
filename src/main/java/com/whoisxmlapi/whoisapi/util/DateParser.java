package com.whoisxmlapi.whoisapi.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    protected static String format = "yyyy-MM-dd HH:mm:ss z";
    protected static String invalidIsoFormat = "yyyy-MM-dd'T'HH:mm:ssx";
    protected final static Logger logger = LoggerFactory.getLogger(DateParser.class);

    public static ZonedDateTime parseNormalizedDate(String date) {
        try {
            return ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException exception) {
            logger.info("Could not parse given normalized date: " + date, exception);
            return null;
        }
    }

    public static ZonedDateTime parseIsoDate(String date) {
        try {
            return ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(invalidIsoFormat));
        } catch (DateTimeParseException exception) {
            logger.info("Could not parse given date: " + date, exception);
            return null;
        }
    }
}
