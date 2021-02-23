package com.example.demo.Listener;

import com.example.demo.Controller.ConsumerController;
import com.example.demo.Model.DBModelList;
import com.example.demo.Model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KafkaConsumerService {

    @Autowired
    SimpMessagingTemplate template;

    public List<MessageModel> list, list2;

    public String str = "TopicProve";

    @Autowired
    private ConsumerController consumerController;

   @KafkaListener(topics = "Topic1", groupId = "group_id2", containerFactory = "kafkaListenerContainerFactory")
    public void ConsumerMessage(List<MessageModel> list){
     this.list = list;
     if(str.equals("TopicProve")){
         template.convertAndSend("/topic/group", list);  // con listen te klienti me ane te websocket
         consumerController.insertToDB(this.list);  // shton listen me mesg ne db
     }
     }

    @KafkaListener(topics = "Topic2", groupId = "group_id2", containerFactory = "kafkaListenerContainerFactory")
    public void ConsumerMessage2(List<MessageModel> list) {
        this.list2 = list;
        if (str.equals("TopicProve2")) {
            template.convertAndSend("/topic/group", list);  // con listen te klienti me ane te websocket
            consumerController.insertToDB(this.list2); // shto ne db
        }
    }

    }

