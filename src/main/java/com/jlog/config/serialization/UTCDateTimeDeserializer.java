package com.jlog.config.serialization;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

public class UTCDateTimeDeserializer extends LocalDateTimeDeserializer {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public UTCDateTimeDeserializer() {
        super(DATE_TIME_FORMATTER);
    }
}
