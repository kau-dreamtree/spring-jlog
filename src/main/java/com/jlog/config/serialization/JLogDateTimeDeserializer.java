package com.jlog.config.serialization;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

public final class JLogDateTimeDeserializer extends LocalDateTimeDeserializer {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00");

    public JLogDateTimeDeserializer() {
        super(DATE_TIME_FORMATTER);
    }
}
