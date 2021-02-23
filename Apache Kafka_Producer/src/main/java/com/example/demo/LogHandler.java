package com.example.demo;

import com.example.demo.Model.MessageModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Takes a file path and reads from the file and then writes in the topic.
Will return a list with messages, and will add each time, only the last added logs.
 */

public class LogHandler {


    public static List<MessageModel> messageList(String path, int lastRow) throws FileNotFoundException {
        List<MessageModel> list = new ArrayList<>();

        FileReader f = new FileReader(path);
        Scanner sc = new Scanner(f);

        int row = 1;
        list.clear();

        while(sc.hasNextLine()){
            String line = sc.nextLine(); // content for the model

            if(row > lastRow) {  // will write only the las added rows
                list.add(new MessageModel(line, path, row));
            }
            row++;
        }
        return list;
    }

}
