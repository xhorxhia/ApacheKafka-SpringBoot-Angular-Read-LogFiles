package com.example.demo.Resource;

import com.example.demo.LogHandler;
import com.example.demo.Model.MessageModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

@RestController
@EnableScheduling
@RequestMapping("/test")
public class ProducerController {

    @Autowired
    KafkaTemplate<String, List<MessageModel>> kafkaTemplate;
    public static long actualOffset =0;
    public String lastObj = "";
    public String lastObj2 = "";

    private static final String TOPIC1 = "Topic1";
    private static final String TOPIC2 = "Topic2";


    @KafkaListener(groupId = "group_id",topicPartitions = {
            @TopicPartition(topic = TOPIC1,
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
            })

    public void consumeObject(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        this.actualOffset = consumerRecord.offset(); // get the last offset
        lastObj = consumerRecord.value(); // obj i fundit i listes se fundit
    }


    @KafkaListener(groupId = "group_id2",topicPartitions =  {
            @TopicPartition(topic = TOPIC2,
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
            }
    )

    public void consumeObject2(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        this.actualOffset = consumerRecord.offset(); // get the last offset
        lastObj2 = consumerRecord.value(); // obj i fundit i listes se fundit
    }


    public MessageModel returnMessage(String string) throws JsonProcessingException {  // returns the last object of the list
        ObjectMapper mapper = new ObjectMapper();
        List<MessageModel> list = new ArrayList<>();
        if(string != ""){
            list = Arrays.asList(mapper.readValue(string, MessageModel[].class));
            return  list.get(list.size() - 1);
        }else{
            return null;
        }
    }


    public String producerToTopic1() throws FileNotFoundException, JsonProcessingException {
        Logger info = Logger.getLogger("info"); // writes info logs to file1.log
        info.info("Get mapping 1");
        List<MessageModel> list = new ArrayList<>();
        if(returnMessage(lastObj) == null){
         list = new LogHandler().messageList("C:/logs/file1.log", 0);
        } else{
            list = new LogHandler().messageList("C:/logs/file1.log", returnMessage(lastObj).getRowNr());
        }

        if(list.size() > 0)
        kafkaTemplate.send("Topic1", list);  //test is topic name

        return "yes";
    }


    public String producerToTopic2() throws FileNotFoundException, JsonProcessingException {
        Logger debug = Logger.getLogger("debug"); // writes debugs to file2.log
        debug.info("Get mapping 2");
        List<MessageModel> list = new ArrayList<>();
        if(returnMessage(lastObj2) == null){
            list = new LogHandler().messageList("C:/logs/file2.log", 0);
        } else{
            list = new LogHandler().messageList("C:/logs/file2.log", returnMessage(lastObj2).getRowNr());
        }

        if(list.size() > 0)
            kafkaTemplate.send("Topic2", list);  //test is topic name
        return "po";
    }


    @Scheduled(fixedRate = 10000)
    @GetMapping("/writeToTopics")
    public void run() throws FileNotFoundException, JsonProcessingException {
        producerToTopic1();
        producerToTopic2();
    }

}
