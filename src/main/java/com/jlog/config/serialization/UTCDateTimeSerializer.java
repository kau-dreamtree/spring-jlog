package com.jlog.config.serialization;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class UTCDateTimeSerializer extends LocalDateTimeSerializer {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public UTCDateTimeSerializer() {
        super(DATE_TIME_FORMATTER);
    }
}
