package io.github.eduardoconceicao90.icompras.faturamento.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${icompras.config.kafka.server-url}")
    private String kafkaServerUrl;

    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListener(
            ConsumerFactory<String, String> consumerFactory
    ) {

        ConcurrentKafkaListenerContainerFactory<String, String> listener =
                new ConcurrentKafkaListenerContainerFactory<>();

        listener.setConsumerFactory(consumerFactory);
        return listener;
    }

}
