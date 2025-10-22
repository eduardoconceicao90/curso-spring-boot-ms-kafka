package io.github.eduardoconceicao90.icompras.faturamento.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Registra módulos para suporte a Java 8 e data/hora
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());

        // Configura para ignorar propriedades nulas na serialização
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Configura para não falhar em propriedades desconhecidas na desserialização
        mapper.configure(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES, false);

        return mapper;
    }

}
