package com.example.demo.Controller;

import com.example.demo.Listener.KafkaConsumerService;
import com.example.demo.Model.DBModelList;
import com.example.demo.Model.MessageModel;
import com.example.demo.Repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
Decides from which topic will read after a button is clicked(Topic1/Topic2)
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("test/")
public class ConsumerController {

    @Autowired
    private ConsumerRepository repository;

    @Autowired
    private KafkaConsumerService kafkaService;


    @GetMapping("/changeTopic/{topic}")
    public void changeTopic(@PathVariable(value = "topic") String topic){  // merr emrin e topic nga do lexojme
        kafkaService.str = topic;
    }

    public void insertToDB(List<MessageModel> list){ // save to db
        repository.insert(new DBModelList(list));
    }


}
