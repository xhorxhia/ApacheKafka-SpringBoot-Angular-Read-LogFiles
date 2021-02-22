package com.example.demo.Controller;

import com.example.demo.Model.DBModelList;
import com.example.demo.Model.MessageModel;
import com.example.demo.Repository.ConsumerRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("test/")
public class FilterController {

    @Autowired
    private ConsumerRepository repository;

    public FilterController(ConsumerRepository repository){
        this.repository = repository;
    }

    @GetMapping(value="/search/")
    public List<MessageModel> searchByContent(@RequestParam String content, @RequestParam String source){
        List<DBModelList> list = new ArrayList<>(); // liste me Dbmodel list
        List<MessageModel> finalList = new ArrayList<>(); // kjo do kthehet ne fund

        list = repository.findAll();
        for(int i =0; i< list.size(); i++){

            for(int j=0; j<list.get(i).getList().size(); j++){

                if(list.get(i).getList().get(j).getContent().contains(content) && list.get(i).getList().get(j).getPath().contains(source)){
                    finalList.add(list.get(i).getList().get(j));
                }
            }
        }
        return finalList;
    }

    @GetMapping("/delete")
    public void deleteAll(){
        repository.deleteAll();
    }



}
