package com.example.demo.Model;

public class MessageModel {
     String content;
     String path;
     int rowNr;

    public MessageModel() {

    }

    public MessageModel(String content, String path, int rowNr) {
        this.content = content;
        this.path = path;
        this.rowNr = rowNr;
    }

    public String getContent() {
        return content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRowNr() {
        return rowNr;
    }

    public void setRowNr(int rowNr) {
        this.rowNr = rowNr;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
