package com.example.demo.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "MessageTopic")
public class DBModelList {


    //private int id;
    private List<MessageModel> list;

    public DBModelList() {

    }

    public DBModelList(List<MessageModel> list) {
        this.list = list;
    }

    public List<MessageModel> getList() {
        return list;
    }

    public void setList(List<MessageModel> list) {
        this.list = list;
    }
}
