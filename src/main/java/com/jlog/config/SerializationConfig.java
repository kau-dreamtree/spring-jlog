package com.jlog.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class SerializationConfig {

    private static final String DATE_TIME_FORMAT = StdDateFormat.DATE_FORMAT_STR_ISO8601;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        var datetimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        var serializer = new LocalDateTimeSerializer(datetimeFormatter);
        return builder -> builder.serializers(serializer);
    }
}
