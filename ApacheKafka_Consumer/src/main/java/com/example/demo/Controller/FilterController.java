package com.example.demo.Controller;

import com.example.demo.Model.DBModelList;
import com.example.demo.Model.MessageModel;
import com.example.demo.Repository.ConsumerRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/*
Controller for the search filters combined together( content and source/path)
Takes the messaged filtred from db
 */

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
        List<DBModelList> list = new ArrayList<>(); // liste me Dbmodel list (elementet ne db)
        List<MessageModel> finalList = new ArrayList<>(); // kjo do kthehet ne fund

        list = repository.findAll(); // kap gjithe elementet e db
        for(int i =0; i< list.size(); i++){  // bridh cdo element te db (cdo DbModelList)

            for(int j=0; j<list.get(i).getList().size(); j++){ // per cdo DBModelLst, brith cdo element te ModelLst qe permban

                if(list.get(i).getList().get(j).getContent().contains(content) && list.get(i).getList().get(j).getPath().contains(source)){ // if current MsgModel element contains those two
                    finalList.add(list.get(i).getList().get(j));  // nqs permban cfare kerkon, shtoji te lista finale
                }
            }
        }
        return finalList;
    }

    @GetMapping("/delete")
    public void deleteAll(){  // delete from db
        repository.deleteAll();
    }



}
