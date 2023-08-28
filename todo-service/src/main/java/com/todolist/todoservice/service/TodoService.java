package com.todolist.todoservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.todoservice.model.ToDo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TodoService {
    ObjectMapper mapper = new ObjectMapper();

    public List<ToDo> getAllToDos(String userName) throws IOException {
        List<ToDo> toDoList = Arrays.asList(mapper.readValue(new File("data/toDoLists.json"), ToDo[].class));
        List<ToDo> toDoListOfThisUser = new ArrayList<>();
        for (ToDo toDo: toDoList) {
            if (toDo.getUserName().equals(userName)){
                toDoListOfThisUser.add(toDo);
            }
        }
        if(toDoListOfThisUser!=null){
            return toDoListOfThisUser;
        }
        else {
            return null;
        }
    }
}
