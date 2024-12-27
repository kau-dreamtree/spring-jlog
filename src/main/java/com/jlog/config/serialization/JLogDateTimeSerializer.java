package com.jlog.config.serialization;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public final class JLogDateTimeSerializer extends LocalDateTimeSerializer {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00");

    public JLogDateTimeSerializer() {
        super(DATE_TIME_FORMATTER);
    }
}
