package com.jlog.config.serialization;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerializationConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        var serializer = new JLogDateTimeSerializer();
        var deserializer = new JLogDateTimeDeserializer();
        return builder -> builder.serializers(serializer).deserializers(deserializer);
    }
}
