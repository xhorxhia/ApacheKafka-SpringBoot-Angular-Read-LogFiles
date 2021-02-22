package com.example.demo.Configuration;

import com.example.demo.Model.MessageModel;
import com.fasterxml.jackson.databind.JavaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean
    public ConsumerFactory<String, List<MessageModel>> consumerFactory(){
        Map<String, Object> config = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id2");

        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, MessageModel.class);

        return new DefaultKafkaConsumerFactory<>(
                config,new StringDeserializer(), new JsonDeserializer<List<MessageModel>>(type, objectMapper, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<MessageModel>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, List<MessageModel>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }


}
